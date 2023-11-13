package in.ghostreborn.wanpisu.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.Jikan;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JikanParser {

    public static void parseAnimeFull(String malID) {
        String url = "https://api.jikan.moe/v4/anime/" +
                malID +
                "/full";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();


        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            JSONObject dataObject = new JSONObject(response.body().string())
                    .getJSONObject("data");

            String title = dataObject.getString("title");

            String thumbnail = dataObject
                    .getJSONObject("images")
                    .getJSONObject("jpg")
                    .getString("image_url");
            ArrayList<String> titles = new ArrayList<>();
            JSONArray titlesArray = dataObject.getJSONArray("titles");
            for (int i = 0; i < titlesArray.length(); i++) {
                String titleNow = titlesArray.getJSONObject(i).getString("title");
                titles.add(titleNow);
            }
            String type = dataObject.getString("type");
            String source = dataObject.getString("source");
            String episodes = dataObject.getString("episodes");
            String status = dataObject.getString("status");
            String aired = dataObject
                    .getJSONObject("aired")
                    .getString("string");
            String duration = dataObject.getString("duration");
            String rating = dataObject.getString("rating");
            String score = dataObject.getString("score");
            String synopsis = dataObject.getString("synopsis");
            String season = dataObject.getString("season");

            String broadcast = dataObject.getJSONObject("broadcast")
                    .getString("string");

            Constants.jikan = new Jikan(
                    title,
                    thumbnail,
                    titles,
                    type,
                    source,
                    episodes,
                    status,
                    aired,
                    duration,
                    rating,
                    score,
                    synopsis,
                    season,
                    broadcast
            );

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    public static void getEpisodes(String malID, String page){

        Constants.jikanEpisodes = new ArrayList<>();
        String url = "https://api.jikan.moe/v4/anime/" +
                malID +
                "/episodes?page=" +
                page;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            JSONObject baseJSON = new JSONObject(response.body().string());
            JSONArray dataArray = baseJSON.getJSONArray("data");
            for (int i=0; i<dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                String title = dataObject.getString("title");
                Constants.jikanEpisodes.add(title);
            }
            Constants.ANIME_TOTAL_PAGES = baseJSON.getJSONObject("pagination")
                    .getInt("last_visible_page");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
