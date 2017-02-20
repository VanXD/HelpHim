$(function () {
    buildJqGridGenerator();
    initValidate();
    UEditorFactory.getInstance();

});
function initValidate() {
    editFormValidator({
        rules: {
            title : {
                required : true,
                maxlength : 100
            },
            'ue-content' : {
                required : true
            }
        },
        messages : {
            title : {
                required : "必填",
                maxlength : "最长100个字符"
            },
            'ue-content' : {
                required : "必填",
            }
        }
    });
}


function buildJqGridGenerator() {
    return jqGridFactory.generate({
        tableSelector : "#data-table-1",
        pager : "pager-table-1",
        url : "/article/information/list.json",
        caption:"文章管理",
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
    $("#id").val();
    var ue = UEditorFactory.getInstance();
    ue.setContent("");
}

/**
 * 编辑模板，模态框
 *
 * @param id 数据ID
 */
function editFuncDiaglog(entity) {
    var ue = UEditorFactory.getInstance();
    ue.setContent(entity.content);
}

/**
 * 删除模板，模态框
 *
 * @param id 数据ID
 */
function delFuncDiaglog(id) {
}
