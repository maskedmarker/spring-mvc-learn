package org.example.learn.web.spring.service;

import org.example.learn.web.spring.model.dto.PayRequestDto;
import org.example.learn.web.spring.model.dto.PayResultDto;

public interface PayService {

    PayResultDto pay(PayRequestDto requestDto);
}
