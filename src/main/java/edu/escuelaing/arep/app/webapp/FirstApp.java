package edu.escuelaing.arep.app.webapp;


import edu.escuelaing.arep.app.HttpServer;
import edu.escuelaing.arep.app.sparkService.*;

import java.io.IOException;

public class FirstApp {

    public static void main(String[] args) throws IOException {
        HttpServer server_0 = HttpServer.getInstance();
        sparkService.get("", (req, ans) -> {
            ans.setType("application/json");
            return ans.getResponse();
        });
        server_0.run(args);
    }
}