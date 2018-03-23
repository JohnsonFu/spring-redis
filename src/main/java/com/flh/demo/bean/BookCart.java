package com.flh.demo.bean;

import java.io.Serializable;

/**
 * Created by fulinhua on 2017/8/13.
 */
public class BookCart implements Serializable {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

}
