package in.ghostreborn.wanpisu.parser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.model.Details;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnimeParser {

    public static void searchAnime(String anime) {

        Constants.allAnimes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String queryUrl = "https://api.allanime.day/api?variables=" + Uri.encode("{\"search\":{\"allowAdult\":true,\"allowUnknown\":true,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" + "_id, " + "name, " + "thumbnail" + "}}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();

        try {
            try (Response response = client.newCall(request).execute()) {
                assert response.body() != null;
                JSONArray edgesArray = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("shows").getJSONArray("edges");
                for (int i = 0; i < edgesArray.length(); i++) {
                    JSONObject edges = edgesArray.getJSONObject(i);
                    String animeID = edges.getString("_id");
                    String animeName = edges.getString("name");
                    String thumbnail = edges.getString("thumbnail");
                    Constants.allAnimes.add(new AllAnime(animeID, animeName, thumbnail));
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void getEpisodes(String allAnimeID) {
        Constants.details = new ArrayList<>();
        Constants.episodes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.allanime.day/api")).newBuilder();
        urlBuilder.addQueryParameter("variables", "{\"showId\":\"" + allAnimeID + "\"}");
        urlBuilder.addQueryParameter("query", "query ($showId: String!) { show( _id: $showId ) { " + "name, " + "thumbnail, " + "description," + "availableEpisodesDetail " + " }}");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").addHeader("Referer", "https://allanime.to").addHeader("Cipher", "AES256-SHA256").build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String rawJSON = response.body().string();
            Log.e("TAG", rawJSON);
            JSONObject rawJSONObject = new JSONObject(rawJSON);
            JSONObject dataObject = rawJSONObject.getJSONObject("data");

            if (dataObject.isNull("show")){
                Log.e("TAG", "show object is null");
            }

            JSONObject showObject = dataObject.getJSONObject("show");
            JSONArray episodes = showObject
                    .getJSONObject("availableEpisodesDetail")
                    .getJSONArray("sub");
            String name = showObject.getString("name");
            String thumbnail = showObject.getString("thumbnail");
            String description = showObject.getString("description");
            Constants.details.add(new Details(name, thumbnail, description));
            for (int i = 0; i < episodes.length(); i++) {
                Log.e("TAG", episodes.getString(i));
                Constants.episodes.add(episodes.getString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getServers(String showID, String episodeNumber) {

        OkHttpClient client = new OkHttpClient();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" +
                Uri.encode("{\"showId\":\"" +
                        showID +
                        "\",\"translationType\":\"sub\",\"episodeString\":\"" +
                        episodeNumber +
                        "\"}") +
                "&query=" +
                Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){episodeString,sourceUrls}}");

        Request request = new Request.Builder()
                .url(queryUrl)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String rawJSON = response.body().string();
            JSONObject jsonObject = new JSONObject(rawJSON);
            JSONArray sourceURLs = jsonObject.getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");

            for (int i = 0; i < sourceURLs.length(); i++) {

                String server = sourceURLs.getJSONObject(i)
                        .getString("sourceUrl");
                Constants.servers.add(server);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public static String decryptAllAnimeServer(String decrypt) {
        StringBuilder result = new StringBuilder();
        char[] inputChars = decrypt.toCharArray();

        for (int i = 0; i < inputChars.length; i += 2) {
            String hex = new String(inputChars, i, 2);
            int dec = Integer.parseInt(hex, 16);
            int xor = dec ^ 56;
            String oct = String.format("%03o", xor);
            result.append((char) Integer.parseInt(oct, 8));
        }

        return result.toString();
    }

    public static Void connectAPITwo(String server, int position) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(server)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        try {
            Response response = client.newCall(request).execute();
            String rawJSON = response.body().string();

            Log.e("TAG", rawJSON);

            Constants.servers.remove(position);

            JSONObject jsonObject = new JSONObject(rawJSON);
            Log.e("TAG", jsonObject.toString());

            if (jsonObject.has("links")){
                JSONArray linksArray = jsonObject
                        .getJSONArray("links");
                for (int i=0; i<linksArray.length(); i++) {
                    Constants.servers.add(linksArray.getJSONObject(i).getString("link"));
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
