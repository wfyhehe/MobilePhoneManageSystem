package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Config;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public interface IConfigService {
    List<Config> getConfigs();

    boolean addConfig(Config config);

    Config getConfig(String name);

    ServerResponse<String> delete(String name);
}
