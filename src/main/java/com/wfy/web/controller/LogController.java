package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Log;
import com.wfy.web.model.enums.LogStatus;
import com.wfy.web.service.ILogService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/log/")
public class LogController {

    @Resource
    private ILogService iLogService;

    @RequestMapping(value = "get_logs.do", method = RequestMethod.POST)
    public ServerResponse<List<Log>> getLogs(@RequestBody Map<String, Object> map) {
        String statusStr = (String) map.get("status");
        LogStatus status = null;
        String actionUrl = (String) map.get("actionUrl");
        String username = (String) map.get("username");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        Long startTimeL = (Long) map.get("startTime");
        Long endTimeL = (Long) map.get("endTime");
        Date startTime = (startTimeL != null) ? new Date(startTimeL) : null;
        Date endTime = (endTimeL != null) ? new Date(endTimeL) : null;
        switch (statusStr != null ? statusStr : "") {
            case "ACCEPTED": {
                status = LogStatus.ACCEPTED;
                break;
            }
            case "UNAUTHENTICATED": {
                status = LogStatus.UNAUTHENTICATED;
                break;
            }
            case "UNAUTHORIZED": {
                status = LogStatus.UNAUTHORIZED;
                break;
            }
        }
        RefCount refCount = new RefCount(0);
        List<Log> logs;
        logs = iLogService.getLogs(refCount, startTime, endTime, status, actionUrl, username,
                pageIndex, pageSize);
        if (logs != null) {
            ServerResponse<List<Log>> response = ServerResponse.createBySuccess(logs);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取日志失败");
        }
    }

    @RequestMapping(value = "get_log.do", method = RequestMethod.GET)
    public ServerResponse<Log> getLog(String id) {
        Log log = iLogService.getLogById(id);
        if (log != null) {
            return ServerResponse.createBySuccess(log);
        } else {
            return ServerResponse.createByErrorMessage("获取日志失败");
        }
    }

    @RequestMapping(value = "update_log.do", method = RequestMethod.POST)
    public ServerResponse<String> updateLog(@RequestBody Log log) {
        try {
            System.out.println(log);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_log.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iLogService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
