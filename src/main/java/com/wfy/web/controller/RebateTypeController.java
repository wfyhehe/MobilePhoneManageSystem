package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.RebateType;
import com.wfy.web.service.IRebateTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/rebate_type/")
public class RebateTypeController {

    @Resource
    private IRebateTypeService iRebateTypeService;

    @RequestMapping(value = "get_rebate_types.do", method = RequestMethod.POST)
    public ServerResponse<List<RebateType>> getRebateTypes(@RequestBody Map<String, Object> map) {
        List<RebateType> rebateTypes = iRebateTypeService.getRebateTypes();
        if (rebateTypes != null) {
            return ServerResponse.createBySuccess(rebateTypes);
        } else {
            return ServerResponse.createByErrorMessage("获取返利类型失败");
        }
    }

    @RequestMapping(value = "add_rebate_type.do", method = RequestMethod.POST)
    public ServerResponse<RebateType> addMenu(@RequestBody Map<String, Object> rebateTypeMap) {
        RebateType rebateType = new RebateType();
        String id = (String) rebateTypeMap.get("id");
        String name = (String) rebateTypeMap.get("name");
        String remark = (String) rebateTypeMap.get("remark");
        rebateType.setId(id);
        rebateType.setName(name);
        rebateType.setRemark(remark);
        try {
            rebateType = iRebateTypeService.addRebateType(rebateType);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(rebateType);
    }

    @RequestMapping(value = "get_deleted_rebate_types.do", method = RequestMethod.GET)
    public ServerResponse<List<RebateType>> getDeletedRebateTypes() {
        List<RebateType> rebateTypes = iRebateTypeService.getDeletedRebateTypes();
        if (rebateTypes != null) {
            return ServerResponse.createBySuccess(rebateTypes);
        } else {
            return ServerResponse.createByErrorMessage("获取返利类型失败");
        }
    }

    @RequestMapping(value = "recover_rebate_type.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverRebateType(String id) {
        if (iRebateTypeService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_rebate_type.do", method = RequestMethod.GET)
    public ServerResponse<RebateType> getRebateType(String id) {
        RebateType rebateType = iRebateTypeService.getRebateTypeById(id);
        if (rebateType != null) {
            return ServerResponse.createBySuccess(rebateType);
        } else {
            return ServerResponse.createByErrorMessage("获取返利类型失败");
        }
    }

    @RequestMapping(value = "update_rebate_type.do", method = RequestMethod.POST)
    public ServerResponse<String> updateRebateType(@RequestBody Map<String, Object> rebateTypeMap) {
        RebateType rebateType = new RebateType();
        String id = (String) rebateTypeMap.get("id");
        String name = (String) rebateTypeMap.get("name");
        String remark = (String) rebateTypeMap.get("remark");
        rebateType.setId(id);
        rebateType.setName(name);
        rebateType.setRemark(remark);
        try {
            rebateType.setDeleted(false);
            iRebateTypeService.updateRebateType(rebateType);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_rebate_type.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iRebateTypeService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @RequestMapping(value = "check_rebate_type.do", method = RequestMethod.GET)
    public ServerResponse<String> checkId(String id) {
        if (iRebateTypeService.idExists(id)) {
            return ServerResponse.createByErrorMessage("编号已存在");
        } else {
            return ServerResponse.createBySuccess();
        }
    }
}
