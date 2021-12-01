package com.wl.spring.context;

import com.wl.spring.beans.BeanDefinition;
import com.wl.spring.beans.factory.AbstractBeanFactory;
import com.wl.spring.beans.factory.AutowireCapableBeanFactory;
import com.wl.spring.beans.io.ResourceLoader;
import com.wl.spring.beans.xml.XmlBeanDefinitionReader;

import java.util.Map;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private String configLocation;
    private AbstractBeanFactory beanFactory;
    public ClassPathXmlApplicationContext(String configLocation) throws Exception {
        this(configLocation, new AutowireCapableBeanFactory());
    }

    public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
        super(configLocation, beanFactory);
        this.configLocation = configLocation;
        this.beanFactory = beanFactory;
        refresh();
    }

    //xml配置文件解析
    public void loadBeanDefinition(AbstractBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
        for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }
}
