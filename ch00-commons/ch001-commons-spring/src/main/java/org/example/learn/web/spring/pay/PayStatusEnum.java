package org.example.learn.web.spring.pay;

public enum PayStatusEnum {

    NOT_PAY("0", "未支付"),
    PAYING("", "正在支付"),
    PAYED("", "已支付"),
    ;

    private final String code;
    private final String description;

    PayStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
