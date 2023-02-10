package edu.escuelaing.arep.app.webapp;


import edu.escuelaing.arep.app.HttpServer;
import edu.escuelaing.arep.app.services.*;

import java.io.IOException;

public class FirstApp {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.getInstance();
        server.addServices("/index", new HtmlRestService());
        server.addServices("/app", new JsRestService());
        server.addServices("/style", new CssRestService());
        server.addServices("/imagen", new JpgRestService());
        server.run(args);
    }
}