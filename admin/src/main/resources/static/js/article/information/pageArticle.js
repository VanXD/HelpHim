$(function () {
    buildJqGridGenerator();
    initValidate();

    var ue = UE.getEditor('ue-content');

});
function initValidate() {
    editFormValidator({
        rules: {
            name : {
                required : true
            },
            permission : {
                required : true,
                maxlength : 50
            },
            description : {
                required : true,
                maxlength : 100
            },
            weight : {
                required : true,
                number : true
            }
        },
        messages : {
            name : {
                required : "必填"
            },
            permission : {
                required : "必填",
                maxlength : "最长50个字符"
            },
            description : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            weight : {
                required : "必填",
                number : "只能填数字"
            }
        }
    });
}


function buildJqGridGenerator() {
    return jqGridFactory.generate({
        tableSelector : "#data-table-1",
        pager : "pager-table-1",
        url : "/article/information/list.json",
        caption:"菜单权限管理",
        colNames : ["标题", "创建人","创建时间" ],
        colModel : [
            {
                name : "title",
                index : "title"
            },
            {
                name : "creatorNickname",
                index : "creatorNickname"
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
    });
}

/**
 * 添加模板，模态框
 */
function addFuncDiaglog(id) {
    $("#parentId").val(id);
    $("#id").val();
}

/**
 * 编辑模板，模态框
 *
 * @param id 数据ID
 */
function editFuncDiaglog(entity) {
    console.log(JSON.stringify(entity));
}

/**
 * 删除模板，模态框
 *
 * @param id 数据ID
 */
function delFuncDiaglog(id) {
}
