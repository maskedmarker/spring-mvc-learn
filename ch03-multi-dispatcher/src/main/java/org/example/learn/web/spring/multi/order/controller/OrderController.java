package org.example.learn.web.spring.multi.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        Date now = Calendar.getInstance().getTime();
        return "order hello at " + now.toGMTString();
    }
}
