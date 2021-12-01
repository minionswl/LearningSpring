package com.wl.spring.beans.factory;

import com.wl.spring.aop.BeanFactoryAware;
import com.wl.spring.beans.BeanDefinition;
import com.wl.spring.beans.BeanReference;
import com.wl.spring.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * 负责对bean属性的赋值
     */
    @Override
    public void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValueList()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);    //由于JDK的安全检查耗时较多.所以通过setAccessible(true)的方式关闭安全检查就可以达到提升反射速度的目的
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
