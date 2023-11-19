package in.ghostreborn.wanpisu.model;

public class AnimeMusic {

    String type;
    String title;
    String format;
    String url;

    public AnimeMusic(
            String type,
            String title,
            String format,
            String url
    ){
        this.type = type;
        this.title = title;
        this.format = format;
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
