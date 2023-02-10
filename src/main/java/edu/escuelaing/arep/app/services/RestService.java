package edu.escuelaing.arep.app.services;

public interface RestService {

    /**
     * Contiene el header
     * @return Header en forma de String
     */
    public String getHeader();

    /**
     * Contiene el body del archivo
     * @return Body en forma de String
     */
    public String getResponse();

}