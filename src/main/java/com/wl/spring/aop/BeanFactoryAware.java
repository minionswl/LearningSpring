package com.wl.spring.aop;

import com.wl.spring.beans.factory.BeanFactory;

public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory) throws Exception;
}