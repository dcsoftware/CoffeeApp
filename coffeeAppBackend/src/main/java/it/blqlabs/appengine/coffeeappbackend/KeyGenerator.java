package it.blqlabs.appengine.coffeeappbackend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by davide on 03/11/14.
 */
public class KeyGenerator extends HttpServlet{

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
    private static final int keyLength = 20;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");


        try{
            Entity entity = new Entity(Constants.ENTITY_NAME_KEY);
            entity.setProperty(Constants.PROPERTY_DATE, dateFormat.format(c.getTime()));
            entity.setProperty(Constants.PROPERTY_KEY, generateNewKey());
            datastoreService.put(entity);
            txn.commit();
        } finally {
            if(txn.isActive()) {
                txn.rollback();
            }
        }
    }

    private String generateNewKey() {
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(keyLength);

        for(int i = 0; i < keyLength; i++ ){
            sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        }

        return sb.toString();
    }
}
