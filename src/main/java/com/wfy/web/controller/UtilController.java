package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.service.IVCodeService;
import com.wfy.web.utils.VerifyCodeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/9.
 */
@RestController
@RequestMapping("/util/")
public class UtilController {

    @Resource
    private IVCodeService ivCodeService;

    @RequestMapping("v-code.do")
    public void getVCode(HttpSession session, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        boolean cookieExists = false;

        //生成随机字串
        String verificationCode = VerifyCodeUtils.generateVerifyCode(4);
        //删除以前的
        String uuid = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equalsIgnoreCase(Const.COOKIE_NAME_VCODE_ID)) {
                cookieExists = true;
                uuid = cookie.getValue();
            }
        }
        if (!cookieExists) {
            uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(Const.COOKIE_NAME_VCODE_ID, uuid);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        ivCodeService.createOrUpdateVCode(uuid, verificationCode); // 存入redis
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verificationCode);
    }


}
