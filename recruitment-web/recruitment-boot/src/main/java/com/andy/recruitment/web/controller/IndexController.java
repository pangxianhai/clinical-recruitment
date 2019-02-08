package com.andy.recruitment.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页
 *
 * @author 庞先海 2018-10-17
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String homePage() {
        return "redirect:/recruitment/list";
    }
}