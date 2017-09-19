package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Config;
import com.wfy.web.service.IConfigService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@RestController
@RequestMapping("/config/")
public class ConfigController {

    @Resource
    private IConfigService iConfigService;

    @RequestMapping(value = "get_configs.do", method = RequestMethod.POST)
    public ServerResponse<List<Config>> getConfigs(@RequestBody Map<String, Object> map) {
        List<Config> configs = iConfigService.getConfigs();
        if (configs != null) {
            return ServerResponse.createBySuccess(configs);
        } else {
            return ServerResponse.createByErrorMessage("获取配置失败");
        }
    }

    @RequestMapping(value = "add_config.do", method = RequestMethod.POST)
    public ServerResponse<Config> addConfig(@RequestBody Map<String, Object> configMap) {
        Config config = new Config();
        String name = (String) configMap.get("name");
        config.setName(name);
        try {
            if (!iConfigService.addConfig(config)) {
                return ServerResponse.createByErrorMessage("已存在该配置");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(config);
    }

    @RequestMapping(value = "delete_config.do", method = RequestMethod.POST)
    public ServerResponse<String> delete(@RequestBody Map<String, Object> nameMap) {
        String name = (String) nameMap.get("name");
        return iConfigService.delete(name);
    }
}