package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.dao.UserDao;
import com.wfy.web.dao.cache.TokenDao;
import com.wfy.web.model.Token;
import com.wfy.web.service.ITokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service("iTokenService")
@Transactional
public class TokenServiceImpl implements ITokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private TokenDao tokenDao;

    @Resource
    private UserDao userDao;

    @Override
    public Token createToken(String userId) {
        String uuid = UUID.randomUUID().toString();
        if (userDao.isSuperAdmin(userId)) {
            char[] buffer = uuid.toCharArray();
            buffer[uuid.length() - 1] = '-'; // 超级管理员的token最后一位设为-, 方便前端判断（在后端无特权）
            uuid = String.valueOf(buffer);
        }
        Token token = new Token(userId, uuid);
        tokenDao.setToken(token, Const.TOKEN_EXPIRES_MINUTE, TimeUnit.MINUTES);
        return token;
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

    @Override
    public boolean checkToken(Token token) {
        if (!tokenDao.checkToken(token)) {
            return false;
        }
        //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
        tokenDao.expireToken(token, Const.TOKEN_EXPIRES_MINUTE, TimeUnit
                .MINUTES);
        return true;
    }

    @Override
    public void deleteToken(String userId) {
        tokenDao.deleteToken(userId);
    }
}
