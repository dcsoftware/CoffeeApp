package it.blqlabs.appengine.coffeeappbackend;

/**
 * The object model for the data we are sending through endpoints
 */
public class MyBean {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}