import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * Created by fulinhua on 2017/8/13.
 */
public class test {
    @Test
    public void test() {
        Jedis jedis = new Jedis("localhost", 6379);
        Transaction tx = jedis.multi();
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

    @Test
    public void test3Pipelined() {
        int count = 1000;



        long start = System.currentTimeMillis();

        withoutPipeline(count);

        long end = System.currentTimeMillis();

        System.out.println("withoutPipeline: " + (end-start));



        start = System.currentTimeMillis();

        usePipeline(count);

        end = System.currentTimeMillis();

        System.out.println("usePipeline: " + (end-start));
    }

    private static void withoutPipeline(int count){

        Jedis jr = null;

        try {

            jr = new Jedis("localhost", 6379);

            for(int i =0; i<count; i++){

                jr.incr("testKey1");

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        finally{

            if(jr!=null){

                jr.disconnect();

            }

        }

    }

    private static void usePipeline(int count){

        Jedis jr = null;

        try {

            jr = new Jedis("localhost", 6379);

            Pipeline pl = jr.pipelined();

            for(int i =0; i<count; i++){

                pl.incr("testKey2");

            }

            pl.sync();

        } catch (Exception e) {

            e.printStackTrace();

        }

        finally{

            if(jr!=null){

                jr.disconnect();

            }

        }

    }
}
