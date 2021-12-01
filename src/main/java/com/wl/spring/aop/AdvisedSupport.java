package com.wl.spring.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * 封装了需要代理的目标类，以及目标类的增强类
 */
public class AdvisedSupport {
    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }
}
