package com.wfy.web.service.impl;

import com.wfy.web.dao.LogDao;
import com.wfy.web.model.Log;
import com.wfy.web.model.enums.LogStatus;
import com.wfy.web.service.ILogService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iLogService")
@Transactional
public class LogServiceImpl implements ILogService {

    @Resource
    private LogDao logDao;

    @Override
    public List<Log> getLogs(RefCount refCount, Date startTime, Date endTime, LogStatus
            status, String actionUrl, String username, Integer pageIndex, Integer pageSize) {
        return logDao.search(refCount, startTime, endTime, status, actionUrl, username, pageIndex,
                pageSize);
    }

    @Override
    public Log addLog(Log log) {
        String id = logDao.save(log);
        log.setId(id);
        return log;
    }

    @Override
    public void updateLog(Log log) {
        logDao.update(log);
    }

    @Override
    public Log getLogById(String id) {
        return logDao.getLogById(id);
    }

    @Override
    public boolean delete(String id) {
        //undeletable
        return false;
    }
}
