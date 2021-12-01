package com.wl.spring.aop;

import com.wl.spring.HelloWorldService;
import com.wl.spring.HelloWorldServiceImpl;
import com.wl.spring.TimerInterceptor;
import com.wl.spring.context.ApplicationContext;
import com.wl.spring.context.ClassPathXmlApplicationContext;
import org.junit.Test;

public class JdkDynamicTest {
    @Test
    public void test() throws Throwable {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop.xml");
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");

        // --------- helloWorldService with AOP
        // 1. 设置被代理对象(Joinpoint)
        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldServiceImpl.class, HelloWorldService.class);
        advisedSupport.setTargetSource(targetSource);

        // 2. 设置拦截器(Advice)
        TimerInterceptor timerInterceptor = new TimerInterceptor();
        advisedSupport.setMethodInterceptor(timerInterceptor);

        // 3. 创建代理对象
        Cglib2AopProxy jdkDynamicAopProxy = new Cglib2AopProxy(advisedSupport);
        HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();

        helloWorldServiceProxy.helloWorld();

    }

}
