package in.ghostreborn.wanpisu.model;

/**
 * AllAnime anime model
 */
public class Details {

    String name;
    String thumbnail;
    String description;
    public Details(
            String name,
            String thumbnail,
            String description
    ){
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }
}
