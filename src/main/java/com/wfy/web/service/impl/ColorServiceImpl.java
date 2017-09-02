package com.wfy.web.service.impl;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.dao.ColorDao;
import com.wfy.web.model.Color;
import com.wfy.web.service.IColorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/16.
 */
@Service("iColorService")
@Transactional
public class ColorServiceImpl implements IColorService {

    @Resource
    private ColorDao colorDao;

    @Override
    public List<Color> getColors() {
        return colorDao.getAll();
    }

    @Override
    public boolean addColor(Color color) {
        if (colorDao.exists(color.getName())) {
            return false;
        }
        colorDao.save(color);
        return true;
    }

    @Override
    public Color getColor(String name) {
        return colorDao.getColor(name);
    }

    @Override
    public ServerResponse<String> delete(String name) {
        Color color = new Color(name);
        if (!colorDao.exists(name)) {
            return ServerResponse.createByErrorMessage("删除失败");
        }
        if(colorDao.hasMobiles(color)) {
            return ServerResponse.createByErrorMessage("若要删除该颜色，请先修改所有使用该颜色的手机的颜色");
        }
        colorDao.delete(color);
        return ServerResponse.createBySuccess();
    }
}
