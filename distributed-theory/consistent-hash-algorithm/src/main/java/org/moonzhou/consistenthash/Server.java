package org.moonzhou.consistenthash;

public class Server {

    public Server() {
    }

    public Server(String url) {
        this.url = url;
    }

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
