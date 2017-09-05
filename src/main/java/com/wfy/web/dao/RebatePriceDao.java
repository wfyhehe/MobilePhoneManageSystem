package com.wfy.web.dao;

import com.wfy.web.model.MobileModel;
import com.wfy.web.model.RebatePrice;
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

    public List<RebatePrice> search(RefCount refCount, String name, String type,
                                    Integer pageIndex, Integer pageSize) {

        throw new RuntimeException("useless method");
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

    public void deleteByMobileModel(MobileModel mobileModel) {
        System.out.println(mobileModel);
        String hql = "delete from RebatePrice rp where rp.mobileModel = ?";
        hibernateTemplate.bulkUpdate(hql, mobileModel);
    }
}
