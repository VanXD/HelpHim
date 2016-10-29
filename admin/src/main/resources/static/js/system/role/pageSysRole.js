$(function () {
    buildJqGridGenerator();
    initValidate();

    $('#nestable2').nestable();

});

function initValidate() {
    $("#edit-form").validate({
        errorPlacement: (error, element) => {
            element.after(error);
        },
        rules: {
            name : {
                required : true,
                maxlength : 100
            },
            role : {
                required : true,
                maxlength : 100
            },
            description : {
                required : true,
                maxlength : 300
            }
        },
        messages : {
            name : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            role : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            description : {
                required : "必填",
                maxlength : "最长300个字符"
            }
        },
        submitHandler : form => {
            $(form).ajaxSubmit({
                success : result => {
                    if(200 == result.code) {
                        $(iJqGrid).trigger("reloadGrid");
                        $("#edit-modal-form").modal("hide");
                    } else {
                        alert(result.message);
                    }
                }
            });
        }
    });
}


function buildJqGridGenerator() {
    return jqGridFactory.generate({
        tableSelector : "#data-table-1",
        pager : "pager-table-1",
        url : "/system/role/list.json",
        caption:"角色列表",
        colNames : ["名称", "标识", "描述", "创建人","创建时间" ],
        colModel : [
            {
                name : "name",
                index : "name"
            },
            {
                name : "role",
                index : "role"
            },
            {
                name : "description",
                index : "description"
            },
            {
                name : "creatorUserNickname",
                index : "creatorUserNickname"
            },
            {
                name : "createTime",
                index : "createTime",
                formatter : (cellValue, options, row) => {
                    return new Date(cellValue).format("yyyy-MM-dd hh:mm:ss");
                },
                searchoptions : {
                    dataInit : ele => {
                        DateTimePickerFactory.generate(ele);
                    }
                }
            }
        ]
    }).navButtonAdd('#pager-table-1',{
        caption : "",
        title : "关联权限",
        buttonicon : "ui-icon-shuffle",
        onClickButton : listPermissions
    });
}

/**
 * 添加模板，模态框
 */
function addFuncDiaglog(id) {
    $("#id").val();
}

/**
 * 编辑模板，模态框
 *
 * @param id 数据ID
 */
function editFuncDiaglog(entity) {
}

/**
 * 删除模板，模态框
 *
 * @param id 数据ID
 */
function delFuncDiaglog(id) {
}

function listPermissions() {
    var roleId = iJqGrid.jqGrid('getGridParam','selrow');
    if(!roleId) {
        alert("请选择需要关联权限的角色！");
        return ;
    }
    $.ajax({
        type : "GET",
        url  : "/system/rolePermission/listChecked.json",
        data : {
            roleId : roleId
        },
        success : result => {
            if(isRequestSuccess(result)) {
                result.roleId = roleId;
                var permissionTmpl = `
                    <% for(var i = 0, j = result.length; i < j ; i++) { %>
                    <li class="dd-item dd-collapsed" id="1">
                        <button data-action="collapse" type="button" style="display: none;">Collapse</button>
                        <button data-action="expand" type="button">Expand</button>
                        <div class="dd-row">
                            <span class="pull-right">
                                <input <%=result[i].checked ? 'checked=true' : ''%> id="<%=result[i].id%>" class="parent-permission-icheck" type="checkbox">
                            </span>
                            <span class="label label-info"><i class="fa <%=result[i].icon%>"></i></span> <%=result[i].name%>
                        </div>
                        <ol class="dd-list" style="display: none;" id="sub-of-<%=result[i].id%>">
                            <% for(var k = 0, l = result[i].subPermissions.length; k < l ; k++) { %>
                            <li class="dd-item" data-id="2">
                                <div class="dd-row">
                                    <span class="pull-right">
                                        <input id="<%= result[i].subPermissions[k].id%>" <%= result[i].subPermissions[k].checked ? 'checked=true' : ''%>" onchange="relation(this, '<%=roleId%>', '<%=result[i].subPermissions[k].id%>')" data-parent-id="<%=result[i].id%>" class="permission-icheck" type="checkbox">
                                    </span>
                                    <span class="label label-info"></span> <%=result[i].subPermissions[k].name%>.
                                </div>
                            </li>
                            <% } %>
                        </ol>
                    </li>
                    <% } %>
                `;
                // 检查子菜单是否被全选
                $("#permissions").html(template.compile(permissionTmpl)(result));
                bindIChecks(".permission-icheck").on("ifChecked ifUnchecked", event => {
                    relation(event.type, event.target, roleId);
                });
                bindIChecks(".parent-permission-icheck").on("ifChecked ifUnchecked", event => {
                    relationAll(event.type, event.target, roleId);
                });
            } else {
                handleRequestFail(result);
            }
        }
    });
    $("#relation-permission-form").modal();
}

/**
 * 关联角色和权限
 * 当所有子菜单被选中时，选择父菜单，
 * 当有一个子菜单被取消选中时，取消选择父菜单
 * @param eventType 事件类型
 * @param ele       触发事件的checkbox
 * @param roleId    角色ID
 */
function relation(eventType, ele, roleId) {
    if(eventType == "ifChecked") {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/edit.json",
            data : {
                roleId : roleId,
                permissionId : ele.id
            },
            success : result => {
                var parentId = $(ele).data("parent-id");
                if(parentId) {
                    var parentCheckbox = $("#" + parentId);
                    if(!parentCheckbox.prop("checked")) {
                        var subCheckbox = $("#sub-of-" + parentId + " input[type=checkbox]"),
                            subCheckedCount = subCheckbox.length - subCheckbox.not(":checked").length;
                        if(subCheckedCount == subCheckbox.length) {
                            parentCheckbox.iCheck("check");
                        }
                    }

                }
            }
        });
    } else if (eventType == "ifUnchecked") {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/cancelRelation.json",
            data : {
                roleId : roleId,
                permissionId : ele.id
            },
            success : result => {
                var parentId = $(ele).data("parent-id");
                if(parentId) {
                    var parentCheckbox = $("#" + parentId);
                    if(parentCheckbox.prop("checked")) {
                        parentCheckbox.iCheck("uncheck");
                    }
                }
            }
        });
    }
}

/**
 * 全选/反全选
 * 全选：将所有子菜单选中
 * 反全选：当所有子菜单被选中时才反选所有子菜单
 * @param eventType
 * @param ele
 * @param roleId
 */
function relationAll(eventType, ele, roleId) {
    if(eventType == "ifChecked") {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/edit.json",
            data : {
                roleId : roleId,
                permissionId : ele.id
            },
            success : result => {
                $("#sub-of-" + ele.id + " input[type=checkbox]").iCheck("check");
            }
        });
    } else if (eventType == "ifUnchecked") {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/cancelRelation.json",
            data : {
                roleId : roleId,
                permissionId : ele.id
            },
            success : result => {
                var subCheckbox = $("#sub-of-" + ele.id + " input[type=checkbox]"),
                    subCheckedCount = subCheckbox.length - subCheckbox.not(":checked").length;
                if(subCheckedCount == subCheckbox.length) {
                    $("#sub-of-" + ele.id + " input[type=checkbox]").iCheck("uncheck");
                }
            }
        });
    }
}