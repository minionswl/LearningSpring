package com.wl.spring.aop;

public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
