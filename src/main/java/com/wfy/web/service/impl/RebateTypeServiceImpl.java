package com.wfy.web.service.impl;

import com.wfy.web.dao.RebateTypeDao;
import com.wfy.web.model.RebateType;
import com.wfy.web.service.IRebateTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iRebateTypeService")
@Transactional
public class RebateTypeServiceImpl implements IRebateTypeService {

    @Resource
    private RebateTypeDao rebateTypeDao;

    @Override
    public List<RebateType> getRebateTypes() {
        return rebateTypeDao.getAll();
    }

    @Override
    public List<RebateType> getDeletedRebateTypes() {
        return rebateTypeDao.getDeleted();
    }

    @Override
    public RebateType addRebateType(RebateType rebateType) {
        String id = rebateTypeDao.save(rebateType);
        rebateType.setId(id);
        return rebateType;
    }

    @Override
    public boolean recover(String id) {
        return rebateTypeDao.recover(id);
    }

    @Override
    public void updateRebateType(RebateType rebateType) {
        rebateTypeDao.update(rebateType);
    }

    @Override
    public RebateType getRebateTypeById(String id) {
        return rebateTypeDao.getRebateTypeById(id);
    }

    @Override
    public RebateType getRebateTypeByName(String name) {
        return rebateTypeDao.getRebateTypeByName(name);
    }

    @Override
    public boolean delete(String id) {
        RebateType rebateType = rebateTypeDao.getRebateTypeById(id);
        if (rebateType == null) {
            return false;
        }
        rebateType.setDeleted(true);
        rebateTypeDao.update(rebateType);
        return true;
    }

    @Override
    public boolean idExists(String id) {
        return rebateTypeDao.idExists(id);
    }
}
