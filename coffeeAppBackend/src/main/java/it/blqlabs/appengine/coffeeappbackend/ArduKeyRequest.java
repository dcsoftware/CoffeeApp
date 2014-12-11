package it.blqlabs.appengine.coffeeappbackend;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by davide on 10/12/14.
 */
public class ArduKeyRequest extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");

        Query.Filter userFilter = new Query.FilterPredicate(Constants.PROPERTY_DATE, Query.FilterOperator.EQUAL, dateFormat.format(c.getTime()));

        Query query = new Query(Constants.ENTITY_NAME_KEY).setFilter(userFilter);

        List<Entity> results = datastoreService.prepare(query).asList(FetchOptions.Builder.withDefaults());

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("date=" + results.get(0).getProperty(Constants.PROPERTY_DATE) + "&key=" + results.get(0).getProperty(Constants.PROPERTY_KEY));

    }
}
