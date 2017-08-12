package com.wfy.web.controller;

import com.wfy.web.common.Const;
import com.wfy.web.common.ServerResponse;
import com.wfy.web.utils.VerifyCodeUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2017/8/9.
 */
@CrossOrigin // 开发使用，发布时必须取消
@RestController
@RequestMapping("/util/")
public class UtilController {

    @RequestMapping("v-code.do")
    public void getVCode(HttpSession session, HttpServletResponse response) throws
            ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verificationCode = VerifyCodeUtils.generateVerifyCode(4);
        //删除以前的
        session.removeAttribute(Const.VERIFICATION_CODE);
        session.setAttribute(Const.VERIFICATION_CODE, verificationCode.toLowerCase());
        //生成图片
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verificationCode);
    }

    @RequestMapping(value = "check_v-code.do")
    public ServerResponse<String> checkVCode(String vCode, HttpSession session) {
        if (vCode.equals(session.getAttribute(Const.VERIFICATION_CODE))) {
            return ServerResponse.createBySuccessMessage("验证码正确");
        }
        return ServerResponse.createByErrorMessage("验证码错误");
    }

}
