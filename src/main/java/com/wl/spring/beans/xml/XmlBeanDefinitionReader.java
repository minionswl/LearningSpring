package com.wl.spring.beans.xml;

import com.wl.spring.beans.AbstractBeanDefinitionReader;
import com.wl.spring.beans.BeanDefinition;
import com.wl.spring.beans.BeanReference;
import com.wl.spring.beans.PropertyValue;
import com.wl.spring.beans.factory.BeanFactory;
import com.wl.spring.beans.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    protected void doLoadBeanDefinitions(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        registerBeanDefinitions(document);
        inputStream.close();
    }

    protected void registerBeanDefinitions(Document document) {
        Element root = document.getDocumentElement();
        parseBeanDefinitions(root);
    }

    protected void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    private void processBeanDefinition(Element ele) {
        BeanDefinition beanDefinition = new BeanDefinition();
        String aClass = ele.getAttribute("class");
        beanDefinition.setBeanClassName(aClass);
        processBeanProperty(ele,beanDefinition);
        getRegistry().put(ele.getAttribute("id"), beanDefinition);
    }

    private void processBeanProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList property = ele.getElementsByTagName("property");
        for (int i = 0; i < property.getLength(); i++) {
            Node node = property.item(i);
            if(node instanceof Element){
                Element propertyEle = (Element) node;
                if(propertyEle.hasAttribute("name") && propertyEle.hasAttribute("value")) {
                    String name = propertyEle.getAttribute("name");
                    String value = propertyEle.getAttribute("value");
                    beanDefinition.getPropertyValues().addPropertyValueList(new PropertyValue(name, value));
                }else if(propertyEle.hasAttribute("name") && propertyEle.hasAttribute("ref")){
                    String name = propertyEle.getAttribute("name");
                    String ref = propertyEle.getAttribute("ref");
                    beanDefinition.getPropertyValues().addPropertyValueList(new PropertyValue(name, new BeanReference(ref)));
                }
            }
        }
    }


}
