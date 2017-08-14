package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.TokenManager;
import com.wfy.web.dao.UserDao;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;
import com.wfy.web.service.IUserService;
import com.wfy.web.utils.MD5Util;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/user/")
public class UserController {

    @Resource
    private IUserService iUserService;

    @Resource
    private TokenManager tokenManager;

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<TokenModel> login(@RequestBody Map<String, String> userMap) {
        ServerResponse<TokenModel> response = iUserService.login(userMap.get("username"),
                userMap.get("password"));
        User user = userDao.selectLogin(userMap.get("username"),
                MD5Util.getMD5(userMap.get("password")));
        return response;
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public ServerResponse<String> logout(TokenModel tokenModel) {
        if (tokenManager.checkToken(tokenModel)) {
            tokenManager.deleteToken(tokenModel.getUserId());
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("注销出错");
    }

    @RequestMapping(value = "register.do", method = RequestMethod.GET)
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(TokenModel tokenModel) {
        if (tokenManager.checkToken(tokenModel)) {
            User user = userDao.getUserById(tokenModel.getUserId());
            if (user != null) {
                return ServerResponse.createBySuccess(user);
            }
        }
        return ServerResponse.createBySuccessMessage("用户未登录，无法获取当前用户信息");
    }

    /* @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.GET)
     public ServerResponse<String> forgetResetPassword(String username, String
             passwordNew, String forgetToken) {
         return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
     }
 */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(TokenModel tokenModel, String passwordOld, String
            passwordNew) {
        if (tokenManager.checkToken(tokenModel)) {
            User user = userDao.getUserById(tokenModel.getUserId());
            if (user != null) {
                return iUserService.resetPassword(passwordOld, passwordNew, user);
            }
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(TokenModel tokenModel, User user) {
        if (tokenManager.checkToken(tokenModel)) {
            if (user != null) {
                //TODO
                return null;
            }
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }
}
