package com.wfy.web.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/8/13.
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    public RedisDao(String ip, int port) {

    }

}
