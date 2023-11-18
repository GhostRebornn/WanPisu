package in.ghostreborn.wanpisu.constants;

import android.content.SharedPreferences;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.model.AnimeDetails;
import in.ghostreborn.wanpisu.model.Server;

public class Constants {

    // List of anime
    public static ArrayList<AllAnime> allAnimes;
    public static ArrayList<AllAnime> userAnimes;
    // List of episodes of anime
    public static ArrayList<String> episodes;
    // List of servers available for anime
    public static ArrayList<Server> servers;

    public static int ANIME_TOTAL_PAGES = 0;
    public static int ANIME_CURRENT_PAGE = 0;

    public static AllAnime allAnime;
    public static AnimeDetails animeDetails;

    // AllAnime ID
    public static String ANIME_ID = "";
    // Anime episode number
    public static String ANIME_EPISODE = "";
    // Anime server url
    public static String ANIME_SERVER = "";

    public static final String TABLE_NAME = "USER_ANIME";
    public static final String TABLE_ANIME_ID = "ANIME_ID";
    public static final String TABLE_ANIME_NAME = "ANIME_NAME";
    public static final String TABLE_ANIME_THUMBNAIL = "ANIME_THUMBNAIL";

    public static int ALL_ANIME_TOTAL_EPISODES;
    public static String isAdult = Constants.FALSE;
    public static String isUnknown = Constants.FALSE;
    public static String subOrDub = Constants.SUB;

    public static SharedPreferences WanpisuPreference;
    public static final String WANPISU_PREFERENCE = "WANPISU_PREFERENCE";
    public static final String PREFERENCE_ALLOW_ADULT = "ALLOW_ADULT";
    public static final String PREFERENCE_ALLOW_UNKNOWN = "ALLOW_UNKNOWN";
    public static final String PREFERENCE_SUB_DUB = "SUB_DUB";
    public static final String SUB = "sub";
    public static final String DUB = "dub";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

}
