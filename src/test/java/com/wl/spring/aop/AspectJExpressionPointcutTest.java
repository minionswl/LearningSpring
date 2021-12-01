package com.wl.spring.aop;

import com.wl.spring.HelloWorldDao;
import com.wl.spring.HelloWorldService;
import org.junit.Test;

public class AspectJExpressionPointcutTest {
    @Test
    public void test() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.wl.spring.HelloWorldService.*(..))");
        boolean helloWorld = pointcut.matches(HelloWorldService.class.getDeclaredMethod("helloWorld"), HelloWorldDao.class);
        System.out.println(helloWorld);
    }
}
