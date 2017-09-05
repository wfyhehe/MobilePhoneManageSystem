package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Account;
import com.wfy.web.model.Dept;
import com.wfy.web.service.IAccountService;
import com.wfy.web.service.IDeptService;
import com.wfy.web.utils.RefCount;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26, good luck.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/account/")
public class AccountController {

    @Resource
    private IAccountService iAccountService;

    @Resource
    private IDeptService iDeptService;

    @RequestMapping(value = "get_accounts.do", method = RequestMethod.POST)
    public ServerResponse<List<Account>> getAccounts(@RequestBody Map<String, Object> map) {
        String name = (String) map.get("name");
        String dept = (String) map.get("dept");
        Integer pageIndex = (Integer) map.get("pageIndex");
        Integer pageSize = (Integer) map.get("pageSize");
        RefCount refCount = new RefCount(0);
        List<Account> accounts = iAccountService.getAccounts(refCount, name, dept,
                pageIndex, pageSize);
        //noinspection Duplicates
        if (accounts != null) {
            ServerResponse<List<Account>> response = ServerResponse.createBySuccess(accounts);
            response.setCount(refCount.getCount());
            return response;
        } else {
            return ServerResponse.createByErrorMessage("获取账户失败");
        }
    }

    @RequestMapping(value = "add_account.do", method = RequestMethod.POST)
    public ServerResponse<Account> addAccount(@RequestBody Map<String, Object> accountMap) {
        Account account = new Account();
        String name = (String) accountMap.get("name");
        double balance = Double.parseDouble((String) accountMap.get("balance"));
        String deptName = (String) accountMap.get("dept");
        Dept dept = iDeptService.getDeptByName(deptName);
        String remark = (String) accountMap.get("remark");
        account.setName(name);
        account.setBalance(balance);
        account.setDept(dept);
        account.setRemark(remark);
        try {
            account.setDeleted(false);
            account = iAccountService.addAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createBySuccess(account);
    }

    @RequestMapping(value = "get_deleted_accounts.do", method = RequestMethod.GET)
    public ServerResponse<List<Account>> getDeletedAccounts() {
        List<Account> accounts = iAccountService.getDeletedAccounts();
        if (accounts != null) {
            return ServerResponse.createBySuccess(accounts);
        } else {
            return ServerResponse.createByErrorMessage("获取账户失败");
        }
    }

    @RequestMapping(value = "recover_account.do", method = RequestMethod.GET)
    public ServerResponse<String> recoverAccount(String id) {
        if (iAccountService.recover(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("恢复失败");
        }
    }

    @RequestMapping(value = "get_account.do", method = RequestMethod.GET)
    public ServerResponse<Account> getAccount(String id) {
        Account account = iAccountService.getAccountById(id);
        if (account != null) {
            return ServerResponse.createBySuccess(account);
        } else {
            return ServerResponse.createByErrorMessage("获取账户失败");
        }
    }

    @RequestMapping(value = "update_account.do", method = RequestMethod.POST)
    public ServerResponse<String> updateAccount(@RequestBody Map<String, Object> accountMap) {
        Account account = new Account();
        String id = (String) accountMap.get("id");
        String name = (String) accountMap.get("name");
        double balance = Double.parseDouble((String) accountMap.get("balance"));
        String deptName = (String) accountMap.get("dept");
        Dept dept = iDeptService.getDeptByName(deptName);
        String remark = (String) accountMap.get("remark");
        account.setId(id);
        account.setName(name);
        account.setBalance(balance);
        account.setDept(dept);
        account.setRemark(remark);
        try {
            account.setDeleted(false);
            iAccountService.updateAccount(account);
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "delete_account.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        if (iAccountService.delete(id)) {
            return ServerResponse.createBySuccess();
        } else {
            return ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
