package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.User;
import com.wfy.web.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@CrossOrigin // 开发使用，发布时必须取消
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService iUserService;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<User> login(@RequestBody Map<String, String> user, HttpSession session) {
        System.out.println(user);
        ServerResponse<User> response = iUserService.login(user.get("username"), user.get("password"));
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    public ServerResponse<String> register(User user) {
        System.out.println(user);
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute((Const.CURRENT_USER));
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createBySuccessMessage("用户未登录，无法获取当前用户信息");
    }

    @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username, String
            passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @RequestMapping(value = "reset_password.do", method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String
            passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        //TODO
        return null;
    }
}
