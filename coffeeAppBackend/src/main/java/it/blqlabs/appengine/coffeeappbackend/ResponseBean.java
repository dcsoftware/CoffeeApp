package it.blqlabs.appengine.coffeeappbackend;

/**
 * Created by davide on 28/10/14.
 */
public class ResponseBean {

    private String transactionId;
    private boolean confirmed;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String toString() {
        return "transactionId=" + transactionId + ",confirmed=" + confirmed;
    }

}
