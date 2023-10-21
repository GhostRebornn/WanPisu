package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AllAnimeParser {

    public static String searchAnime(String anime) {

        OkHttpClient client = new OkHttpClient();

        String queryUrl = "https://api.allanime.day/api?variables=" +
                Uri.encode("{\"search\":{\"allowAdult\":true,\"allowUnknown\":true,\"query\":\"" +
                        anime +
                        "\"},\"limit\":40,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") +
                "&query=" +
                Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                        "_id, " +
                        "name, " +
                        "thumbnail" +
                        "}}}");

        Request request = new Request.Builder()
                .url(queryUrl)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        Log.e("TAG", queryUrl);

        String rawJson = "";
        try {
            Response response = client.newCall(request).execute();
            rawJson = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawJson;
    }

    // Available options
    // _id type slugTime description averageScore rating
    // status popularity name tags relatedShows relatedMangas
    // genres prevideos characters episodeCount name altNames
    // availableEpisodes availableEpisodesDetail nextAiringEpisode
    public static String getEpisodes(String allAnimeID){
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.allanime.day/api").newBuilder();
        urlBuilder.addQueryParameter("variables", "{\"malId\":\""+ allAnimeID +"\"}");
        urlBuilder.addQueryParameter("query", "query ($showId: String!) { show( _id: $showId ) { _id type }}");
        String url = urlBuilder.build().toString();

        Log.e("TAG", url);

        RequestBody body = RequestBody.create(null, new byte[0]);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .addHeader("Referer", "https://allanime.to")
                .addHeader("Cipher", "AES256-SHA256")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "{}";

    }

    public static String getServer(String showID, String episodeNumber){
        OkHttpClient client = new OkHttpClient();


        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" +
                Uri.encode("{\"showId\":\"" +
                        showID +
                        "\",\"translationType\":\"sub\",\"episodeString\":\"" +
                        episodeNumber +
                        "\"}") +
                "&query=" +
                Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){_id}}");

        Request request = new Request.Builder()
                .url(queryUrl)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        String rawJSON = "{}";
        try {
            Response response = client.newCall(request).execute();
            rawJSON = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawJSON;
    }

}
