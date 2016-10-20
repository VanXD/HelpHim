/**
 * todo 这个文件里的各个功能分到其他文件去
 */
var iJqGrid = null;


$(window).bind('resize', function () {
    bindJqGridResize();
});

$(function () {
    initMenu();
    dateFormatterRegister();
    bindIChecks();
    initCustomValidateMethod();
});

/**
 * 初始化自定义表单校验方法
 * todo 分离到其他文件
 */
function initCustomValidateMethod() {
    /**
     * 根据#{according-to}的值进行校验
     * 1.如果{according-data}为true，则指定的标签有值就校验通过
     * 2.如果不为true，则{according-data}的值与{according-to}标签的值相同才校验通过
     */
    $.validator.addMethod("accordingTo", function (value, ele, params) {
        var accordingObjData = $("#" + $(ele).data("according-to")).val();
        var accordingPropertyValue = $(ele).data("according-data");
        if(accordingPropertyValue == true) {
            if(accordingObjData) {
                return true;
            } else {
                return !!value;
            }
        } else {
            return accordingObjData == $(ele).data("according-data");
        }
    }, "必填");
}

/**
 * 生成icheck组件
 * @param selector 可为空 为空使用默认的，否则只为{selector}生成组件
 */
function bindIChecks(selector) {
    var doSelector = ".i-checks";
    if(selector) {
        doSelector = selector;
    }
    var iChecks = $(doSelector);
    if(0 < iChecks.length) {
        $(doSelector).iCheck({
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
        iJqGrid = $(data.tableSelector).jqGrid({
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
                addfunc : id => {
                    var editModalForm = $("#edit-modal-form");
                    editModalForm.find("input:not([type=checkbox]), input:not([type=radio])").val("");
                    editModalForm.find("input:checked").iCheck("uncheck");
                    addFuncDiaglog(id);
                    editModalForm.modal();
                },
                editfunc  : id => {
                    $.ajax({
                        type : "GET",
                        url  : "getById.json",
                        data : {
                            id : id
                        },
                        success : result => {
                            if(isRequestSuccess(result)) {
                                addPrimaryKeyInput(id);
                                renderData(result.result);
                                editFuncDiaglog(result.result);
                                $("#edit-modal-form").modal();
                            } else {
                                handleRequestFail(result);
                            }
                        }
                    });
                },
                delfunc : id => {
                    $.ajax({
                        type : "POST",
                        url  : "delete.json",
                        data : {
                            id : id
                        },
                        success : result => {
                            if(isRequestSuccess(result)) {
                                $(iJqGrid).trigger("reloadGrid");
                            } else {
                                handleRequestFail(result);
                            }
                        }
                    });
                },
                alerttext  : "请选中需要操作的数据行！"
            },{},{},{},{
                multipleSearch : true
            }
        );
        return iJqGrid;
    }
};

var DateTimePickerFactory = {
    /**
     * 将一个dom实例化为DateTimePicker组件
     * @param ele
     */
    generate : function(ele) {
        $(ele).datetimepicker({
            language : "zh-CN"
        });
    }
};


/**
 * 渲染数据，需要input的ID和字段名相同
 * @param entity
 */
function renderData(entity) {
    var value;
    for(var key in entity) {
        value = entity[key];
        // 这里判断可能会是checkbox radio之类的
        if(typeof value == "boolean") {
            if(value == true) {
                $("#" + key).iCheck("check");
            } else {
                $("#" + key).iCheck("uncheck");
            }
        } else {
            $("#" + key).val(value);
        }
    }
}

function addPrimaryKeyInput(id) {
    document.getElementById("id").value = id;
}

function isRequestSuccess(result) {
    return 200 == result.code;
}

function handleRequestFail(result) {
    alert(result.message);
}

/**
 * 发起ajax请求
 * @param ajaxObj
 */
function ajaxRequest(ajaxObj) {
    $.ajax({
        type : ajaxObj.type || "get",
        url  : ajaxObj.url,
        data : ajaxObj.data,
        sync : ajaxObj.sync || true ,
        success : result => {
            if(isRequestSuccess(result)) {
                if(ajaxObj.success) {
                    ajaxObj.success(result);
                }
            } else {
                handleRequestFail(result);
            }
        }
    });
}