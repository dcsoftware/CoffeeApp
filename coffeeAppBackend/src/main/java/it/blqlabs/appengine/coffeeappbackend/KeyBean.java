package it.blqlabs.appengine.coffeeappbackend;

/**
 * Created by davide on 28/10/14.
 */
public class KeyBean {

    private int id;
    private String key;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "key=" + key + ",date=" + date;
    }


}
