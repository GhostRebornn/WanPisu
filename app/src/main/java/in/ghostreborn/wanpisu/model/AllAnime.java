package in.ghostreborn.wanpisu.model;

/**
 * AllAnime anime model
 */
public class AllAnime {

    String id;
    String name;
    String thumbnail;
    String description;
    public AllAnime(
            String id,
            String name,
            String thumbnail,
            String description
    ){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.description = description;
    }

    public String getId() {
        return id;
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
