package com.wfy.web.service.impl;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.BrandDao;
import com.wfy.web.model.Brand;
import com.wfy.web.service.IBrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Service("iBrandService")
@Transactional
public class BrandServiceImpl implements IBrandService {

    @Resource
    private BrandDao brandDao;

    @Override
    public List<Brand> getBrands() {
        return brandDao.getAll();
    }

    @Override
    public boolean addBrand(Brand brand) {
        if (brandDao.exists(brand.getName())) {
            return false;
        }
        brandDao.save(brand);
        return true;
    }

    @Override
    public Brand getBrand(String name) {
        return brandDao.getBrand(name);
    }
    
    @Override
    public ServerResponse<String> delete(String name) {
        Brand brand = new Brand(name);
        if (!brandDao.exists(name)) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if(brandDao.hasMobiles(brand)) {
            return ServerResponse.createByErrorMessage("若要删除该颜色，请先修改所有使用该颜色的手机的颜色");
        }
        brandDao.delete(brand);
        return ServerResponse.createBySuccess();
    }
}
