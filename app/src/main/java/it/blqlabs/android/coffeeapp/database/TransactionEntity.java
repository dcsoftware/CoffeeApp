package it.blqlabs.android.coffeeapp.database;

/**
 * Created by davide on 24/10/14.
 */
public class TransactionEntity {
    public Long _id;
    public String transactionNumber;
    public String userId;
    public String timestamp;
    public String amount;
    public String machineId;
    public String encryptedKey = "0";
    public String transactionId;
    public boolean synced = false;

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return machineId + transactionNumber;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
