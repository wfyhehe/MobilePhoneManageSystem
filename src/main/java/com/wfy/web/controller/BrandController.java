package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Brand;
import com.wfy.web.service.IBrandService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/brand/")
public class BrandController {

    @Resource
    private IBrandService iBrandService;

    @RequestMapping(value = "get_brands.do", method = RequestMethod.POST)
    public ServerResponse<List<Brand>> getBrands(@RequestBody Map<String, Object> map) {
        List<Brand> brands = iBrandService.getBrands();
        if (brands != null) {
            return ServerResponse.createBySuccess(brands);
        } else {
            return ServerResponse.createByErrorMessage("获取品牌失败");
        }
    }

    @RequestMapping(value = "add_brand.do", method = RequestMethod.POST)
    public ServerResponse<Brand> addBrand(@RequestBody Map<String, Object> brandMap) {
        Brand brand = new Brand();
        String name = (String) brandMap.get("name");
        brand.setName(name);
        try {
            if (!iBrandService.addBrand(brand)) {
                return ServerResponse.createByErrorMessage("已存在该品牌");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(brand);
    }

    @RequestMapping(value = "delete_brand.do", method = RequestMethod.POST)
    public ServerResponse<String> delete(@RequestBody Map<String, Object> nameMap) {
        String name = (String) nameMap.get("name");
        return iBrandService.delete(name);
    }
}