package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Info;
import com.wfy.web.service.IInfoService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/info/")
public class InfoController {

    @Resource
    private IInfoService iInfoService;

    @RequestMapping(value = "get_infos.do", method = RequestMethod.GET)
    public ServerResponse<List<Info>> getInfos(String position) {
        List<Info> infos = iInfoService.getInfos(position);
        if (infos != null) {
            return ServerResponse.createBySuccess(infos);
        } else {
            return ServerResponse.createByErrorMessage("获取信息失败");
        }
    }

    @RequestMapping(value = "get_info.do", method = RequestMethod.GET)
    public ServerResponse<Info> getInfo(String id) {
        Info info = iInfoService.getInfoById(id);
        if (info != null) {
            return ServerResponse.createBySuccess(info);
        } else {
            return ServerResponse.createByErrorMessage("获取信息失败");
        }
    }

    @RequestMapping(value = "add_info.do", method = RequestMethod.POST)
    public ServerResponse<String> addInfo(@RequestBody Info info) {
        try {
            iInfoService.addInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "update_info.do", method = RequestMethod.POST)
    public ServerResponse<String> updateInfo(@RequestBody Info info) {
        try {
            iInfoService.updateInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_info.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        try {
            iInfoService.delete(id);
        } catch(Exception e) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        return ServerResponse.createBySuccess();
    }
}
