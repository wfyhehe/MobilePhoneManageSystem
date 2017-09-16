package com.wfy.web.service.impl;

import com.wfy.web.dao.InfoDao;
import com.wfy.web.model.Info;
import com.wfy.web.service.IInfoService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iInfoService")
@Transactional
public class InfoServiceImpl implements IInfoService {

    @Resource
    private InfoDao infoDao;

    @Override
    public List<Info> getInfos(String position) {
        return infoDao.getInfos(position);
    }

    @Override
    public Info addInfo(Info info) throws Exception {
        String id = infoDao.save(info);
        info.setId(id);
        return info;
    }

    @Override
    public void updateInfo(Info info) {
        infoDao.update(info);
    }

    @Override
    public Info getInfoById(String id) {
        return infoDao.getInfoById(id);
    }

    @Override
    public void delete(String id) {
        infoDao.delete(new Info(id));
    }
}
