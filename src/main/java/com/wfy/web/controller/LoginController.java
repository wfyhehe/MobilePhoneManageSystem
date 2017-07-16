package com.wfy.web.controller;

import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import com.wfy.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/12.
 */
@Controller
public class LoginController {
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    @Resource
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("user_login.do")
    public String login() {
        return "user_login";
    }

    @RequestMapping("login_process.do")
    public String loginProcess(String username, String password, String
            verificationCode, HttpSession session, HttpServletRequest request) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
        System.out.println("verificationCode: " + verificationCode);
        if (!verificationCode.toLowerCase().equals(session.getAttribute
                ("verificationCode"))) {
            request.setAttribute("error", "verificationCode");
            return "user_login";
        } else if (!userService.exists(new User(username, password,
                UserStatus.ONLINE))) {
            request.setAttribute("error", "username");
            return "user_login";
        } else if (!userService.match(new User(username, password, UserStatus.ONLINE))) {
            request.setAttribute("error", "password");
            return "user_login";
        } else {
            session.removeAttribute("verificationCode");
            session.setAttribute("username", username);
            return "home";
        }
    }

}
