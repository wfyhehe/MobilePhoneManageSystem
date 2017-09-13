package com.wfy.web.dao.cache;

import com.wfy.web.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/13.
 */
@Repository
public class TokenDao {

    // inject the actual template
    @Resource
    private RedisTemplate<String, Token> template;

    public void setToken(Token token, long time, TimeUnit timeUnit) {
        template.boundValueOps(token.getUserId()).set(token, time, timeUnit);
    }

    public Token getToken(String userId) {
        return template.boundValueOps(userId).get();
    }

    public boolean checkToken(Token token) {
        if (token == null
                || StringUtils.isBlank(token.getUserId())
                || StringUtils.isBlank(token.getCredentials())) {
            return false;
        }
        Token tokenInRedis = template.boundValueOps(token.getUserId()).get();
        return token.equals(tokenInRedis);

    }

    public void deleteToken(String userId) {
        template.delete(userId);
    }

    public void expireToken(Token token, long time, TimeUnit timeUnit) {
        template.boundValueOps(token.getUserId()).expire(time, timeUnit);
    }

}
