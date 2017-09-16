package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.dto.UserSignUpDto;
import com.wfy.web.exceptions.UsernameExistsException;
import com.wfy.web.exceptions.UsernameNotExistsException;
import com.wfy.web.exceptions.WrongPasswordException;
import com.wfy.web.exceptions.WrongVCodeException;
import com.wfy.web.model.Token;
import com.wfy.web.model.User;
import com.wfy.web.model.VCode;
import com.wfy.web.service.IAuthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/auth/")
public class AuthController {

    @Resource
    private IAuthService iAuthService;

    private String getVCodeIdFromCookie(HttpServletRequest request) {
        String vCodeId = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(Const.COOKIE_NAME_VCODE_ID)) {
                vCodeId = cookie.getValue();
            }
        }
        return vCodeId;
    }

    @RequestMapping(value = "sign_in.do", method = RequestMethod.POST)
    public ServerResponse<Token> signIn(@RequestBody Map<String, String> userMap,
                                        HttpServletRequest request) {
        Token token;
        String username = userMap.get("username");
        String password = userMap.get("password");
        String vCodeId = getVCodeIdFromCookie(request);
        String vCodeCredentials = userMap.get("vCode");
        VCode vCode = new VCode(vCodeId, vCodeCredentials);
        try {
            token = iAuthService.signIn(username, password, vCode);
        } catch (UsernameNotExistsException | WrongPasswordException | WrongVCodeException e) {
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(token);
    }


    @RequestMapping(value = "sign_out.do", method = RequestMethod.GET)
    public ServerResponse<String> signOut(HttpServletRequest request) {
        String id = Token.parse(request.getHeader(Const.AUTHORIZATION)).getUserId();
        iAuthService.signOut(id);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "sign_up.do", method = RequestMethod.POST)
    public ServerResponse<String> signUp(@RequestBody UserSignUpDto userSignUpDto,
                                         HttpServletRequest request) {
        String vCodeId = getVCodeIdFromCookie(request);
        String vCodeCredentials = userSignUpDto.getvCode();
        VCode vCode = new VCode(vCodeId, vCodeCredentials);
        try {
            iAuthService.signUp(userSignUpDto.getUser(), vCode);
        } catch (WrongVCodeException | UsernameExistsException e) { // 自定义的RuntimeEx 无需打印错误信息
            return ServerResponse.createByErrorMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }


    /* @RequestMapping(value = "forget_reset_password.do", method = RequestMethod.GET)
     public ServerResponse<String> forgetResetPassword(String username, String
             passwordNew, String forgetToken) {
         return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
     }
 */
    @RequestMapping(value = "reset_password.do", method = RequestMethod.POST)
    public ServerResponse<String> resetPassword(@RequestBody Map<String, String> passwordMap,
                                                HttpServletRequest request) {
        String passwordOld = passwordMap.get("passwordOld");
        String passwordNew = passwordMap.get("passwordNew");
        String userId = Token.parse(request.getHeader(Const.AUTHORIZATION)).getUserId();
        try {
            iAuthService.resetPassword(passwordOld, passwordNew, userId);
        } catch (WrongPasswordException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "test.do", method = RequestMethod.POST)
    public void test(@ModelAttribute(value = "user") User user, String vCode) {
        System.out.println(user);
        System.out.println(vCode);
    }

}
