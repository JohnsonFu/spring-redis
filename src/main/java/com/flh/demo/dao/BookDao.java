package com.flh.demo.dao;

import com.flh.demo.bean.Book;
import com.flh.demo.bean.BookCart;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fulinhua on 2017/8/12.
 */
@Component
public interface BookDao {
    void add(Book member);

    abstract boolean add(List<Book> list);

    void delete(String key);

    Book get(String keyId);
    public boolean update(final Book member);

    List<Book> getList();

    Boolean addCart(String sessionID, int id, int i);

    List<BookCart> getCart(String sessionID);

    void removeBookFromCart(String sessionID,int id);
}