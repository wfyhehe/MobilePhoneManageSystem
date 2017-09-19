package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
import com.wfy.web.service.IBrandService;
import com.wfy.web.service.IMobileModelService;
import com.wfy.web.service.IRebatePriceService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@RestController
@RequestMapping("/mobile_model/")
public class MobileModelController {

    @Resource
    private IMobileModelService iMobileModelService;

    @Resource
    private IBrandService iBrandService;

    @Resource
    private IRebatePriceService iRebatePriceService;

    @RequestMapping(value = "get_mobile_models.do", method = RequestMethod.POST)
    public ServerResponse<List<MobileModel>> getMobileModels(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String brand = (String) map.get("brand");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        RefCount refCount = new RefCount(0);
        List<MobileModel> mobileModels;
        mobileModels = iMobileModelService.getMobileModels(refCount, name, brand, pageIndex, pageSize);

        //noinspection Duplicates
        if (mobileModels != null) {
            ServerResponse<List<MobileModel>> response = ServerResponse.createBySuccess(mobileModels);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "add_mobile_model.do", method = RequestMethod.POST)
    public ServerResponse<MobileModel>
    addMobileModel(@RequestBody MobileModel mobileModel) {
        // 先持久化MobileModel，再持久化中间实体RebatePrice
        try {
            List<RebatePrice> rebatePrices = new ArrayList<>(mobileModel.getRebatePrices());
            for (int i = 0; i < rebatePrices.size(); i++) {
                RebatePrice rebatePrice = rebatePrices.get(i);
                System.out.println(rebatePrice);
                rebatePrices.set(i, rebatePrice);
            }
            mobileModel.setRebatePrices(null); // 中间实体先设空，通过mappedBy从RebatePrice方向关联
            iMobileModelService.addMobileModel(mobileModel);
            System.out.println(mobileModel);
            for (RebatePrice rebatePrice : rebatePrices) {
                iRebatePriceService.addRebatePrice(rebatePrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccess(mobileModel);
    }

    @RequestMapping(value = "get_deleted_mobile_models.do", method = RequestMethod.GET)
    public ServerResponse<List<MobileModel>> getDeletedMobileModels() {
        List<MobileModel> mobileModels = iMobileModelService.getDeletedMobileModels();
        if (mobileModels != null) {
            return ServerResponse.createBySuccess(mobileModels);
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "recover_mobile_model.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverMobileModel(String id) {
        if (iMobileModelService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_mobile_model.do", method = RequestMethod.GET)
    public ServerResponse<MobileModel> getMobileModel(String id) {
        MobileModel mobileModel = iMobileModelService.getMobileModelById(id);
        if (mobileModel != null) {
            return ServerResponse.createBySuccess(mobileModel);
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "update_mobile_model.do", method = RequestMethod.POST)
    public ServerResponse<String> updateMobileModel(@RequestBody MobileModel mobileModel) {
        try {
            List<RebatePrice> rebatePrices = new ArrayList<>(mobileModel.getRebatePrices());
            for (int i = 0; i < rebatePrices.size(); i++) {
                RebatePrice rebatePrice = rebatePrices.get(i);
                System.out.println(rebatePrice);
                rebatePrices.set(i, rebatePrice);
            }
            mobileModel.setRebatePrices(null); // 中间实体先设空，通过mappedBy从RebatePrice方向关联
            iRebatePriceService.deleteByMobileModel(mobileModel); // 先删除中间表的所有该MobileModel的关联
            iMobileModelService.updateMobileModel(mobileModel);
            System.out.println(mobileModel);
            for (RebatePrice rebatePrice : rebatePrices) {
                iRebatePriceService.addRebatePrice(rebatePrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_mobile_model.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iMobileModelService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "check_mobile_model.do", method = RequestMethod.GET)
    public ServerResponse<String> checkId(String id) {
        if (iMobileModelService.idExists(id)) {
            return ServerResponse.createByErrorMessage("编号已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
    }

}
