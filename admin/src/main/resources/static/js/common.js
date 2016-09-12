$(function () {
    initMenu();
});


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