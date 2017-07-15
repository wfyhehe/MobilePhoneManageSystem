package com.wfy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/7/12.
 */
@Controller
public class HomeController {

    @RequestMapping("home.do")
    public String test() { //后门，仅供测试
        return "home";
    }
}
