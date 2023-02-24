package edu.escuelaing.arep.app.spring;



import edu.escuelaing.arep.app.HttpServer;
import edu.escuelaing.arep.app.spring.components.RequestMapping;
import edu.escuelaing.arep.app.spring.components.component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@component
public class ResourcesController {

    @RequestMapping(value = "/app")
    public static String indexjs() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/javascript\r\n" +
                "\r\n" + body("app.js");
    }

    @RequestMapping(value = "/index")
    public static String index() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/html\r\n" +
                "\r\n" + body("index.html");
    }

    private static String body(String extention){
        byte[] content = new byte[0];
        try {
            Path file = Paths.get("src/main/resources/"+extention);
            content = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(content);
    }

    @RequestMapping(value = "/style")
    public static String stylecss() {
        System.out.println("ENTRE A CSS");
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: text/css\r\n" +
                "\r\n" + body("style.css");
    }

    @RequestMapping("/imagen")
    public static String readFileImage() {
        return  getResponseImg();
    }

    public static String getResponseImg() {
        String file = "";
        try {
            file = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: image/jpg\r\n"
                    + "\r\n";
            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/imagen.jpg"));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            HttpServer server = HttpServer.getInstance();
            DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            dataOutputStream.writeBytes(file);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}