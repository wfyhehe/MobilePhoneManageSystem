package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.dao.UserDao;
import com.wfy.web.dao.cache.TokenDao;
import com.wfy.web.model.Token;
import com.wfy.web.service.ITokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service("iTokenService")
@Transactional
public class TokenServiceImpl implements ITokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    public String createToken(String userId) {
        return tokenDao.createToken(userId, Const.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
    }

//    public Token getToken(String authentication) {
//        if (authentication == null || authentication.length() == 0) {
//            return null;
//        }
//        String[] param = authentication.split("_");
//        if (param.length != 2) {
//            return null;
//        }
//        //使用userId和源token简单拼接成的token，可以增加加密措施
//        String userId = param[0];
//        String token = param[1];
//        return new Token(userId, token);
//    }

    public boolean checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        tokenDao.expireToken(Token.parseUserId(token), Const.TOKEN_EXPIRES_MINUTE, TimeUnit
                .MINUTES);
        return true;
    }

    public void deleteToken(String userId) {
        tokenDao.deleteToken(userId);
    }
}
