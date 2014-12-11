package it.blqlabs.appengine.coffeeappbackend;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by davide on 15/11/14.
 */
public class ArduinoGet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.setContentType("text/plain");
        resp.getWriter().print("ciao");
    }

}
