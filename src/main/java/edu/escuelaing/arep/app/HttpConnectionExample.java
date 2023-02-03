package main.java.edu.escuelaing.arep.app;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionExample {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static  String GET_URL = "http://www.omdbapi.com/?t=";
    private static String GET_API_KEY = "&apikey=f208f684";

    /**
     *
     * @param movie titulo de la pelicula que se va a buscar
     * @return la información brindada de la pelicula
     * @throws IOException
     */
    public static String ApiAnswer(String movie) throws IOException {
        String Answer = "";
        movie += GET_API_KEY;
        GET_URL += movie;
        System.out.println(movie);
        System.out.println(GET_URL);
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Answer = response.toString();
            System.out.println(response.toString());
        } else {
            Answer = "";
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        GET_URL = "http://www.omdbapi.com/?t=";
        GET_API_KEY = "&apikey=f208f684";
        return Answer;
    }


}

