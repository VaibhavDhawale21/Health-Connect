package com.demo.healthconnect.listData;

public class MyListData{

    private String description;
    private String key;

    public MyListData(String description, String key) {
        this.description = description;
        this.key = key;
    }
    public String getDescription() {  
        return description;  
    }  
    public void setDescription(String description) {  
        this.description = description;  
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}