package com.wl.spring.context;

import com.wl.spring.beans.BeanPostProcessor;
import com.wl.spring.beans.factory.AbstractBeanFactory;

import java.util.List;

public abstract class AbstractApplicationContext implements ApplicationContext {
    private AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(String location,AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    //定义了容器创建的各种操作
    public void refresh() throws Exception {
        loadBeanDefinition(beanFactory);    //加载bean定义
        registerBeanPostProcessors(beanFactory);    //注册bean后处理器
        onRefresh();    //刷新容器以创建bean
    }

    protected void onRefresh() throws Exception{
        beanFactory.preInstantiateSingletons();
    }


    public abstract void loadBeanDefinition (AbstractBeanFactory beanFactory) throws Exception;


    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        for (Object beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
        }
    }

}
