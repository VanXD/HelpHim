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
    var dataId = iJqGrid.jqGrid('getGridParam','selrow');
    if(!dataId) {
        alert("请选择需要关联权限的角色！");
        return ;
    }
    $.ajax({
        type : "GET",
        url  : "/system/rolePermission/listChecked.json",
        data : {
            roleId : dataId
        },
        success : result => {
            if(isRequestSuccess(result)) {
                result.roleId = dataId;
                var permissionTmpl = `
                    <% for(var i = 0, j = result.length; i < j ; i++) { %>
                    <li class="dd-item dd-collapsed" id="1">
                        <button data-action="collapse" type="button" style="display: none;">Collapse</button>
                        <button data-action="expand" type="button">Expand</button>
                        <div class="dd-row">
                            <span class="pull-right">
                                <label class="i-checks" id="is-show-checks">
                                <div class="icheckbox_square-green <%=isAllChecked(result[i]) ? "checked" : ''%>" style="position: relative;" >
                                    <input onchange="realationAll(this, '<%=roleId%>', '<%=result[i].subPermissions%>')" class="permission-icheck" type="checkbox" style="position: absolute; opacity: 0;">
                                    <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                                </div>
                            </label>
                            </span>
                            <span class="label label-info"><i class="fa <%=result[i].icon%>"></i></span> <%=result[i].name%>
                        </div>
                        <ol class="dd-list" style="display: none;">
                            <% for(var k = 0, l = result[i].subPermissions.length; k < l ; k++) { %>
                            <li class="dd-item" data-id="2">
                                <div class="dd-row">
                                    <span class="pull-right">
                                        <label class="i-checks" id="is-show-checks">
                                            <div class="icheckbox_square-green <%= result[i].subPermissions[k].checked ? "checked" : ''%>" style="position: relative;">
                                                <input <%= result[i].subPermissions[k].checked ? 'checked=true' : ''%>" onchange="relation(this, '<%=roleId%>', '<%=result[i].subPermissions[k].id%>')" class="permission-icheck" type="checkbox" style="position: absolute; opacity: 0;">
                                                <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                                            </div>
                                        </label>
                                    </span>
                                    <span class="label label-info"></span> <%=result[i].subPermissions[k].name%>.
                                </div>
                            </li>
                            <% } %>
                        </ol>
                    </li>
                    <% } %>
                `;
                template.helper("isAllChecked", permission => {
                    return isAllChecked(permission);
                });
                $("#permissions").html(template.compile(permissionTmpl)(result));
            } else {
                handleRequestFail(result);
            }
        }
    });
    $("#relation-permission-form").modal();
}

/**
 * 关联角色和权限
 * @param ele       触发事件的checkbox
 * @param roleId    角色ID
 * @param userId    用户ID
 */
function relation(ele, roleId, permissionId) {
    $(ele).parent().toggleClass("checked");
    if(ele.checked) {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/edit.json",
            data : {
                roleId : roleId,
                permissionId : permissionId
            }
        });
    } else {
        ajaxRequest({
            type : "post",
            url  : "/system/rolePermission/cancelRelation.json",
            data : {
                roleId : roleId,
                permissionId : permissionId
            }
        });
    }
}

function isAllChecked(permission) {
    var subPermissions = permission.subPermissions,
        checkedCount = 0;
    for(var subPermission of subPermissions) {
        if(subPermission.checked) {
            checkedCount++;
        }
    }
    console.log(checkedCount == subPermissions.length);
    return checkedCount == subPermissions.length;
}