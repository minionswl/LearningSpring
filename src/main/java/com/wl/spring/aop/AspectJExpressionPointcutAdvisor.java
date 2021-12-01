package com.wl.spring.aop;

import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor {
    private Advice advice;

    private AspectJExpressionPointcut pointcut =  new AspectJExpressionPointcut();


    public AspectJExpressionPointcutAdvisor() {
    }

    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Advice getAdvice() {
        return this.advice;
    }
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
