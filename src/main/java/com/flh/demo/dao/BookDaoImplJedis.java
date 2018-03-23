package com.flh.demo.dao;

import com.flh.demo.bean.Book;
import com.flh.demo.bean.BookCart;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * Created by fulinhua on 2017/8/13.
 */
@Component(value="bookDao")
public class BookDaoImplJedis implements BookDao {
    private static Jedis jedis=new Jedis("localhost", 6379);
    private static final String BookId_Set="id_set";
    public void add(Book member) {
        Transaction tx = jedis.multi();
        Map<String,String> values= new HashMap<String, String>();
                values.put("name",member.getName());
                values.put("author",member.getAuthor());
               values.put("desc",member.getDesc());
        tx.hmset(member.getId()+"",values);
        tx.sadd(BookId_Set,member.getId()+"");
       tx.exec();
    }


    public boolean add(List<Book> list) {
        return false;
    }

    public void delete(String key) {
jedis.srem(BookId_Set,key);
    }

    public Book get(String keyId) {
        Book book=new Book();
        book.setId(Integer.parseInt(keyId));
        book.setDesc(jedis.hget(keyId,"desc"));
        book.setAuthor(jedis.hget(keyId,"author"));
        book.setName(jedis.hget(keyId,"name"));
        return book;
    }

    public boolean update(Book member) {
        if (get(member.getId()+"") == null) {
            throw new NullPointerException("数据行不存在, key = " + member.getId());
        }
        Map<String,String> values= new HashMap<String, String>();
        values.put("name",member.getName());
        values.put("author",member.getAuthor());
        values.put("desc",member.getDesc());
        jedis.hmset(member.getId()+"",values);
        return true;
    }

    public List<Book> getList() {
        List<Book> result=new ArrayList<Book>();
        Transaction tx = jedis.multi();
        Response<Set<String>> res=  tx.smembers(BookId_Set);
      List<Object> objects= tx.exec();
       Set<String>keys= (Set<String>) objects.get(0);
        for(String key:keys){
            Book book=new Book();
            book.setId(Integer.parseInt(key));
            book.setDesc(jedis.hget(key,"desc"));
            book.setAuthor(jedis.hget(key,"author"));
            book.setName(jedis.hget(key,"name"));
            result.add(book);
        }

        return result;
    }



    public Boolean addCart(String sessionID, int id, int i) {
        if(jedis.hexists("cart:"+sessionID,id+"")){
            jedis.hincrBy("cart:"+sessionID,id+"",i);
        }else {
            jedis.hset("cart:" + sessionID, id + "", i + "");
        }
        return true;
    }

    public List<BookCart> getCart(String sessionID) {
        List<BookCart> list=new ArrayList<BookCart>();
        Pipeline pl=jedis.pipelined();
        pl.hkeys("cart:"+sessionID);

        List<Object> objects=pl.syncAndReturnAll();
        Set<String> keys= (Set<String>) objects.get(0);
        for(String key:keys){
            BookCart bookCart=new BookCart();
            bookCart.setId(key);
            bookCart.setCount(Integer.parseInt(jedis.hget("cart:"+sessionID,key)));
            list.add(bookCart);
        }
        return list;
    }

    public void removeBookFromCart(String sessionID,int id) {
        if(jedis.hexists("cart:"+sessionID,id+"")){
            if(Integer.parseInt(jedis.hget("cart:"+sessionID,id+""))>1){
                jedis.hincrBy("cart:"+sessionID,id+"",-1);
            }else{
                jedis.hdel("cart:"+sessionID,id+"");
            }
        }
    }
}
