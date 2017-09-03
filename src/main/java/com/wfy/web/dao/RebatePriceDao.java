package com.wfy.web.dao;

import com.wfy.web.model.RebatePrice;
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
public class RebatePriceDao {

    @Resource
    private HibernateTemplate hibernateTemplate;

    private List<RebatePrice> normalizeRebatePrices(List<RebatePrice> rebatePrices) {
        for (RebatePrice rebatePrice : rebatePrices) {
            normalizeRebatePrice(rebatePrice);
        }
        return rebatePrices;
    }

    private RebatePrice normalizeRebatePrice(RebatePrice rebatePrice) {
        return rebatePrice;
    }

    private RebatePrice extractAndNormalizeFirstRebatePrice(List<RebatePrice> list) {
        if (list.size() > 0) {
            RebatePrice rebatePrice = list.get(0);
            return normalizeRebatePrice(rebatePrice);
        } else {
            return null;
        }
    }

    public List<RebatePrice> getAll(RefCount refCount, int offset, int length) {
        String hql = "from RebatePrice s where s.deleted <> 1 order by s.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql)).get(0));
        List<RebatePrice> rebatePrices = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql);
        return normalizeRebatePrices(rebatePrices);
    }

    public List<RebatePrice> search(RefCount refCount, String name, String type, int offset, int length) {
        name = "%" + name + "%";
        type = "%" + type + "%";
        String hql = "from RebatePrice s where s.name like ? and s.type.id in (" +
                "select t.id from RebatePriceType t where t.name like ? and t.deleted <> 1" +
                ") and s.deleted <> 1 order by s.id";
        List<Long> countList = (List<Long>) hibernateTemplate.find("select count(*) " + hql,
                name, type);
        refCount.setCount(countList.get(0));
        List<RebatePrice> rebatePrices = PaginationUtil.pagination(
                hibernateTemplate, offset, length, hql, name, type);
        return normalizeRebatePrices(rebatePrices);
    }

    public List<RebatePrice> search(RefCount refCount, String name, int offset, int length) {
        name = "%" + name + "%";
        String hql = "from RebatePrice s where s.name like ? and s.deleted <> 1 order by s.id";
        refCount.setCount(((List<Long>) hibernateTemplate.find("select count(*) " + hql, name))
                .get(0));
        List<RebatePrice> rebatePrices = PaginationUtil.pagination(hibernateTemplate,
                offset, length, hql, name);
        return normalizeRebatePrices(rebatePrices);
    }

    public RebatePrice getRebatePriceById(String id) {
        String hql = "from RebatePrice rp where rp.id = ?";
        List<RebatePrice> list = (List<RebatePrice>) hibernateTemplate.find(hql, id);
        return extractAndNormalizeFirstRebatePrice(list);
    }

    public void update(RebatePrice rebatePrice) {
        hibernateTemplate.update(rebatePrice);
    }


    public String save(RebatePrice rebatePrice) {
        System.out.println(rebatePrice);
        return (String) hibernateTemplate.save(rebatePrice);
    }

    public void delete(RebatePrice rebatePrice) {
        hibernateTemplate.delete(rebatePrice);
    }
}
