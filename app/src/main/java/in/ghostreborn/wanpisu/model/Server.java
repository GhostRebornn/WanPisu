package in.ghostreborn.wanpisu.model;

public class Server {

    String name;
    String url;
    public Server(String name, String url){
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
