package com.wfy.web.service.impl;

import com.wfy.web.dao.MobileInboundDao;
import com.wfy.web.model.MobileInbound;
import com.wfy.web.service.IMobileInboundService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iMobileInboundService")
@Transactional
public class MobileInboundServiceImpl implements IMobileInboundService {

    @Resource
    private MobileInboundDao mobileInboundDao;

    @Override
    public List<MobileInbound> getMobileInbounds(RefCount refCount, Date startTime, Date endTime, String
            supplier, String mobileModel, Integer pageIndex, Integer pageSize) {
        return mobileInboundDao.search(refCount, startTime, endTime, supplier, mobileModel, pageIndex,
                pageSize);
    }

    @Override
    public MobileInbound addMobileInbound(MobileInbound mobileInbound) {
        String id = mobileInboundDao.save(mobileInbound);
        mobileInbound.setId(id);
        return mobileInbound;
    }

    @Override
    public boolean recover(String id) {
        return mobileInboundDao.recover(id);
    }

    @Override
    public long countMobileInbound() {
        return mobileInboundDao.count();
    }

    @Override
    public void updateMobileInbound(MobileInbound mobileInbound) {
        mobileInboundDao.update(mobileInbound);
    }

    @Override
    public MobileInbound getMobileInboundById(String id) {
        return mobileInboundDao.getMobileInboundById(id);
    }

    @Override
    public MobileInbound getMobileInboundByName(String name) {
        return mobileInboundDao.getMobileInboundByName(name);
    }

    @Override
    public boolean delete(String id) {
        //undeletable
        return false;
    }

    @Override
    public boolean idExists(String id) {
        return mobileInboundDao.idExists(id);
    }

}
