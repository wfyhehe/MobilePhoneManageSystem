package com.wfy.web.common;

/**
 * Created by Administrator on 2017/8/8.
 */
public class Const {
    public static final String FRONT_END_URL = "http://localhost:3000";
    public static final String VERIFICATION_CODE = "verificationCode";
    public static final int TOKEN_EXPIRES_HOUR = 1;
    public static final int TOKEN_EXPIRES_MINUTE = 30;
    public static final int VCODE_EXPIRES_MINUTE = 5;
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN_SEPARATOR = "#";
    public static final String SIGN_IN_URL = "/auth/sign_in.do";
    public static final String SIGN_UP_URL = "/auth/sign_up.do";
    public static final String GET_INFO_URL = "/info/get_infos.do";
    public static final String VCODE_URL = "/util/v-code.do";
    public static final String CHECK_USERNAME_URL = "/user/check_username.do";
    public static final String COOKIE_NAME_VCODE_ID = "vCodeId";

}
