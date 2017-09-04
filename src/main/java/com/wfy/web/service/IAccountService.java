package com.wfy.web.service;

import com.wfy.web.model.Account;
import com.wfy.web.utils.RefCount;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */
public interface IAccountService {
    @SuppressWarnings("Duplicates")
    List<Account> getAccounts(RefCount refCount, String name, String dept, int pageIndex, int
            pageSize);

    List<Account> getDeletedAccounts();

    Account addAccount(Account account);

    boolean recover(String id);

    long countAccount();

    void updateAccount(Account account);

    Account getAccountById(String id);

    Account getAccountByName(String name);

    boolean delete(String id);
}
