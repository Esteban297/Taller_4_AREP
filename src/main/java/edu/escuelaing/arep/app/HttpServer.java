package edu.escuelaing.arep.app;


import edu.escuelaing.arep.app.services.RestService;
import edu.escuelaing.arep.app.sparkService.sparkAns;
import edu.escuelaing.arep.app.sparkService.sparkService;

import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.*;


public class HttpServer {
    private static HttpServer instance = new HttpServer();

    private Map<String, Method> services = new HashMap<>();

    private HttpServer() {
    }

    public static HttpServer getInstance() {
        return instance;
    }

    public void run(String[] args) throws IOException, ClassNotFoundException {
        String className = args[0];
        Class c = Class.forName(className);
        Method[] methods = c.getMethods();
        for(Method m  : methods){
            if(m.isAnnotationPresent(RequestMapping.class)){
                try {
                    String URL = m.getAnnotation(RequestMapping.class).value();
                    services.put(URL,m);
                } catch (Throwable x){
                    x.printStackTrace();
                }
            }
        }
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 34000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine = null;
            String title = "";
            boolean firs_line = true;
            String request = "/simple";
            String verb = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firs_line) {
                    request = inputLine.split(" ")[1];
                    firs_line = false;
                }
                if (inputLine.contains("title?name")) {
                    String[] firstSplit = inputLine.split("=");
                    title = (firstSplit[1].split("HTTP"))[0];
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (Objects.equals(verb, "GET")) {
                if (sparkService.cache.containsKey(request)) {
                    outputLine = sparkService.cache.get(request).getResponse();
                } else if (!sparkService.cache.containsKey(request) && !request.contains("favicon")) {
                    outputLine = sparkService.setCache(request);
                }
            }else if (Objects.equals(verb, "POST")) {
                if(!request.contains("favicon")){
                    String value = request.split("=")[1];
                    String key = request.split("=")[0];
                    key = key.split("\\?")[1];
                    outputLine = sparkService.post(value,key);

                }
            } else if (!Objects.equals(title, "")) {
                outputLine = APIanswer(title);
            } else {
                outputLine = htmlOriginal();
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * @param apiAnswer toda la información de la pelicula la cual va a ser mostrada en una tabla
     * @return
     */
    private static String createTable(String apiAnswer) {
        String[] apiDatini = apiAnswer.split(":");
        String tabla = "<table> \n";
        for (int i = 0; i < (apiDatini.length); i++) {
            String[] temporalAnswer = apiDatini[i].split(",");
            tabla += "<td>" + Arrays.toString(Arrays.copyOf(temporalAnswer, temporalAnswer.length - 1)).replace("[", "").replace("]", "").replace("}", "") + "</td>\n</tr>\n";
            tabla += "<tr>\n<td>" + temporalAnswer[temporalAnswer.length - 1].replace("{", "").replace("[", "") + "</td>\n";
        }
        tabla += "</table>";
        return tabla;

    }

    private static String htmlOriginal() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Form Example</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <h1>GET</h1>\n" +
                "        <form action=\"/hello\">\n" +
                "            <label for=\"name\">Title:</label><br>\n" +
                "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n" +
                "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "        </form> \n" + "<br>" +
                "        <div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "        <script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/title?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "\n" +
                "</html>";
    }

    private static String APIanswer(String title) throws IOException {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border:1px solid black;\n" +
                "}\n" +
                "</style>" +
                createTable(Cache.findTitle(title));
    }


}