package it.blqlabs.appengine.coffeeappbackend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by davide on 20/11/14.
 */
public class ArduEventLogger extends HttpServlet {

    private static final Logger log = Logger.getLogger(ArduEventLogger.class.getName());

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int event = Integer.parseInt(req.getParameter(Constants.PARAMETER_EVENT));

        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();

        switch (event) {
            case 0: //Login from arduino event
                try{
                    Entity entity = new Entity(Constants.ENTITY_NAME_EVENT);
                    entity.setProperty(Constants.PROPERTY_MACHINE_ID, req.getParameter(Constants.PARAMETER_MACHINE_ID));
                    entity.setProperty(Constants.PROPERTY_USER_ID, req.getParameter(Constants.PARAMETER_USER_ID));
                    entity.setProperty(Constants.PROPERTY_EVENT_TYPE, "log_in");
                    entity.setProperty(Constants.PROPERTY_TIMESTAMP, req.getParameter(Constants.PARAMETER_TIMESTAMP));
                    datastoreService.put(entity);
                    txn.commit();
                } finally {
                    if(txn.isActive()) {
                        txn.rollback();
                    }
                }
                break;
            case 1: //Transaction storing event from arduino
                try{
                    Entity entity = new Entity(Constants.ENTITY_NAME_MACHINE_TRANSACTION);
                    entity.setProperty(Constants.PROPERTY_TRANSACTION_ID, req.getParameter(Constants.PARAMETER_TRANSACTION_ID));
                    entity.setProperty(Constants.PROPERTY_USER_ID, req.getParameter(Constants.PARAMETER_USER_ID));
                    entity.setProperty(Constants.PROPERTY_AMOUNT, req.getParameter(Constants.PARAMETER_AMOUNT));
                    entity.setProperty(Constants.PROPERTY_TIMESTAMP, req.getParameter(Constants.PARAMETER_TIMESTAMP));
                    datastoreService.put(entity);
                    txn.commit();
                } finally {
                    if(txn.isActive()) {
                        txn.rollback();
                    }
                }
                break;
        }

        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.getWriter().println("OK");
        log.info(req.toString());
        log.info(resp.toString());
    }
}
