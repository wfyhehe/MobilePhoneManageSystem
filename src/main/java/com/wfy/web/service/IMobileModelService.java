package com.wfy.web.service;

import com.wfy.web.model.MobileModel;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public interface IMobileModelService {
    @SuppressWarnings("Duplicates")
    List<MobileModel> getMobileModels(RefCount refCount, String name, String brand, Integer pageIndex,
                                      Integer pageSize);

    List<MobileModel> getDeletedMobileModels();

    MobileModel addMobileModel(MobileModel mobileModel);

    boolean recover(String id);

    long countMobileModel();

    void updateMobileModel(MobileModel mobileModel);

    MobileModel getMobileModelById(String id);

    MobileModel getMobileModelByName(String name);

    boolean delete(String id);

    boolean idExists(String id);

}
