package com.wl.spring.beans;

public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;

}