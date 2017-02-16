package com.vanxd.admin.controller.ueditor;

import com.vanxd.admin.exception.BusinessException;
import com.vanxd.admin.ueditor.ActionEnter;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wyd on 2017/2/15.
 */
@Controller
@RequestMapping("/ueditor")
public class ConfigureController {

    @RequestMapping("/configController.json")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        String rootPath = request.getServletPath();
        try {
            PrintWriter writer = response.getWriter();
            writer.write(new ActionEnter( request, rootPath).exec());
        } catch (JSONException e) {
            throw new BusinessException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
