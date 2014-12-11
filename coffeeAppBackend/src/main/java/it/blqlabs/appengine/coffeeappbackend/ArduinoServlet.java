package it.blqlabs.appengine.coffeeappbackend;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by davide on 03/11/14.
 */
public class ArduinoServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{

        String id = req.getParameter("id");

        resp.setContentType("text/plain");
        resp.getWriter().println(id);
    }

}
