$(function () {
    buildJqGridGenerator();
    initValidate();
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
            userId : dataId
        },
        success : result => {
            if(isRequestSuccess(result)) {
                result.userId = dataId;
                var roleTmpl = `
                        <% for(var i = 0, j = result.length; i < j ; i++) { %>
                        <tr>
                            <td><%=i+1%></td>
                            <td><%=result[i].name%></td>
                            <td><%=result[i].description%></td>
                            <td>
                                <label class="i-checks" id="is-show-checks">
                                    <div class="icheckbox_square-green <%= result[i].checked ? "checked" : ''%>" style="position: relative;">
                                        <input <%= result[i].checked ? 'checked=true' : ''%>" onchange="relation(this, '<%=result[i].id%>', '<%=userId%>')" class="role-icheck" type="checkbox" style="position: absolute; opacity: 0;">
                                        <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                                    </div>
                                </label>
                            </td>
                        </tr>
                        <% } %>
                    `;
                $("#roles").html(template.compile(roleTmpl)(result));
            } else {
                handleRequestFail(result);
            }
        }
    });
    $("#relation-role-form").modal();
}