package org.example.learn.web.spring.multi.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/pay")
public class PayController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        Date now = Calendar.getInstance().getTime();
        return "pay hello at " + now.toGMTString();
    }
}
