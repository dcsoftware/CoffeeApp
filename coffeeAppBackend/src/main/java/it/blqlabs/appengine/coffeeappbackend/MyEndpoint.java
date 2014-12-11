/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package it.blqlabs.appengine.coffeeappbackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;

/**
 * An endpoint class we are exposing
 */
@Api(name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "coffeeappbackend.appengine.blqlabs.it",
                ownerName = "coffeeappbackend.appengine.blqlabs.it", packagePath = ""))

public class MyEndpoint {

    @ApiMethod(name = "storeClientTransaction")
    public ResponseBean storeClientTransaction(TransactionBean bean) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try{
            Entity entity = new Entity(Constants.ENTITY_NAME_CLIENT_TRANSACTION);
            entity.setProperty(Constants.PROPERTY_TRANSACTION_ID, bean.getId());
            entity.setProperty(Constants.PROPERTY_USER_ID, bean.getUserId());
            entity.setProperty(Constants.PROPERTY_TIMESTAMP, bean.getTimestamp());
            entity.setProperty(Constants.PROPERTY_AMOUNT, bean.getAmount());
            datastoreService.put(entity);
            txn.commit();
        } finally {
            if(txn.isActive()) {
                txn.rollback();
            }
        }

        ResponseBean response = new ResponseBean();
        response.setTransactionId(bean.getId());
        response.setConfirmed(true);

        return response;

    }

    @ApiMethod(name = "clearClientHistory")
    public ResponseBean clearClientHistory(UserBean bean) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        Query.Filter userFilter = new Query.FilterPredicate(Constants.PROPERTY_USER_ID, Query.FilterOperator.EQUAL, bean.getUserId());
        try {
            Query query = new Query(Constants.ENTITY_NAME_CLIENT_TRANSACTION).setFilter(userFilter);
            List<Entity> results = datastoreService.prepare(query)
                    .asList(FetchOptions.Builder.withDefaults());
            for (Entity result : results) {
                datastoreService.delete(result.getKey());
            }
            txn.commit();
        } finally {
            if (txn.isActive()) { txn.rollback(); }
        }

        ResponseBean response = new ResponseBean();
        response.setTransactionId("null");
        response.setConfirmed(true);

        return response;
    }

    @ApiMethod(name = "getTodayKey")
    public KeyBean getTodayKey(UserBean bean) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

        Query.Filter userFilter = new Query.FilterPredicate(Constants.PROPERTY_DATE, Query.FilterOperator.EQUAL, dateFormat.format(c.getTime()));

        Query query = new Query(Constants.ENTITY_NAME_KEY).setFilter(userFilter);

        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());

        KeyBean responseKey = new KeyBean();
        responseKey.setKey((String) results.get(0).getProperty(Constants.PROPERTY_KEY));
        responseKey.setDate((String) results.get(0).getProperty(Constants.PROPERTY_DATE));

        return responseKey;
    }

}
