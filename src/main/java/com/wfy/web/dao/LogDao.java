package com.wfy.web.dao;

import com.wfy.web.model.Log;
import com.wfy.web.model.enums.LogStatus;
import com.wfy.web.utils.CloneUtil;
import com.wfy.web.utils.RefCount;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Repository
public class LogDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<Log> normalizeLogs(List<Log> logs) {
        for (Log m : logs) {
            normalizeLog(m);
        }
        return logs;
    }

    private Log normalizeLog(Log log) {
        return log;
    }

    private Log extractAndNormalizeFirstLog(List<Log> list) {
        if (list.size() > 0) {
            Log log = list.get(0);
            return normalizeLog(log);
        } else {
            return null;
        }
    }

    public List<Log> search(RefCount refCount, Date startTime, Date endTime,
                            List<LogStatus> status, String actionUrl, String username, Integer pageIndex,
                            Integer pageSize) {
        List<Log> logs;
        DetachedCriteria criteria = DetachedCriteria.forClass(Log.class, "l")
                .setFetchMode("action", FetchMode.SELECT)
                .setFetchMode("user", FetchMode.SELECT)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .addOrder(Order.desc("l.createDate"));
        if (startTime != null && endTime != null) {
            criteria.add(Restrictions.between("l.createDate", startTime, endTime));
        }
        if (status != null && status.size() > 0) {
            Disjunction disjunction = Restrictions.disjunction();
            for (LogStatus logStatus : status) {
                disjunction.add(Restrictions.eq("l.status", logStatus));
            }
            criteria.add(disjunction);
        }
        if (StringUtils.isNotBlank(actionUrl)) {
            criteria.createAlias("action", "a")
                    .add(Restrictions.like("a.url", "%" + actionUrl + "%"));
        }
        if (StringUtils.isNotBlank(username)) {
            criteria.createAlias("user", "u")
                    .add(Restrictions.like("u.username", "%" + username + "%"));
        }
        DetachedCriteria countCriteria = CloneUtil.clone(criteria);
        countCriteria.setProjection(Projections.rowCount());
        long count = ((List<Long>) hibernateTemplate.findByCriteria(countCriteria)).get(0);
        refCount.setCount(count);
        if (pageIndex != null && pageSize != null) {
            int offset = (pageIndex - 1) * pageSize;
            logs = (List<Log>) hibernateTemplate.findByCriteria(criteria, offset, pageSize);
        } else {
            logs = (List<Log>) hibernateTemplate.findByCriteria(criteria);
        }
//        for (Log log : logs) {
//            Hibernate.initialize(log.getMobiles());
//        }
        return normalizeLogs(logs);
    }

    public String save(Log log) throws Exception {
        return (String) hibernateTemplate.save(log);
    }

    public Log getLogById(String id) {
        String hql = "from Log m where m.id = ?";
        List<Log> list = (List<Log>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstLog(list);
    }

    public void update(Log log) {
        hibernateTemplate.update(log);
    }

    public void delete(Log log) {
        hibernateTemplate.delete(log);
    }

    public void merge(Log log) {
        hibernateTemplate.merge(log);
    }
}
