package com.wfy.web.service;

import com.wfy.web.model.RebateType;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */
public interface IRebateTypeService {
    List<RebateType> getRebateTypes();

    List<RebateType> getDeletedRebateTypes();

    RebateType addRebateType(RebateType rebateType);

    boolean recover(String id);

    void updateRebateType(RebateType rebateType);

    RebateType getRebateTypeById(String id);

    RebateType getRebateTypeByName(String name);

    boolean delete(String id);

    boolean idExists(String id);
}
