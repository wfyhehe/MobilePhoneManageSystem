package com.wfy.web.service.impl;

import com.mysql.cj.core.util.StringUtils;
import com.wfy.web.dao.RebatePriceDao;
import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
import com.wfy.web.service.IRebatePriceService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iRebatePriceService")
@Transactional
public class RebatePriceServiceImpl implements IRebatePriceService {

    @Resource
    private RebatePriceDao rebatePriceDao;

    @Override
    public RebatePrice addRebatePrice(RebatePrice rebatePrice) {
        String id = rebatePriceDao.save(rebatePrice);
        rebatePrice.setId(id);
        return rebatePrice;
    }
    
    @Override
    public RebatePrice getRebatePriceById(String id) {
        return rebatePriceDao.getRebatePriceById(id);
    }

    @Override
    public boolean delete(String id) {
        RebatePrice rebatePrice = rebatePriceDao.getRebatePriceById(id);
        if (rebatePrice == null) {
            return false;
        }
        rebatePriceDao.delete(rebatePrice);
        return true;
    }

    @Override
    public void deleteByMobileModel(MobileModel mobileModel) {
        rebatePriceDao.deleteByMobileModel(mobileModel);
    }
}
