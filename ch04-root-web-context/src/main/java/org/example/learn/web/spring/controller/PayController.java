package org.example.learn.web.spring.controller;


import org.example.learn.web.spring.dto.PayRequestDto;
import org.example.learn.web.spring.dto.PayResultDto;
import org.example.learn.web.spring.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/paySystem")
public class PayController {

    @Autowired
    PayService payService;

    @RequestMapping("/pay")
    @ResponseBody
    public PayResultDto pay(@ModelAttribute PayRequestDto payRequestDto) {
        PayResultDto payResultDto = payService.pay(payRequestDto);

        return payResultDto;
    }
}
