package com.wfy.web.service.impl;

import com.mysql.cj.core.util.StringUtils;
import com.wfy.web.dao.AccountDao;
import com.wfy.web.model.Account;
import com.wfy.web.service.IAccountService;
import com.wfy.web.utils.RefCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */
@Service("iAccountService")
@Transactional
public class AccountServiceImpl implements IAccountService {

    @Resource
    private AccountDao accountDao;

    @SuppressWarnings("Duplicates")
    @Override
    public List<Account> getAccounts(RefCount refCount, String name, String dept, int pageIndex, int
            pageSize) {
        int offset = (pageIndex - 1) * pageSize;
        if (!StringUtils.isEmptyOrWhitespaceOnly(dept)) {
            return accountDao.search(refCount, name.trim(), dept.trim(), offset, pageSize);
        } else {
            if (!StringUtils.isEmptyOrWhitespaceOnly(name)) {
                return accountDao.search(refCount, name.trim(), offset, pageSize);
            } else {
                return accountDao.getAll(refCount, offset, pageSize);
            }
        }
    }

    @Override
    public List<Account> getDeletedAccounts() {
        return accountDao.getDeleted();
    }

    @Override
    public Account addAccount(Account account) {
        String id = accountDao.save(account);
        account.setId(id);
        return account;
    }

    @Override
    public boolean recover(String id) {
        return accountDao.recover(id);
    }

    @Override
    public long countAccount() {
        return accountDao.count();
    }

    @Override
    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    @Override
    public Account getAccountById(String id) {
        return accountDao.getAccountById(id);
    }

    @Override
    public Account getAccountByName(String name) {
        return accountDao.getAccountByName(name);
    }

    @Override
    public boolean delete(String id) {
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            return false;
        }
        account.setDeleted(true);
        accountDao.update(account);
        return true;
    }
}
