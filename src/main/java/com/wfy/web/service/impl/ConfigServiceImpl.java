package com.wfy.web.service.impl;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.ConfigDao;
import com.wfy.web.model.Config;
import com.wfy.web.service.IConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Service("iConfigService")
@Transactional
public class ConfigServiceImpl implements IConfigService {

    @Resource
    private ConfigDao configDao;

    @Override
    public List<Config> getConfigs() {
        return configDao.getAll();
    }

    @Override
    public boolean addConfig(Config config) {
        if (configDao.exists(config.getName())) {
            return false;
        }
        configDao.save(config);
        return true;
    }

    @Override
    public Config getConfig(String name) {
        return configDao.getConfig(name);
    }

    @Override
    public ServerResponse<String> delete(String name) {
        Config config = new Config(name);
        if (!configDao.exists(name)) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if(configDao.isInUse(config)) {
            return ServerResponse.createByErrorMessage("若要删除该配置，请先修改所有使用该配置的手机的配置");
        }
        configDao.delete(config);
        return ServerResponse.createBySuccess();
    }
}
