package com.wfy.web.service;

import com.wfy.web.model.RebatePrice;

/**
 * Created by Administrator on 2017/9/3.
 */
public interface IRebatePriceService {
    RebatePrice addRebatePrice(RebatePrice rebatePrice);

    RebatePrice getRebatePriceById(String id);

    boolean delete(String id);
}
