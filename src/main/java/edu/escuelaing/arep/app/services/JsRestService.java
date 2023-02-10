package edu.escuelaing.arep.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class JsRestService implements RestService {

    @Override
    public String getHeader() {
        return "HTTP/1.1 200 \r\n" + "Content-Type: text/javascript \r\n" + "\r\n";
    }

    @Override
    public String getResponse() {
        byte[] archivo;
        try {
            archivo = Files.readAllBytes(Paths.get("src/main/resources/app.js"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(archivo);
    }
}
