package com.wfy.web.service.impl;

import com.wfy.web.common.Const;
import com.wfy.web.dao.UserDao;
import com.wfy.web.dao.cache.VCodeDao;
import com.wfy.web.model.VCode;
import com.wfy.web.service.IVCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/14.
 */
@Service("iVCodeService")
@Transactional
public class VCodeServiceImpl implements IVCodeService {

    @Resource
    private VCodeDao vCodeDao;

    @Override
    public VCode createOrUpdateVCode(String userId, String credentials) {
        VCode vCode = new VCode(userId, credentials);
        vCodeDao.setVCode(vCode, Const.VCODE_EXPIRES_MINUTE, TimeUnit.MINUTES);
        return vCode;
    }

//    public VCode getVCode(String authentication) {
//        if (authentication == null || authentication.length() == 0) {
//            return null;
//        }
//        String[] param = authentication.split("_");
//        if (param.length != 2) {
//            return null;
//        }
//        //使用userId和源vCode简单拼接成的vCode，可以增加加密措施
//        String userId = param[0];
//        String vCode = param[1];
//        return new VCode(userId, vCode);
//    }

    @Override
    public boolean checkVCode(VCode vCode) {
        return vCodeDao.checkVCode(vCode);
    }


}
