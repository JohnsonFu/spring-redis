package com.flh.demo.bean;

import java.io.Serializable;

/**
 * Created by fulinhua on 2016/12/10.
 */
public class Book implements Serializable {
    public Book(){};
    public Book(String name, String author) {
        super();
        this.name = name;
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;
    private int id;
    private String name;
    private String author;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
