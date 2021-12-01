package com.wl.spring.beans.io;

import java.net.URL;

public class ResourceLoader {
    public ResourceLoader() {

    }
    public Resource getResource(String location) {
        URL resource = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(resource);
    }
}
