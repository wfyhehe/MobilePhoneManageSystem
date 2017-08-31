package com.wfy.web.utils;

import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
public class PaginationUtil {
    public static <E> List<E> pagination(HibernateTemplate hibernateTemplate, int
            offset, int length, String hql, Object... hqlParams) {
        return (List<E>) hibernateTemplate.executeWithNativeSession(session -> {
            Query query = session.createQuery(hql);
            for (int i = 0; i < hqlParams.length; i++) {
                query.setParameter(i, hqlParams[i]);
            }
            query.setFirstResult(offset);
            query.setMaxResults(length);
            return query.list();
        });
    }
}
