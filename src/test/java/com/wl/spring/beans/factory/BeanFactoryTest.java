package com.wl.spring.beans.factory;

import com.wl.spring.HelloWorldDao;
import com.wl.spring.HelloWorldService;
import com.wl.spring.beans.*;
import com.wl.spring.beans.io.ResourceLoader;
import com.wl.spring.beans.xml.XmlBeanDefinitionReader;
import org.junit.Test;

import java.util.Map;

public class BeanFactoryTest {

    @Test
    public void test() throws Exception {
        //initial beanFactory
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();

        //use BeanDefinition to initial HelloWorldService
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClassName("com.wl.spring.HelloWorldService");

        PropertyValue propertyValue = new PropertyValue("text","wl");
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValueList(propertyValue);
        beanDefinition.setPropertyValues(propertyValues);


        beanFactory.registerBeanDefinition("hello",beanDefinition);

        //use beanFactory to get bean
        Object hello1 = beanFactory.getBean("hello");
        HelloWorldService hello = (HelloWorldService) beanFactory.getBean("hello");
        hello.helloWorld();
    }

    @Test
    public void test2() throws Exception {
        // 1.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions("ioc.xml");

        // 2.初始化BeanFactory并注册bean
        AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }

        // 3.获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
//        System.out.println(helloWorldService.getHelloWorldDao());
        HelloWorldDao helloWorldDao = (HelloWorldDao) beanFactory.getBean("helloWorldDao");
        System.out.println(helloWorldDao);
    }

}
