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
import in.ghostreborn.wanpisu.helper.WanPisuUtils;
import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.model.Server;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AllAnimeParser {

    /**
     * AllAnime api to search for anime
     *
     * @param anime - Anime name
     */
    public static void searchAnime(String anime) {

        Constants.allAnimes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String queryUrl = "https://api.allanime.day/api?variables=" + Uri.encode("{\"search\":{\"allowAdult\":true,\"allowUnknown\":true,\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"sub\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id, " +
                "malId, " +
                "name, " +
                "thumbnail" +
                "}}}");
        Request request = new Request.Builder().url(queryUrl).header("Referer", "https://allanime.to").header("Cipher", "AES256-SHA256").header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").build();

        try {
            try (Response response = client.newCall(request).execute()) {
                assert response.body() != null;
                JSONArray edgesArray = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("shows").getJSONArray("edges");
                for (int i = 0; i < edgesArray.length(); i++) {
                    JSONObject edges = edgesArray.getJSONObject(i);
                    String animeID = edges.getString("_id");
                    String malId = edges.getString("malId");
                    String animeName = edges.getString("name");
                    String thumbnail = edges.getString("thumbnail");
                    Constants.allAnimes.add(new AllAnime(animeID, malId, animeName, thumbnail, ""));
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * AllAnime api to parse available episodes of anime
     *
     * @param allAnimeID - AllAnime anime ID
     */
    public static void getEpisodes(String allAnimeID, boolean getDetails) {
        Constants.episodes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        String queryParameter;
        if (!getDetails){
            queryParameter = "query ($showId: String!) { show( _id: $showId ) { " +
                    "availableEpisodesDetail " +
                    " }}";
        }else {
            queryParameter = "query ($showId: String!) { show( _id: $showId ) { " +
                    "name, " +
                    "thumbnail, " +
                    "description," +
                    "availableEpisodesDetail " +
                    " }}";
        }

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.allanime.day/api")).newBuilder();
        urlBuilder.addQueryParameter("variables", "{\"showId\":\"" + allAnimeID + "\"}");
        urlBuilder.addQueryParameter("query", queryParameter);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").addHeader("Referer", "https://allanime.to").addHeader("Cipher", "AES256-SHA256").build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String rawJSON = response.body().string();
            JSONObject rawJSONObject = new JSONObject(rawJSON);
            JSONObject dataObject = rawJSONObject.getJSONObject("data");

            JSONObject showObject = dataObject.getJSONObject("show");
            JSONArray episodes = showObject
                    .getJSONObject("availableEpisodesDetail")
                    .getJSONArray("sub");

            Log.e("TAG", rawJSON);

            if (getDetails){
                String name = showObject.getString("name");
                String thumbnail = showObject.getString("thumbnail");
                String description = showObject.getString("description");
                Constants.allAnime = new AllAnime(
                        allAnimeID,
                        "",
                        name,
                        thumbnail,
                        description
                );
            }else {
                JikanParser.parseAnimeFull(Constants.ANIME_MAL_ID);
                JikanParser.getEpisodes("21");
            }

            Constants.episodeGroup = new ArrayList<>();
            for (int i = episodes.length() - 1; i >= 0; i--) {
                Constants.episodes.add(episodes.getString(i));
            }
            Constants.ALL_ANIME_TOTAL_EPISODES = Constants.episodes.size() - 1;

            WanPisuUtils.setupEpisodeGroups();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * AllAnime api to get available servers for anime
     *
     * @param showID        - AllAnime anime ID
     * @param episodeNumber - Anime episode number
     */
    public static void getServers(String showID, String episodeNumber) {

        OkHttpClient client = new OkHttpClient();
        Constants.servers = new ArrayList<>();

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

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String rawJSON = response.body().string();
            JSONObject jsonObject = new JSONObject(rawJSON);
            JSONArray sourceURLs = jsonObject.getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONArray("sourceUrls");

            for (int i = 0; i < sourceURLs.length(); i++) {

                String server = sourceURLs.getJSONObject(i)
                        .getString("sourceUrl");
                if (server.contains("--")) {
                    server = AllAnimeParser.decryptAllAnimeServer(server.substring(2));
                    if (server.contains("clock")) {
                        server = "https://embed.ssbcontent.site/apivtwo/clock.json?id=" + server.substring(18);
                        connectAPITwo(server);
                        continue;
                    }
                }

                String name = server;
                if (server.contains("fast4speed")) {
                    name = "Fast4Speed";
                } else if (server.contains("ok.ru")) {
                    name = "Ok Ru";
                } else if (server.contains("streamsb")) {
                    name = "StreamSB";
                } else if (server.contains("filemoon")) {
                    name = "FileMoon";
                } else if (server.contains("goone")) {
                    name = "GoOne";
                } else if (server.contains("mp4upload")) {
                    name = "MP4Upload";
                } else if (server.contains("streamlare")) {
                    name = "Streamlare";
                } else if (server.contains("streamwish")) {
                    name = "StreamWish";
                }

                Constants.servers.add(new Server(name, server));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * AllAnime servers are decrypted, this method decrypts it
     *
     * @param decrypt - Server url to be decypted
     * @return - Decrypted server url
     */
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

    /**
     * Most of the server urls are stored in embedded site
     * This method parses embedded url and gets servers from it
     *
     * @param server - Anime server url
     */
    public static void connectAPITwo(String server) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(server)
                .header("Referer", "https://allanime.to")
                .header("Cipher", "AES256-SHA256")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0")
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String rawJSON = response.body().string();

            if (rawJSON.contains("error")) {
                return;
            }

            JSONObject jsonObject = new JSONObject(rawJSON);

            if (jsonObject.has("links")) {
                JSONArray linksArray = jsonObject
                        .getJSONArray("links");
                for (int i = 0; i < linksArray.length(); i++) {
                    String link = linksArray.getJSONObject(i).getString("link");

                    String name = link;

                    if (link.contains("kaavid")) {
                        name = "Kaavid";
                    } else if (link.contains("dropbox")) {
                        name = "Dropbox";
                    } else if (link.contains("sharepoint")) {
                        name = "Sharepoint";
                    } else if (link.contains("vipanicdn")) {
                        name = "Vipanicdn";
                    } else if (link.contains("anifastcdn")) {
                        name = "AniFastCDN";
                    } else if (link.contains("workfields")) {
                        name = "WorkFields";
                    } else if (link.contains("wixmp")) {
                        name = "WixMP";
                    }

                    Constants.servers.add(new Server(name, link));
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

    }

}
