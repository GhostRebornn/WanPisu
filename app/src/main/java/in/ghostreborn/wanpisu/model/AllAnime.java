package in.ghostreborn.wanpisu.model;

/**
 * AllAnime anime model
 */
public class AllAnime {

    String id;
    String name;
    String thumbnail;

    public AllAnime(
            String id,
            String name,
            String thumbnail
    ){
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
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

}
