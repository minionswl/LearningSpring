package com.wl.spring.beans.factory;

import com.wl.spring.beans.BeanDefinition;
import com.wl.spring.beans.BeanPostProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanFactory抽象类,维护了一个BeanDefinition的Map,用于存储BeanDefinition,同时负责注册BeanPostProcessor（aop）
 */
public abstract class AbstractBeanFactory implements BeanFactory{

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    private List<String> beanDefinitionNames = new ArrayList<>();

    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * getBean方法，根据beanName获取bean，如果没有则创建bean，调用此方法以创建bean实例
     * @param
     */
    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null){
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = beanDefinition.getBean();
        if(bean == null){
            bean = doCreateBean(beanDefinition);
            initializeBean(bean, name);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    /**
     * 代理bean的初始化方法
     */
    protected void initializeBean(Object bean, String name) throws Exception {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessBeforeInitialization(bean, name);
        }

        // TODO:call initialize method
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            bean = beanPostProcessor.postProcessAfterInitialization(bean, name);
        }
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    //method to create bean, and return the bean
    public Object doCreateBean(BeanDefinition beanDefinition) throws Exception {
        try {
            //create bean instance
            Object bean = createBeanInstance(beanDefinition);

            //set property values
            applyPropertyValues(bean, beanDefinition);

            return bean;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Object createBeanInstance(BeanDefinition beanDefinition) throws InstantiationException, IllegalAccessException {
        return beanDefinition.getBeanClass().newInstance();
    }

    protected abstract void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception;


    public List getBeansForType(Class type) throws Exception {
        List beans = new ArrayList<Object>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(getBean(beanDefinitionName));
            }
        }
        return beans;
    }

    public  void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
        beanPostProcessors.add(beanPostProcessor);
    }

    public void preInstantiateSingletons() throws Exception {
        for (String beanDefinitionName : beanDefinitionNames) {
            getBean(beanDefinitionName);
        }
    }
}
