package com.wfy.web.service;

import com.wfy.web.model.MobileInbound;
import com.wfy.web.utils.RefCount;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5, good luck.
 */
public interface IMobileInboundService {
    List<MobileInbound> getMobileInbounds(RefCount refCount, Date startTime, Date endTime,
                                          String name, String brand, Integer pageIndex, Integer pageSize);

    MobileInbound addMobileInbound(MobileInbound mobileInbound);

    boolean recover(String id);

    long countMobileInbound();

    void updateMobileInbound(MobileInbound mobileInbound);

    MobileInbound getMobileInboundById(String id);

    MobileInbound getMobileInboundByName(String name);

    boolean delete(String id);

    boolean idExists(String id);
}
