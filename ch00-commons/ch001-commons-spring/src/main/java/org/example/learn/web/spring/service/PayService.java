package org.example.learn.web.spring.service;


import org.example.learn.web.spring.dto.PayRequestDto;
import org.example.learn.web.spring.dto.PayResultDto;

public interface PayService {

    PayResultDto pay(PayRequestDto requestDto);
}
