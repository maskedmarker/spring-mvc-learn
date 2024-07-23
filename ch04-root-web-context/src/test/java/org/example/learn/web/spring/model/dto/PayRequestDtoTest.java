package org.example.learn.web.spring.model.dto;


import org.junit.Test;

public class PayRequestDtoTest {

    @Test
    public void testTestToString() {
        PayRequestDto payRequestDto = new PayRequestDto();
        payRequestDto.setRequestNo("1234");
        payRequestDto.setTraceId("789");
        payRequestDto.setBankAcc("622222");
        payRequestDto.setAmount("1.23");

        System.out.println("payRequestDto = " + payRequestDto);
    }
}