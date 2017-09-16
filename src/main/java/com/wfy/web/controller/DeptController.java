package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Dept;
import com.wfy.web.service.IDeptService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
//@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/dept/")
public class DeptController {

    @Resource
    private IDeptService iDeptService;

    @RequestMapping(value = "get_depts.do", method = RequestMethod.GET)
    public ServerResponse<List<Dept>> getDepts() {
        List depts = iDeptService.getDepts();
        if (depts != null) {
            return ServerResponse.createBySuccess(depts);
        } else {
            return ServerResponse.createByErrorMessage("获取部门失败");
        }
    }
    @RequestMapping(value = "add_dept.do", method = RequestMethod.POST)
    public ServerResponse<Dept> addMenu(@RequestBody Map<String,Object> nameMap) {
        String name = (String) nameMap.get("name");
        String address = (String) nameMap.get("address");
        Dept dept;
        try {
            dept = iDeptService.addDept(name, address);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(dept);
    }
    @RequestMapping(value = "get_deleted_depts.do", method = RequestMethod.GET)
    public ServerResponse<List<Dept>> getDeletedDepts() {
        List depts = iDeptService.getDeletedDepts();
        if (depts != null) {
            return ServerResponse.createBySuccess(depts);
        } else {
            return ServerResponse.createByErrorMessage("获取部门失败");
        }
    }

    @RequestMapping(value="recover_dept.do",method = RequestMethod.GET)
    public ServerResponse<String> recoverDept(String id) {
        if(iDeptService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_dept.do", method = RequestMethod.GET)
    public ServerResponse<Dept> getDept(String id) {
        Dept dept = iDeptService.getDeptById(id);
        if (dept != null) {
            return ServerResponse.createBySuccess(dept);
        } else {
            return ServerResponse.createByErrorMessage("获取部门失败");
        }
    }

    @RequestMapping(value = "update_dept.do", method = RequestMethod.POST)
    public ServerResponse<String> updateDept(@RequestBody Dept dept) {
        try {
            dept.setDeleted(false);
            iDeptService.updateDept(dept);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_dept.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iDeptService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
