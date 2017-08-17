package com.wfy.web.dao.cache;

import com.wfy.web.model.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/13.
 */
@Repository
public class TokenDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // inject the actual template
    @Resource
    private RedisTemplate<String, String> template;

    public TokenModel createToken(String userId, long time, TimeUnit timeUnit) {
        String token = UUID.randomUUID().toString();
        TokenModel tokenModel = new TokenModel(userId, token);
        logger.debug("createToken:");
        logger.debug("userId = " + userId);
        logger.debug("token = " + token);
        template.boundValueOps(userId).set(token, time, timeUnit);
        return tokenModel;
    }

    public TokenModel getToken(String userId) {
        String token = template.boundValueOps(userId).get();
        return new TokenModel(userId, token);
    }

    public boolean checkToken(TokenModel model) {
        if (model == null || model.getToken() == null || model.getUserId() == null) {
            return false;
        }
        String token = template.boundValueOps(model.getUserId()).get();
        if (token == null || !token.equals(model.getToken())) {
            return false;
        }
        return true;
    }

    public void deleteToken(String userId) {
        logger.debug("deleteToken:");
        logger.debug("userId = " + userId);
        template.delete(userId);
    }

    public void expireToken(String userId, long time, TimeUnit timeUnit) {
        template.boundValueOps(userId).expire(time, timeUnit);
    }
}
