package com.vanxd.admin.controller;

import com.vanxd.data.component.RespJSON;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wyd on 2016/11/17.
 */
@Controller
public class AppErrorController implements ErrorController{
    private final static String ERROR_PATH = "/error";

    /**
     * 支持普通页面异常
     * @return
     */
    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorHtml() {
        return "/error/404";
    }

    /**
     * 支持json格式异常
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public RespJSON error() {
        return new RespJSON(RespJSON.RespCode.FAIL);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
