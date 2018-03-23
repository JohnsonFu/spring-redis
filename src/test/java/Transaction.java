import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by fulinhua on 2017/8/13.
 */
public class Transaction {

    public void test() {
        Jedis jedis = new Jedis("localhost", 6379);
        redis.clients.jedis.Transaction tx = jedis.multi();
        tx.incr( "foo");
        tx.set( "t1", "2");
        List<Object> result = tx.exec();

        if (result == null || result.isEmpty()) {
            System. err.println( "Transaction error...");
            return ;
        }

        for (Object rt : result) {
            System. out.println(rt.toString());
        }
    }

}
