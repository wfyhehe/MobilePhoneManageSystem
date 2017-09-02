package com.wfy.web.service;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Color;

import java.util.List;

/**
 * Created by Administrator on 2017/9/2.
 */
public interface IColorService {
    List<Color> getColors();

    boolean addColor(Color color);

    Color getColor(String name);

    ServerResponse<String> delete(String name);
}
