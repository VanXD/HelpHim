$(function () {
    buildJqGridGenerator();
});


function buildJqGridGenerator() {
    jqGridFactory.generate({
        tableSelector : "#data-table-1",
        pager : "pager-table-1",
        url : "/system/permission/list.json",
        caption:"菜单管理",
        colNames : ["名称", "权限", "图标", "描述", "URL", "权重", "是否显示", "创建人","创建时间" ],
        colModel : [
            {
                name : "name",
                index : "name"
            },
            {
                name : "permission",
                index : "permission"
            },
            {
                name : "icon",
                index : "icon",
            },
            {
                name : "description",
                index : "description"
            },
            {
                name : "url",
                index : "url"
            },
            {
                name : "weight",
                index : "weight"
            },
            {
                name : "isShow",
                index : "isShow",
                formatter : (cellValue, options, row) => {
                    return cellValue ? "是" : "否";
                }
            },
            {
                name : "creatorUserId",
                index : "creatorUserId"
            },
            {
                name : "createTime",
                index : "createTime",
                formatter : (cellValue, options, row) => {
                    return new Date(cellValue).format("yyyy-MM-dd hh:mm:ss");
                }
            }
        ]
    });
}

/**
 * 添加模板，模态框
 */
function addFuncDiaglog() {
    $("#add-modal-form").modal();
}

/**
 * 编辑模板，模态框
 *
 * @param id 数据ID
 */
function editFuncDiaglog(id) {
}

/**
 * 删除模板，模态框
 *
 * @param id 数据ID
 */
function delFuncDiaglog(id) {
}
