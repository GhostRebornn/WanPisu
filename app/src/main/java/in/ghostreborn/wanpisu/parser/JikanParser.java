package in.ghostreborn.wanpisu.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

        try {
            Response response = client.newCall(request).execute();
            JSONObject dataObject = new JSONObject(response.body().string())
                    .getJSONObject("data");

            String thumbnail = dataObject
                    .getJSONObject("images")
                    .getJSONObject("jpg")
                    .getString("image_url");
            ArrayList<String> titles = new ArrayList<>();
            JSONArray titlesArray = dataObject.getJSONArray("titles");
            for (int i = 0; i < titlesArray.length(); i++) {
                JSONObject titlesObject = titlesArray.getJSONObject(i);
                String title = titlesObject.getString("type") +
                        ":" + titlesObject.getString("title");
                titles.add(title);
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

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}
