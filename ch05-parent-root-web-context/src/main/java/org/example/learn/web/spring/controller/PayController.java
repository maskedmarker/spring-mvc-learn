package org.example.learn.web.spring.controller;


import org.example.learn.web.spring.dto.PayRequestDto;
import org.example.learn.web.spring.dto.PayResultDto;
import org.example.learn.web.spring.legacy.LegacyService;
import org.example.learn.web.spring.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayController {

    @Autowired
    PayService payService;

    @Autowired
    LegacyService legacyService; // 遗留系统在单独的容器中

    @RequestMapping("/pay")
    @ResponseBody
    public PayResultDto hello(@ModelAttribute PayRequestDto payRequestDto) {
        PayResultDto payResultDto = payService.pay(payRequestDto);

        // 支付完成后,需要通知遗留系统
        legacyService.service(payResultDto.getTraceId());

        return payResultDto;
    }
}
