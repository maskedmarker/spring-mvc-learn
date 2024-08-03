package org.example.learn.web.spring.service.impl;

import org.example.learn.web.spring.constant.ErrorCode;
import org.example.learn.web.spring.dto.PayRequestDto;
import org.example.learn.web.spring.dto.PayResultDto;
import org.example.learn.web.spring.service.PayService;
import org.example.learn.web.spring.util.TraceUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class PayServiceImpl implements PayService {

    @Override
    public PayResultDto pay(PayRequestDto requestDto) {
        Date now = Calendar.getInstance().getTime();
        PayResultDto payResultDto = new PayResultDto();
        payResultDto.setErrorCode(ErrorCode.OK);
        payResultDto.setErrorMsg("OK");
        payResultDto.setRequestNo(requestDto.getRequestNo());
        payResultDto.setTraceId(TraceUtils.getTraceId());

        return payResultDto;
    }
}
