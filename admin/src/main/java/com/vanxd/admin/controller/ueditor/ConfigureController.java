package com.vanxd.admin.controller.ueditor;

import com.vanxd.admin.ueditor.ActionEnter;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wyd on 2017/2/15.
 */
@Controller
@RequestMapping("/ueditor")
public class ConfigureController {

    @RequestMapping("/configController.json")
    @ResponseBody
    public String config(HttpServletRequest request, HttpServletResponse response) {
        String rootPath = request.getServletPath();
        try {
            return new ActionEnter( request, rootPath).exec();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootPath;
    }
}
