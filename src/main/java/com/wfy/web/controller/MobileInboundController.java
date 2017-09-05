package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.MobileInbound;
import com.wfy.web.service.IBrandService;
import com.wfy.web.service.IMobileInboundService;
import com.wfy.web.service.IRebatePriceService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/mobile_inbound/")
public class MobileInboundController {

    @Resource
    private IMobileInboundService iMobileInboundService;

    @Resource
    private IBrandService iBrandService;

    @Resource
    private IRebatePriceService iRebatePriceService;

    @RequestMapping(value = "get_mobile_inbounds.do", method = RequestMethod.POST)
    public ServerResponse<List<MobileInbound>> getMobileInbounds(@RequestBody Map<String, Object> map) {
        String supplier = (String) map.get("supplier");
        String mobileModel = (String) map.get("mobileModel");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        Long startTimeL = (Long) map.get("startTime");
        Long endTimeL = (Long) map.get("endTime");
        Date startTime = (startTimeL != null) ? new Date(startTimeL) : null;
        Date endTime = (endTimeL != null) ? new Date(endTimeL) : null;
        RefCount refCount = new RefCount(0);
        List<MobileInbound> mobileInbounds;
        mobileInbounds = iMobileInboundService.getMobileInbounds(refCount, startTime, endTime, supplier, mobileModel, pageIndex, pageSize);

        //noinspection Duplicates
        if (mobileInbounds != null) {
            ServerResponse<List<MobileInbound>> response = ServerResponse.createBySuccess(mobileInbounds);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "add_mobile_inbound.do", method = RequestMethod.POST)
    public ServerResponse<MobileInbound>
    addMobileInbound(@RequestBody MobileInbound mobileInbound) {
        try {
            iMobileInboundService.addMobileInbound(mobileInbound);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccess(mobileInbound);
    }

    @RequestMapping(value = "recover_mobile_inbound.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverMobileInbound(String id) {
        if (iMobileInboundService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_mobile_inbound.do", method = RequestMethod.GET)
    public ServerResponse<MobileInbound> getMobileInbound(String id) {
        MobileInbound mobileInbound = iMobileInboundService.getMobileInboundById(id);
        if (mobileInbound != null) {
            return ServerResponse.createBySuccess(mobileInbound);
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "update_mobile_inbound.do", method = RequestMethod.POST)
    public ServerResponse<String> updateMobileInbound(@RequestBody MobileInbound mobileInbound) {
        try {
            System.out.println(mobileInbound);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_mobile_inbound.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iMobileInboundService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "check_mobile_inbound.do", method = RequestMethod.GET)
    public ServerResponse<String> checkId(String id) {
        if (iMobileInboundService.idExists(id)) {
            return ServerResponse.createByErrorMessage("编号已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
    }

}
