package com.wfy.web.dao.cache;

import com.wfy.web.model.Token;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
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

    public String createToken(String userId, long time, TimeUnit timeUnit) {
        String token = Token.getToken(userId);
        template.boundValueOps(userId).set(token, time, timeUnit);
        return token;
    }

    public String getToken(String userId) {
        return template.boundValueOps(userId).get();
    }

    public boolean checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        String tokenInRedis = template.boundValueOps(Token.parseUserId(token)).get();
        return !token.equals(tokenInRedis);

    }

    public void deleteToken(String userId) {
        template.delete(userId);
    }

    public void expireToken(String userId, long time, TimeUnit timeUnit) {
        template.boundValueOps(userId).expire(time, timeUnit);
    }

}
