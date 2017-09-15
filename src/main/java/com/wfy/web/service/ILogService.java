package com.wfy.web.service;

import com.wfy.web.model.Log;
import com.wfy.web.model.enums.LogStatus;
import com.wfy.web.utils.RefCount;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/14, good luck.
 */
public interface ILogService {
    List<Log> getLogs(RefCount refCount, Date startTime, Date endTime, List<LogStatus>
            status, String actionUrl, String username, Integer pageIndex, Integer pageSize);

    Log addLog(Log log) throws Exception;

    void updateLog(Log log);

    Log getLogById(String id);

    boolean delete(String id);
}
