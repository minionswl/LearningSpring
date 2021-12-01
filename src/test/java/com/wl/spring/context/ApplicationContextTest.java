package com.wl.spring.context;

import com.wl.spring.HelloWorldService;
import com.wl.spring.HelloWorldServiceImpl;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void test() throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ioc.xml");
        HelloWorldServiceImpl helloWorldService = (HelloWorldServiceImpl) applicationContext.getBean("helloWorldServiceImpl");
        helloWorldService.helloWorld();
    }
}
