package com.wl.spring.beans;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {
    private List<PropertyValue> propertyValueList=new ArrayList<>();

    public PropertyValues() {
    }

    public PropertyValues(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }

    public void addPropertyValueList(PropertyValue propertyValue) {
        //TODO decide if we need to add it to the list. e.p. if it already exists
        this.propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}
