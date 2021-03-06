$(function () {
    buildJqGridGenerator();
    initValidate();

    $('#nestable2').nestable();

});

function initValidate() {
    editFormValidator({
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

/**
 * 所有权限列表
 */
function listPermissions() {
    var roleId = DEFAULT_JQ_GRID.jqGrid('getGridParam','selrow');
    if(!roleId) {
        alert("请选择需要关联权限的角色！");
        return ;
    }
    ajaxRequest({
        type : "GET",
        url  : "/system/rolePermission/listChecked.json",
        data : {
            roleId : roleId
        },
        success : result => {
            result.roleId = roleId;
            PAGE.permsValue = new Vue({
                el : '#permissions',
                data : {
                    roleId : roleId,
                    entities : result.result
                }
            });
            bindIChecks(".permission-icheck").on("ifChecked ifUnchecked", event => {
                relation(event.type, event.target, roleId);
            });
            $("#relation-permission-form").modal();
        }
    });
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
                // 找到当前元素的所有子权限全部全选
                var subCheckbox = $("#sub-of-" + ele.id).find("input[data-parent-id=" + ele.id +"]");
                if(subCheckbox.length > 0) {
                    subCheckbox.iCheck("check");
                }
                // 找到当前元素的父权限的所有子权限，如果该父权限下的所有子权限被选中，则勾选父权限
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
                // 找到当前元素的所有子权限，如果所有子权限被选中，则反选所有子权限
                var subCheckbox = $("#sub-of-" + ele.id + " input[type=checkbox]"),
                    subCheckedCount = subCheckbox.length - subCheckbox.not(":checked").length;
                if(subCheckedCount == subCheckbox.length) {
                    subCheckbox.iCheck("uncheck");
                }
                // 如果当前元素的父权限被选中，反选父权限
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