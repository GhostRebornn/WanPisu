package in.ghostreborn.wanpisu.constants;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.model.Server;

public class Constants {

    // List of anime
    public static ArrayList<AllAnime> allAnimes;
    public static ArrayList<AllAnime> userAnimes;
    // List of episodes of anime
    public static ArrayList<String> episodes;
    // List of servers available for anime
    public static ArrayList<Server> servers;
    public static ArrayList<String> jikanEpisodes;

    public static int ANIME_TOTAL_PAGES = 0;
    public static int ANIME_CURRENT_PAGE = 0;

    public static Jikan jikan;
    public static AllAnime allAnime;

    // AllAnime ID
    public static String ANIME_ID = "";
    public static String ANIME_MAL_ID = "";
    // Anime episode number
    public static String ANIME_EPISODE = "";
    // Anime server url
    public static String ANIME_SERVER = "";

    public static final String TABLE_NAME = "USER_ANIME";
    public static final String TABLE_ANIME_ID = "ANIME_ID";
    public static final String TABLE_ANIME_NAME = "ANIME_NAME";
    public static final String TABLE_ANIME_THUMBNAIL = "ANIME_THUMBNAIL";
    public static final String TABLE_ANIME_DESC = "ANIME_DESC";

    public static int ALL_ANIME_EPISODE_ADD = 0;
    public static int ALL_ANIME_TOTAL_EPISODES;
}
