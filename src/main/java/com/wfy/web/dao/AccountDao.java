package com.wfy.web.dao;

import com.wfy.web.model.Account;
import com.wfy.web.utils.PaginationUtil;
import com.wfy.web.utils.RefCount;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

@Repository
public class AccountDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Account> normalizeAccounts(List<Account> accounts) {
        for (Account account : accounts) {
            normalizeAccount(account);
        }
        return accounts;
    }

    private Account normalizeAccount(Account account) {
        return account;
    }

    private Account extractAndNormalizeFirstAccount(List<Account> list) {
        if (list.size() > 0) {
            Account account = list.get(0);
            return normalizeAccount(account);
        } else {
            return null;
        }
    }

    public List<Account> getAll(RefCount refCount, int offset, int length) {
        String hql = "from Account a where a.deleted <> 1 order by a.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql)).get(0));
        List<Account> accounts = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql);
        return normalizeAccounts(accounts);
    }

    public List<Account> search(RefCount refCount, String name, String dept, int offset, int
            length) {
        name = "%" + name + "%";
        dept = "%" + dept + "%";
        String hql = "from Account a where a.name like ? and a.dept.id in (" +
                "select d.id from Dept d where d.name like ? and d.deleted <> 1" +
                ") and a.deleted <> 1 order by a.id";
        List<Long> countList = (List<Long>) hibernateTemplate.find("select count(*) " + hql,
                name, dept);
        refCount.setCount(countList.get(0));
        List<Account> accounts = PaginationUtil.pagination(
                hibernateTemplate, offset, length, hql, name, dept);
        return normalizeAccounts(accounts);
    }

    public List<Account> search(RefCount refCount, String name, int offset, int length) {
        name = "%" + name + "%";
        String hql = "from Account a where a.name like ? and a.deleted <> 1 order by a.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql, name))
                .get(0));
        List<Account> accounts = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql, name);
        return normalizeAccounts(accounts);
    }

    public List<Account> getDeleted() {
        String hql = "from Account a where a.deleted = 1 order by a.id";
        List<Account> accounts = (List<Account>) hibernateTemplate.find(hql);
        return normalizeAccounts(accounts);
    }

    public Account getAccountByName(String name) {
        String hql = "from Account a where a.name = ? and a.deleted <> 1";
        List<Account> list = (List<Account>) hibernateTemplate.find(hql, name);
        return extractAndNormalizeFirstAccount(list);
    }

    public Account getAccountById(String id) {
        String hql = "from Account a where a.id = ? and a.deleted <> 1";
        List<Account> list = (List<Account>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstAccount(list);
    }

    public void update(Account account) {
        hibernateTemplate.update(account);
    }


    public String save(Account account) {
        return (String) hibernateTemplate.save(account);
    }

    public boolean recover(String id) {
        String hql = "from Account a where a.id = ? and a.deleted = 1";
        List<Account> list = (List<Account>) hibernateTemplate.find(hql, id);
        if (list.size() <= 0) {
            return false;
        }
        Account account = list.get(0);
        account.setDeleted(false);
        return true;
    }

    public long count() {
        String hql = "select count(*) from Account a where a.deleted <> 1";
        List<Long> list = (List<Long>) hibernateTemplate.find(hql);
        return list.get(0);
    }

}
