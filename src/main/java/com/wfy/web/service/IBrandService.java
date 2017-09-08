package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Brand;
import com.wfy.web.model.MobileStock;
import com.wfy.web.utils.RefCount;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public interface IBrandService {
    List<Brand> getBrands();

    boolean addBrand(Brand brand);

    Brand getBrand(String name);

    ServerResponse<String> delete(String name);

    /**
     * Created by Administrator on 2017/9/6, good luck.
     */
    interface IMobileStockService {
        List<MobileStock> getMobileStocks(RefCount refCount, Date startTime, Date endTime, String
                supplier, String mobileModel, Integer pageIndex, Integer pageSize);

        MobileStock addMobileStock(MobileStock mobileStock);

        boolean recover(String id);

        long countMobileStock();

        void updateMobileStock(MobileStock mobileStock);

        MobileStock getMobileStockById(String id);

        boolean delete(String id);

        boolean idExists(String id);
    }
}
