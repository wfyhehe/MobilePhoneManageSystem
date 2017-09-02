package com.wfy.web.controller;

import com.wfy.web.common.ServerResponse;
import com.wfy.web.model.Color;
import com.wfy.web.service.IColorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/26.
 */
@CrossOrigin //TODO 开发使用，发布时必须取消
@RestController
@RequestMapping("/color/")
public class ColorController {

    @Resource
    private IColorService iColorService;

    @RequestMapping(value = "get_colors.do", method = RequestMethod.POST)
    public ServerResponse<List<Color>> getColors(@RequestBody Map<String, Object> map) {
        List<Color> colors = iColorService.getColors();
        if (colors != null) {
            return ServerResponse.createBySuccess(colors);
        } else {
            return ServerResponse.createByErrorMessage("获取颜色失败");
        }
    }

    @RequestMapping(value = "add_color.do", method = RequestMethod.POST)
    public ServerResponse<Color> addColor(@RequestBody Map<String, Object> colorMap) {
        Color color = new Color();
        String name = (String) colorMap.get("name");
        color.setName(name);
        try {
            if (!iColorService.addColor(color)) {
                return ServerResponse.createByErrorMessage("已存在该颜色");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage(e.getMessage());
        }
        return ServerResponse.createBySuccess(color);
    }

    @RequestMapping(value = "delete_color.do", method = RequestMethod.GET)
    public ServerResponse<String> delete(String id) {
        return iColorService.delete(id);
    }
}