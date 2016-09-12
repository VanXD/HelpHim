$(function () {
    $("#data-table-1").jqGrid({
        url : "/system/permission/list.json",
        datatype : "json",
        colNames : ["权限", "名称"],
        colModel : [
            {
                name : "permission",
                index : "permission",
                width : 1
            },
            {
                name : "name",
                index : "name",
                width : 1
            }
        ],
        rownumbers : true,
        rowNum : 10,
        rowList : [10, 20, 30],
        pager : "pager-table-1",
        sortname : "permission",
        viewrecords: true,
        sortorder: "desc",
        autowidth : true,
        caption:"JSON Example"
    });
    // $("#data-table-1").jqGrid('navGrid', '#pager-table-1',
    //     {edit: true, add: true, del: true, search: true},
    //     {height: 200, reloadAfterSubmit: true}
    // );
});

