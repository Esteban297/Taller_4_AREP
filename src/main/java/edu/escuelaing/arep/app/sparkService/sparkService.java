package edu.escuelaing.arep.app.sparkService;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class sparkService {

    public static Map<String, sparkAns> cache = new HashMap<>();

    public static String post(String value, String key){
        sparkAns sparkAnswer = new sparkAns();
        String body = "{"+key+":"+value+"}";
        sparkAnswer.setBody(body);
        sparkAnswer.setType("application/json");
        cache.put(key,sparkAnswer);
        return sparkAnswer.getResponse();
    }

    public static void get(String path, sparkTrace trace){
        sparkReq sparkRequest = new sparkReq();
        sparkAns sparkAnswer = new sparkAns();
        String body = trace.get(sparkRequest,sparkAnswer);
        sparkAnswer.setBody(body);
        sparkAnswer.setPath(path);
        cache.put(path,sparkAnswer);
    }

    public static String setCache (String path){
        byte[] info = new byte[0];
        String path_1 = "src/main/resources"+path;
        sparkAns sparkAnswer = new sparkAns();
        try {
            Path file = Paths.get(path_1);
            info = Files.readAllBytes(file);
            String type = (path_1).split("\\.")[1];
            sparkAnswer.setType("text/"+type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sparkAnswer.setBody(new String(info));
        cache.put(path, sparkAnswer);
        return sparkAnswer.getResponse();
    }
}
