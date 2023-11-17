package in.ghostreborn.wanpisu.model;

public class AnimeDetails {

    String englishName;
    String thumbnail;
    String sequel;
    String prequel;

    public AnimeDetails(
            String englishName,
            String thumbnail,
            String sequel,
            String prequel
    ) {
        this.englishName = englishName;
        this.thumbnail = thumbnail;
        this.sequel = sequel;
        this.prequel = prequel;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getPrequel() {
        return prequel;
    }

    public String getSequel() {
        return sequel;
    }
}
