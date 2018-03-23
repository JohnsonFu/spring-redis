

/**
 * Created by fulinhua on 2017/8/13.
 */
public class TransactionThread extends Thread {

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    private Transaction transaction;


    public void run(){



    }

}
