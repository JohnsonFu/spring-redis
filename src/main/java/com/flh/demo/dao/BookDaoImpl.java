package com.flh.demo.dao;

import com.flh.demo.bean.Book;
import com.flh.demo.bean.BookCart;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by fulinhua on 2017/8/12.
 */
public class BookDaoImpl extends RedisGeneratorDao<String,Book> implements BookDao {

    /**
     * 添加对象
     */

    private static final String BookId_Set="id_set";

    private static final String BookMap="books";

    public void add(final Book member) {
      redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(member.getId()+"");
                Map<byte[],byte[]> values= new HashMap<byte[], byte[]>();
                values.put(serializer.serialize("name"),serializer.serialize(member.getName()));
                values.put(serializer.serialize("author"),serializer.serialize(member.getAuthor()));
                values.put(serializer.serialize("desc"),serializer.serialize(member.getDesc()));
                 connection.hMSet(key,values);
               return addKeysToSet(member.getId()+"");
            }
        });

    }

    /**
     * 添加集合
     */

    public boolean add(final List<Book> list) {
        Assert.notEmpty(list);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (Book member : list) {
                    byte[] key  = serializer.serialize(member.getId()+"");
                    byte[] name = serializer.serialize(member.getName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);
        return result;
    }

    /**
     * 删除对象 ,依赖key
     */

    public void delete(final String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
               boolean k= connection.sRem(serializer.serialize(BookId_Set),serializer.serialize(key));
                return k;
            }
        });
    }

    /**
     * 删除集合 ,依赖key集合
     */
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 修改对象
     */
    public boolean update(final Book member) {
        String key = member.getId()+"";
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(member.getId()+"");
                Map<byte[],byte[]> values= new HashMap<byte[], byte[]>();
                values.put(serializer.serialize("name"),serializer.serialize(member.getName()));
                values.put(serializer.serialize("author"),serializer.serialize(member.getAuthor()));
                values.put(serializer.serialize("desc"),serializer.serialize(member.getDesc()));
                connection.hMSet(key,values);
                return true;
            }
        });
        return result;
    }

    public boolean addKeysToSet(final String id){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] setName  = serializer.serialize(BookId_Set);
                byte[] ID  = serializer.serialize(id);
                return connection.sAdd(setName, ID);
            }
        });
        return result;
    }




    public List<Book> getList() {
        List<Book> list = redisTemplate.execute(new RedisCallback<List<Book>>() {
            public List<Book>  doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] setName = serializer.serialize(BookId_Set);
                Set<byte[]> set = connection.sMembers(setName);
                List<Book> list=new ArrayList<Book>();
                for(byte[] key:set){
Book book=new Book();
                    book.setId(Integer.parseInt(serializer.deserialize(key)));
                    book.setName(serializer.deserialize(connection.hGet(key,serializer.serialize("name"))));
                    book.setDesc(serializer.deserialize(connection.hGet(key,serializer.serialize("desc"))));
                    book.setAuthor(serializer.deserialize(connection.hGet(key,serializer.serialize("author"))));
list.add(book);
                }
                return list;
            }
        });
        return list;
    }

    public Boolean  addCart(final String sessionID,final int id,final int i) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
               if(connection.hExists(serializer.serialize("cart:"+sessionID),serializer.serialize(id+""))) {
                   int count = Integer.parseInt(serializer.deserialize(connection.hGet(serializer.serialize("cart:" + sessionID), serializer.serialize(id + ""))));
                   count=count+i;
                   if(count>0) {
                       connection.hSet(serializer.serialize("cart:" + sessionID), serializer.serialize(id + ""), serializer.serialize(count + ""));
                   }
                   else{
                       connection.hDel(serializer.serialize("cart:" + sessionID), serializer.serialize(id + ""));

                   }

               }else{
                   connection.hSet(serializer.serialize("cart:" + sessionID), serializer.serialize(id + ""), serializer.serialize(i+""));

               }
               System.out.println("cart:"+sessionID);


                return false;
            }
        });
        return result;
    }

    public List<BookCart> getCart(final String sessionID) {
        List<BookCart> result = redisTemplate.execute(new RedisCallback<List<BookCart>>() {
            public List<BookCart> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                List<BookCart> result=new ArrayList<BookCart>();
           Set<byte[]> keys= connection.hKeys(serializer.serialize("cart:"+sessionID));
                for(byte[] key:keys){
                    BookCart bookCart=new BookCart();
                    bookCart.setId(serializer.deserialize(key));
                    bookCart.setCount(Integer.parseInt(serializer.deserialize(connection.hGet(serializer.serialize("cart:"+sessionID),key))));
                    result.add(bookCart);

                }

                return result;
            }
        });

        return result;
    }

    public void removeBookFromCart(String sessionID,int id) {

    }

    /**
     * 根据key获取对象
     */

    public Book get(final String keyId) {
        Book result = redisTemplate.execute(new RedisCallback<Book>() {
            public Book doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] name=connection.hGet(key, serializer.serialize("name"));
                byte[] desc=connection.hGet(key, serializer.serialize("desc"));
                byte[] author=connection.hGet(key, serializer.serialize("author"));
                Book book=new Book();
                book.setId(Integer.parseInt(serializer.deserialize(key)));
                book.setName(serializer.deserialize(name));
                book.setAuthor(serializer.deserialize(author));
                book.setDesc(serializer.deserialize(desc));
                return book;
            }
        });
        return result;
    }




}
