package edu.escuelaing.arep.app;

import edu.escuelaing.arep.app.spring.components.RequestMapping;
import edu.escuelaing.arep.app.spring.springApplication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.*;


public class HttpServer {
    private static HttpServer instance = new HttpServer();
    private OutputStream outputStream = null;
    private Map<String, Method> services = new HashMap<>();

    private HttpServer() {
    }

    public static HttpServer getInstance() {
        return instance;
    }

    public void run(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        springApplication spring = new springApplication();
        ArrayList<String> classes = spring.getClassComponent(new ArrayList<>(), ".");
        for (String className: classes) {
            Class c = Class.forName(className);
            Method[] methods = c.getMethods();
            for (Method m  : methods){
                if(m.isAnnotationPresent(RequestMapping.class)){
                    try {
                        String URL = m.getAnnotation(RequestMapping.class).value();
                        services.put(URL,m);
                    } catch (Throwable x){
                        x.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Methods: " + services);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
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
            boolean first_line = true;
            outputStream = clientSocket.getOutputStream();
            String request = "/simple";
            String verb = "";
            while ((inputLine = in.readLine()) != null) {
                if (first_line) {
                    request = inputLine.split(" ")[1];
                    first_line = false;
                    verb = inputLine.split(" ")[0];
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (Objects.equals(verb, "GET")) {
                if(services.containsKey(request)) {
                    outputLine = services.get(request).invoke(null).toString();
                }
            }else {
            outputLine = "HTTP/1.1 200 OK\r\n" +
                    "Content-type: text/html\r\n" +
                    "\r\n" +
                    "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<title>404</title>\n"
                    + "</head>"
                    + "<body>"
                    + "Use metodos GET"
                    + "</body>"
                    + "</html>";
        }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
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


    public OutputStream getOutputStream() {
        return outputStream;
    }
}