package in.ghostreborn.wanpisu.parser;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnimeParser {

    public static String searchAnime(String anime) {

        OkHttpClient client = new OkHttpClient();

        String queryUrl = "https://api.allanime.day/api?variables=" +
                Uri.encode("{\"search\":{\"allowAdult\":false,\"allowUnknown\":false,\"query\":\"" +
                        anime +
                        "\"},\"limit\":40,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") +
                "&query=" +
                Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{_id,malId,name,englishName,availableEpisodes,__typename,malId,thumbnail,lastEpisodeInfo,lastEpisodeDate,season,airedStart,episodeDuration,episodeCount,lastUpdateEnd}}}");

        Request request = new Request.Builder()
                .url(queryUrl)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        String rawJson = "";
        try {
            Response response = client.newCall(request).execute();
            rawJson = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawJson;

    }

}
