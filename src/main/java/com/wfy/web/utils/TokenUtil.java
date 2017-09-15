package com.wfy.web.utils;

import com.wfy.web.common.Const;
import com.wfy.web.model.Token;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/9/14, good luck.
 */
public class TokenUtil {
    public static Token getTokenfromRequest(HttpServletRequest request) {
        String header = request.getHeader(Const.AUTHORIZATION);
        if (StringUtils.isBlank(header)) {
            return null;
        }
        return Token.parse(header);
    }
}
