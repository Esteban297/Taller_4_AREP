package main.java.edu.escuelaing.arep.app;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class Cache {
    public static ConcurrentHashMap<String,String> CacheMovie = new ConcurrentHashMap<>();

    /**
     *
     * @param title nombre de la pelicula la cual se esta consultado
     * @return la información de la pelicula en dado caso que ya se encuentre registrada
     * @throws IOException
     */
    public static String findTitle(String title) throws IOException {
        String cache="";
        if (CacheMovie.containsKey(title)){
            cache += CacheMovie.get(title);
        }else{
            cache = HttpConnectionExample.ApiAnswer(title);
            CacheMovie.put(title,cache);
        }
        return cache;
    }

    /**
     *
     * @param title nombre de la pelicula la cual se esta consultado
     * @return la información de la pelicula en dado caso que ya se encuentre registrada
     * @throws IOException
     */
    public String addCache(String title) throws IOException {
        String cache = HttpConnectionExample.ApiAnswer(title);
        CacheMovie.put(title,title);
        return cache;
    }

}