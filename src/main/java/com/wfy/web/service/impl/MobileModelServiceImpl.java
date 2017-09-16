package com.wfy.web.service.impl;

import com.wfy.web.dao.MobileModelDao;
import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
import com.wfy.web.service.IMobileModelService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iMobileModelService")
@Transactional
public class MobileModelServiceImpl implements IMobileModelService {

    @Resource
    private MobileModelDao mobileModelDao;

    @SuppressWarnings("Duplicates")
    @Override
    public List<MobileModel> getMobileModels(RefCount refCount, String name, String brand,
                                             Integer pageIndex, Integer pageSize) {
        return mobileModelDao.search(refCount, name, brand, pageIndex, pageSize);
    }

    @Override
    public List<MobileModel> getDeletedMobileModels() {
        return mobileModelDao.getDeleted();
    }

    @Override
    public MobileModel addMobileModel(MobileModel mobileModel) {
        String id = mobileModelDao.save(mobileModel);
        mobileModel.setId(id);
        return mobileModel;
    }

    @Override
    public boolean recover(String id) {
        return mobileModelDao.recover(id);
    }

    @Override
    public long countMobileModel() {
        return mobileModelDao.count();
    }

    @Override
    public void updateMobileModel(MobileModel mobileModel) {
        mobileModelDao.update(mobileModel);
    }

    @Override
    public MobileModel getMobileModelById(String id) {
        return mobileModelDao.getMobileModelById(id);
    }

    @Override
    public MobileModel getMobileModelByName(String name) {
        return mobileModelDao.getMobileModelByName(name);
    }

    @Override
    public boolean delete(String id) {
        MobileModel mobileModel = mobileModelDao.getMobileModelById(id);
        if (mobileModel == null) {
            return false;
        }
        mobileModel.setDeleted(true);
        for (RebatePrice rebatePrice: mobileModel.getRebatePrices()) {
            rebatePrice.setMobileModel(mobileModel);
        }
        mobileModelDao.update(mobileModel);
        return true;
    }

    @Override
    public boolean idExists(String id) {
        return mobileModelDao.idExists(id);
    }

}
