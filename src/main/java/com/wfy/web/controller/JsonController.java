package com.wfy.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wfy.web.model.Menu;
import com.wfy.web.model.User;
import com.wfy.web.model.UserStatus;
import com.wfy.web.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.*;
import java.util.*;

/**
 * Created by Administrator on 2017/7/17.
 */
@Controller
public class JsonController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "menu.json", method = RequestMethod.GET)
    public @ResponseBody List<Menu> menuJson() {
        List<Menu> list = menuService.getTopMenus(null);
        return list;
    }

    @RequestMapping("ajax.do")
    public String test() {
        return "testAjax";
    }

}
