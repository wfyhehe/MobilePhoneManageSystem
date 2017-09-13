package com.wfy.web.dao.cache;

import com.wfy.web.model.VCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/13.
 */
@Repository
public class VCodeDao {

    // inject the actual template
    @Resource
    private RedisTemplate<String, VCode> template;

    public void setVCode(VCode vCode, long time, TimeUnit timeUnit) {
        template.boundValueOps(vCode.getId()).set(vCode, time, timeUnit);
    }

    public VCode getVCode(String id) {
        return template.boundValueOps(id).get();
    }

    public boolean checkVCode(VCode vCode) {
        if (vCode == null
                || StringUtils.isBlank(vCode.getId())
                || StringUtils.isBlank(vCode.getCredentials())) {
            return false;
        }
        VCode vCodeInRedis = template.boundValueOps(vCode.getId()).get();
        System.out.println("vCodeInRedis: " + vCodeInRedis);
        return vCode.equals(vCodeInRedis);
    }

    public void deleteVCode(String id) {
        template.delete(id);
    }
}
