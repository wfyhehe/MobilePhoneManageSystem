package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.TokenModel;
import com.wfy.web.model.User;
import com.wfy.web.service.ITokenService;
import com.wfy.web.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    private ITokenService iTokenService;

//    @RequestMapping(value = "login.do", method = RequestMethod.POST)
//    public ServerResponse<TokenModel> login(@RequestBody Map<String, String> userMap) {
//        return iUserService.login(userMap.get("username"), userMap.get("password"));
//    }

    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ServerResponse<TokenModel> login(@RequestBody User user) {
        return iUserService.login(user.getUsername(), user.getPassword());
    }

    @RequestMapping(value = "logout.do", method = RequestMethod.GET)
    public ServerResponse<String> logout(TokenModel tokenModel) {
        if (iTokenService.checkToken(tokenModel)) {
            iTokenService.deleteToken(tokenModel.getUserId());
            iUserService.logout(tokenModel.getUserId());
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByErrorMessage("注销出错");
    }

    @RequestMapping(value = "register.do", method = RequestMethod.POST)
    public ServerResponse<String> register(@RequestBody User user) {
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_username.do", method = RequestMethod.GET)
    public ServerResponse<String> checkUsername(String username) {
        return iUserService.checkUsername(username);
    }

    @RequestMapping(value = "check_valid.do", method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @RequestMapping(value = "get_user_info.do", method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(TokenModel tokenModel) {
        if (iTokenService.checkToken(tokenModel)) {
            return ServerResponse.createBySuccess(iUserService.getUser(tokenModel.getUserId()));
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
        if (iTokenService.checkToken(tokenModel)) {
            return iUserService.resetPassword(passwordOld, passwordNew, tokenModel.getUserId());
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }

    @RequestMapping(value = "update_information.do", method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(TokenModel tokenModel, User user) {
        if (iTokenService.checkToken(tokenModel)) {
            if (user != null) {
                //TODO
                return null;
            }
        }
        return ServerResponse.createByErrorMessage("用户未登录");
    }

    @RequestMapping(value = "validate_token.do")
    public ServerResponse<String> validateToken(TokenModel tokenModel) {
        if (iTokenService.checkToken(tokenModel)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("token已过期");
        }
    }
}
