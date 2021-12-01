package com.wl.spring;

public class HelloWorldServiceImpl implements HelloWorldService {
    private HelloWorldDao helloWorldDao;

    private String text;

    public void helloWorld(){
        System.out.println(text);
    }

    public void setHelloWorldDao(HelloWorldDao helloWorldDao) {
        this.helloWorldDao = helloWorldDao;
    }

    public void setText(String text) {
        this.text = text;
    }
}
