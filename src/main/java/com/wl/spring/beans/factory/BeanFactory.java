package com.wl.spring.beans.factory;

/**
 * BeanFactory的接口
 */
public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
