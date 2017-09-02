package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
import com.wfy.web.service.IBrandService;
import com.wfy.web.service.IMobileModelService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/mobile_model/")
public class MobileModelController {

    @Resource
    private IMobileModelService iMobileModelService;

    @Resource
    private IBrandService iBrandService;

    @RequestMapping(value = "get_mobile_models.do", method = RequestMethod.POST)
    public ServerResponse<List<MobileModel>> getMobileModels(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String type = (String) map.get("type");
        int pageIndex = (int) map.get("pageIndex");
        int pageSize = (int) map.get("pageSize");
        RefCount refCount = new RefCount(0);
        List<MobileModel> mobileModels = iMobileModelService.getMobileModels(refCount, name, type, pageIndex,
                pageSize);
        //noinspection Duplicates
        if (mobileModels != null) {
            ServerResponse response = ServerResponse.createBySuccess(mobileModels);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取手机型号失败");
        }
    }

    @RequestMapping(value = "add_mobile_model.do", method = RequestMethod.POST)
    public ServerResponse<MobileModel>
    addMobileModel(@RequestBody Map<String, Object> mobileModelMap) {
        MobileModel mobileModel = new MobileModel();
        String id = (String) mobileModelMap.get("id");
        String name = (String) mobileModelMap.get("name");
        String brand = (String) mobileModelMap.get("brand");
        double buyingPrice = (double) mobileModelMap.get("buyingPrice");
        List<RebatePrice> rebatePrices = (List<RebatePrice>) mobileModelMap.get("rebatePrices");
        String remark = (String) mobileModelMap.get("remark");
        mobileModel.setId(id);
        mobileModel.setName(name);
        mobileModel.setBrand(iBrandService.getBrand(brand));
        mobileModel.setBuyingPrice(buyingPrice);
        System.out.println(rebatePrices);
        mobileModel.setRebatePrices(rebatePrices);
        mobileModel.setRemark(remark);
        mobileModel.setDeleted(false);
        try {
            mobileModel = iMobileModelService.addMobileModel(mobileModel);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
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
    public ServerResponse<String> updateMobileModel(@RequestBody Map<String, Object> mobileModelMap) {
        MobileModel mobileModel = new MobileModel();
        String id = (String) mobileModelMap.get("id");
        String name = (String) mobileModelMap.get("name");
        String brand = (String) mobileModelMap.get("brand");
        double buyingPrice = (double) mobileModelMap.get("buyingPrice");
        List<RebatePrice> rebatePrices = (List<RebatePrice>) mobileModelMap.get("rebatePrices");
        String remark = (String) mobileModelMap.get("remark");
        mobileModel.setId(id);
        mobileModel.setName(name);
        mobileModel.setBrand(iBrandService.getBrand(brand));
        mobileModel.setBuyingPrice(buyingPrice);
        System.out.println(rebatePrices);
        mobileModel.setRebatePrices(rebatePrices);
        mobileModel.setRemark(remark);
        mobileModel.setDeleted(false);
        try {
            iMobileModelService.updateMobileModel(mobileModel);
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
