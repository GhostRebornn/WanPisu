package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class AnimeDetails {

    String englishName;
    String thumbnail;
    String sequel;
    String prequel;
    ArrayList<AnimeCharacter> animeCharacters;

    public AnimeDetails(
            String englishName,
            String thumbnail,
            String sequel,
            String prequel,
            ArrayList<AnimeCharacter> animeCharacters
    ) {
        this.englishName = englishName;
        this.thumbnail = thumbnail;
        this.sequel = sequel;
        this.prequel = prequel;
        this.animeCharacters = animeCharacters;
    }

    public ArrayList<AnimeCharacter> getAnimeCharacters() {
        return animeCharacters;
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
