package com.wfy.web.service.impl;

import com.wfy.web.dao.MobileStockDao;
import com.wfy.web.model.MobileStock;
import com.wfy.web.service.IBrandService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iMobileStockService")
@Transactional
public class MobileStockServiceImpl implements IBrandService.IMobileStockService {

    @Resource
    private MobileStockDao mobileStockDao;

    @Override
    public List<MobileStock> getMobileStocks(RefCount refCount, Date startTime, Date endTime, String
            supplier, String mobileModel, Integer pageIndex, Integer pageSize) {
        return mobileStockDao.search(refCount, startTime, endTime, supplier, mobileModel, pageIndex,
                pageSize);
    }

    @Override
    public MobileStock addMobileStock(MobileStock mobileStock) {
        mobileStockDao.save(mobileStock);
        return mobileStock;
    }

    @Override
    public boolean recover(String id) {
        return mobileStockDao.recover(id);
    }

    @Override
    public long countMobileStock() {
        return mobileStockDao.count();
    }

    @Override
    public void updateMobileStock(MobileStock mobileStock) {
        mobileStockDao.update(mobileStock);
    }

    @Override
    public MobileStock getMobileStockById(String id) {
        return mobileStockDao.getMobileStockById(id);
    }

    @Override
    public boolean delete(String id) {
        //undeletable
        return false;
    }

    @Override
    public boolean idExists(String id) {
        return mobileStockDao.idExists(id);
    }

}
