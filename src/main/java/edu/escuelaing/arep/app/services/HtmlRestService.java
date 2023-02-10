package edu.escuelaing.arep.app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HtmlRestService implements RestService{
    @Override
    public String getHeader() {
        return "HTTP/1.1 200 \r\n" + "Content-Type: text/html \r\n" + "\r\n";
    }

    @Override
    public String getResponse() {
        byte[] archivo;
        try{
            archivo = Files.readAllBytes(Paths.get("src/main/resources/index.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(archivo);
    }
}
