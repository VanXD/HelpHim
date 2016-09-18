$(window).bind('resize', function () {
    bindJqGridResize();
});

$(function () {
    initMenu();
    dateFormatterRegister();
    bindIChecks();
});


function bindIChecks() {
    var iChecks = $('.i-checks');
    if(0 < iChecks.length) {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
    }
}

function dateFormatterRegister() {
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }
}

/**
 * 浏览器尺寸改变时，改变jqGrid的宽度
 */
function bindJqGridResize() {
    var jqGridWrapper = $('.jqGrid_wrapper');
    if(jqGridWrapper.length > 0) {
        var width = $('.jqGrid_wrapper').width();
        $('#data-table-1').setGridWidth(width);
    }
}

/**
 * 初始化菜单，没找到thymeleaf怎么include一个url，只能用这种方式了，感觉好蠢。
 */
function initMenu() {
    var pageWrapper = $("#page-wrapper");
    if(pageWrapper.length > 0) {
        $.ajax({
            type : "GET",
            url  : "/system/menu/list",
            async : false,
            success : function (result) {
                if($("#page-wrapper").length > 0) {
                    $("#page-wrapper").before(result);
                    var navbar = $("#navbar");
                    navbar.find(".active").removeClass("active");
                    var choosedMenu = getChoosedMenu(navbar);
                    if(choosedMenu) {
                        $(choosedMenu).parent().addClass("active");
                        var topParentId = choosedMenu.data("parent-id");
                        $("#menu_" + topParentId).addClass("active").trigger("click");
                    }
                }
            }
        });
    }
}

/**
 * 获得当前带参数uri与菜单中相匹配的项
 * 1.匹配整个带参数uri
 * 2.从后往前去掉&参数进行匹配，因为菜单中配的URL参数肯定在前面的，后面去掉一些非必要参数，例如pageNo,pageSize
 * 3.从后往前去掉？参数进行匹配
 *
 * @param navbar
 * @returns 被选中的菜单
 */
function getChoosedMenu(navbar) {
    var uri = getUriWithParamsByUrl(window.location.href);
    while(true) {
        var choosedMenu = navbar.find("a[href*='"+ uri +"']");
        if(choosedMenu.length > 0) {
            return choosedMenu.eq(0);
        }
        var index = uri.lastIndexOf("&") > -1 ? uri.lastIndexOf("&") : uri.lastIndexOf("?") > -1 ? uri.lastIndexOf("?") : null;
        if(index) {
            uri = uri.substr(0, index);
        } else {
            return null;
        }
    }
}

function getUriWithParamsByUrl(url) {
    url = url.substr(7);
    return url.substr(url.indexOf("/"));
}

/**
 * todo 引入模块化编程
 * @type {{generate: jqGridFactory.generate}}
 */
var jqGridFactory = {
    generate : function (data) {
        $(data.tableSelector).jqGrid({
            url : data.url,
            mtype : data.mtype || "get",
            datatype : "json",
            caption : data.caption,
            pager : data.pager,
            viewrecords: true,
            autowidth : true,
            height : "100%",
            rownumbers : true,
            rowNum : 10,
            rowList : [10, 20, 30, 50],
            colNames : data.colNames,
            colModel : data.colModel
        });
        // Setup buttons
        $(data.tableSelector).jqGrid('navGrid', '#' + data.pager,
            {
                addfunc : addFuncDiaglog,
                editfunc  : editFuncDiaglog,
                delfunc : delFuncDiaglog,
                alerttext  : "请选中需要操作的数据行！"
            }
        );
    }
};