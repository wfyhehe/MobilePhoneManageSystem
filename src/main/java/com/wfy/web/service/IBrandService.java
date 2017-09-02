package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Brand;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public interface IBrandService {
    List<Brand> getBrands();

    boolean addBrand(Brand brand);

    Brand getBrand(String name);

    ServerResponse<String> delete(String name);
}
