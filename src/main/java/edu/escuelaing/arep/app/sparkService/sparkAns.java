package edu.escuelaing.arep.app.sparkService;

public class sparkAns {
    private String type;
    private String path;
    private String body;

    public String getHeader() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-type: " + type + "\r\n" +
                "\r\n";
    }

    public String getResponse() {
        return getHeader() + body;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
