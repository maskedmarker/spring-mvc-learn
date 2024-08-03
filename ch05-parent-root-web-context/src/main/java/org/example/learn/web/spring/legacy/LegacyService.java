package org.example.learn.web.spring.legacy;

/**
 * 表示旧系统的代码逻辑(通常不要动它)
 */
public class LegacyService {

    public void init() {
        System.out.println("LegacyService.init");
    }

    public void destroy() {
        System.out.println("LegacyService.destroy");
    }

    public String service(String param) {
        System.out.println("LegacyService.service: " + param);

        return param;
    }
}
