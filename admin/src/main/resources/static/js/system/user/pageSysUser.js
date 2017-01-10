$(function () {
    buildJqGridGenerator();
    initValidate();
});

function initValidate() {
    editFormValidator({
        rules: {
            nickname : {
                required : true,
                maxlength : 100
            },
            username : {
                required : true,
                maxlength : 100
            },
            password : {
                accordingTo : true,
                maxlength : 30,
                minlength : 6

            },
            email : {
                required : true,
                maxlength : 100
            },
            mobilePhone : {
                required : true,
                number : true,
                maxlength : 11,
                minlength : 11
            }
        },
        messages : {
            nickname : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            username : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            password : {
                maxlength : "最长30个字符",
                minlength : "最短6个字符",
                accordingTo : "必填"
            },
            email : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            mobilePhone : {
                required : "必填",
                number : "只能填数字",
                maxlength : "最长11个字符",
                minlength : "最短11个字符"
            }
        }
    });
}


function buildJqGridGenerator() {
    var tableSelector = "#data-table-1",
        pager = "pager-table-1";
    return jqGridFactory.generate({
        tableSelector : tableSelector,
        pager : pager,
        url : "/system/user/list.json",
        caption:"系统用户列表",
        colNames : ["昵称", "用户名", "邮箱", "手机", "创建时间" ],
        colModel : [
            {
                name : "nickname",
                index : "nickname"
            },
            {
                name : "username",
                index : "username"
            },
            {
                name : "email",
                index : "email",
            },
            {
                name : "mobilePhone",
                index : "mobilePhone"
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
        title : "关联角色",
        buttonicon : "ui-icon-shuffle",
        onClickButton : listRoles
    });
}

/**
 * 显示所有角色，并标注该用户已关联的角色
 */
function listRoles() {
    var dataId = DEFAULT_JQ_GRID.jqGrid('getGridParam','selrow');
    if(!dataId) {
        alert("请选择需要关联角色的用户！");
        return ;
    }

    ajaxRequest({
        type : "GET",
        url  : "/system/userRole/listChecked.json",
        data : {
            userId : dataId
        },
        success : result => {
            result.userId = dataId;
            if ( !PAGE.rolesVue ) {
                PAGE.rolesVue = new Vue({
                    el: '#roles',
                    data: {
                        userId : dataId,
                        entities: result.result
                    },
                    methods : {
                        relation : (event, roleId, userId) => {
                            relation(event.target, roleId, userId);
                        }
                    }
                });
            }
            $("#relation-role-form").modal();
        }
    });
}

/**
 * 关联角色和用户
 * @param ele       触发事件的checkbox
 * @param roleId    角色ID
 * @param userId    用户ID
 */
function relation(ele, roleId, userId) {
    $(ele).parent().toggleClass("checked");
    if(ele.checked) {
        ajaxRequest({
            type : "post",
            url  : "/system/userRole/edit.json",
            data : {
                roleId : roleId,
                userId : userId
            }
        });
    } else {
        ajaxRequest({
            type : "post",
            url  : "/system/userRole/cancelRelation.json",
            data : {
                roleId : roleId,
                userId : userId
            }
        });
    }
}

/**
 * 添加模板，模态框
 */
function addFuncDiaglog(id) {
    $("#id").val();
    $("#edit-form #username").prop("disabled", false);
}

/**
 * 编辑模板，模态框
 *
 * @param id 数据ID
 */
function editFuncDiaglog(entity) {
    $("#edit-form #password").val("");
    $("#edit-form #username").prop("disabled", true);
}

/**
 * 删除模板，模态框
 *
 * @param id 数据ID
 */
function delFuncDiaglog(id) {
}
