package in.ghostreborn.wanpisu.constants;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.model.Details;

public class Constants {

    // List of anime
    public static ArrayList<AllAnime> allAnimes;
    // List of anime data
    public static Details details;
    // List of episodes of anime
    public static ArrayList<String> episodes;
    // List of servers available for anime
    public static ArrayList<String> servers;

    // AllAnime ID
    public static String ANIME_ID = "";
    // Anime episode number
    public static String ANIME_EPISODE = "";
    // Anime server url
    public static String ANIME_SERVER = "";

    public static final String TABLE_NAME = "USER_ANIME";
    public static final String TABLE_ANIME_NAME = "ANIME_NAME";
    public static final String TABLE_ANIME_THUMBNAIL = "ANIME_THUMBNAIL";
    public static final String TABLE_ANIME_DESC = "ANIME_DESC";

}
