package edu.escuelaing.arep.app.webapp;


import edu.escuelaing.arep.app.HttpServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FirstApp {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InvocationTargetException, IllegalAccessException {
        HttpServer server_0 = HttpServer.getInstance();
        server_0.run(args);
    }
}