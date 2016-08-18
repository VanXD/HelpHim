package com.vanxd.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by wejoy-a on 2016/8/18.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(ModelAndView mv, String username, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            mv.setViewName("dashboard");
        } catch (UnknownAccountException e) {
            mv.addObject("message", "用户不合法!");
            mv.setViewName("login");
        } catch (IncorrectCredentialsException e) {
            mv.addObject("message", "密码错误!");
            mv.setViewName("login");
        } catch (LockedAccountException e) {
            mv.addObject("message", "用户已经被锁定不能登录，请与管理员联系!");
            mv.setViewName("login");
        } catch (ExcessiveAttemptsException e) {
            mv.addObject("message", "错误次数过多,请过半小时再试!");
            mv.setViewName("login");
        } catch (AuthenticationException e) {
            mv.addObject("message", "认证失败!");
            mv.setViewName("login");
        } catch (Exception e) {
            mv.addObject("message", "认证失败!");
            mv.setViewName("login");
        }
        return mv;
    }



}
