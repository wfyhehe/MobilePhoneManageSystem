package com.wfy.web.dao;

import com.wfy.web.model.Account;
import com.wfy.web.model.Employee;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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

    public List<Account> search(RefCount refCount, String name, String dept, Integer pageIndex, Integer pageSize) {
        List<Account> accounts;
        DetachedCriteria criteria = DetachedCriteria.forClass(Account.class, "a")
                .setFetchMode("dept", FetchMode.SELECT)
                .add(Restrictions.ne("a.deleted", true))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (StringUtils.isNotBlank(name)) {
            criteria.add(Restrictions.like("a.name", "%" + name + "%"));
        }
        if (StringUtils.isNotBlank(dept)) {
            criteria.createAlias("dept", "d")
                    .add(Restrictions.eq("d.name", dept));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            accounts = (List<Account>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            accounts = (List<Account>) hibernateTemplate.findByCriteria(criteria);
        }
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
