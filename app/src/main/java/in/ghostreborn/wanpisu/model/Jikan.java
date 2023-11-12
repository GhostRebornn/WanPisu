package in.ghostreborn.wanpisu.model;

import java.util.ArrayList;

public class Jikan {

    String title;
    String thumbnail;
    ArrayList<String> titles;
    String type;
    String source;
    String episodes;
    String status;
    String aired;
    String duration;
    String rating;
    String score;
    String synopsis;
    String season;
    String broadcast;

    public Jikan(
            String title,
            String thumbnail,
            ArrayList<String> titles,
            String type,
            String source,
            String episodes,
            String status,
            String aired,
            String duration,
            String rating,
            String score,
            String synopsis,
            String season,
            String broadcast
    ) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.titles = titles;
        this.type = type;
        this.source = source;
        this.episodes = episodes;
        this.status = status;
        this.aired = aired;
        this.duration = duration;
        this.rating = rating;
        this.score = score;
        this.synopsis = synopsis;
        this.season = season;
        this.broadcast = broadcast;
    }

    public String getSeason() {
        return season;
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public String getAired() {
        return aired;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public String getDuration() {
        return duration;
    }

    public String getEpisodes() {
        return episodes;
    }

    public String getRating() {
        return rating;
    }

    public String getScore() {
        return score;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }
}
