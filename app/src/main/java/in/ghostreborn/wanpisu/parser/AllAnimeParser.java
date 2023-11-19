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
import in.ghostreborn.wanpisu.model.AnimeCharacter;
import in.ghostreborn.wanpisu.model.AnimeDetails;
import in.ghostreborn.wanpisu.model.AnimeMusic;
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
        String queryUrl = "https://api.allanime.day/api?variables=" + Uri.encode(
                "{\"search\":{\"allowAdult\":" +
                        Constants.isAdult +
                        ",\"allowUnknown\":" +
                        Constants.isUnknown +
                        ",\"query\":\"" + anime + "\"},\"limit\":39,\"page\":1,\"translationType\":\"" +
                        Constants.subOrDub +
                        "\",\"countryOrigin\":\"ALL\"}") + "&query=" + Uri.encode("query($search:SearchInput,$limit:Int,$page:Int,$translationType:VaildTranslationTypeEnumType,$countryOrigin:VaildCountryOriginEnumType){shows(search:$search,limit:$limit,page:$page,translationType:$translationType,countryOrigin:$countryOrigin){edges{" +
                "_id, " +
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
                    String animeName = edges.getString("name");
                    String thumbnail = edges.getString("thumbnail");
                    Constants.allAnimes.add(new AllAnime(animeID, animeName, thumbnail));
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAnimeDetails(String allAnimeID) {
        OkHttpClient client = new OkHttpClient();

        String queryParameter = "query ($showId: String!) { show( _id: $showId ) { " +
                "_id, " +
                "name, " +
                "characters, " +
                "englishName, " +
                "thumbnail, " +
                "musics, " +
                "relatedShows " +
                " }}";

        ArrayList<AnimeCharacter> animeCharacters = new ArrayList<>();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse("https://api.allanime.day/api")).newBuilder();
        urlBuilder.addQueryParameter("variables", "{\"showId\":\"" + allAnimeID + "\"}");
        urlBuilder.addQueryParameter("query", queryParameter);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; rv:109.0) Gecko/20100101 Firefox/109.0").addHeader("Referer", "https://allanime.to").addHeader("Cipher", "AES256-SHA256").build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;

            String rawJSON = response.body().string();
            Log.e("TAG", rawJSON);

            JSONObject showObject = new JSONObject(rawJSON)
                    .getJSONObject("data")
                    .getJSONObject("show");

            String name = showObject.getString("name");
            String englishName = showObject.getString("englishName");
            if (!englishName.equals("null")) {
                name = englishName;
            }

            String thumbnail = showObject.getString("thumbnail");
            String sequel = "";
            String prequel = "";
            if (showObject.has("relatedShows")) {
                JSONArray relationArray = showObject.getJSONArray("relatedShows");
                for (int i = 0; i < relationArray.length(); i++) {
                    JSONObject relationObject = relationArray.getJSONObject(i);
                    String relation = relationObject.getString("relation");
                    if (relation.equals("prequel")) {
                        prequel = relationObject.getString("showId");
                    }
                    if (relation.equals("sequel")) {
                        sequel = relationObject.getString("showId");
                    }
                }
            }

            JSONArray charactersArray = showObject.getJSONArray("characters");
            for (int i = 0; i < charactersArray.length(); i++) {
                JSONObject charactersObject = charactersArray.getJSONObject(i);
                String role = charactersObject.getString("role");
                String characterName = charactersObject.getJSONObject("name")
                        .getString("full");
                String image = charactersObject.getJSONObject("image")
                        .getString("large");
                animeCharacters.add(new AnimeCharacter(role, characterName, image));
            }

            ArrayList<AnimeMusic> animeMusics = new ArrayList<>();
            if (showObject.has("musics")) {
                JSONArray musicArray = showObject.getJSONArray("musics");
                Log.e("TAG", musicArray.toString());
                for (int i = 0; i < musicArray.length(); i++) {
                    JSONObject musicObject = musicArray.getJSONObject(i);
                    String type = musicObject.getString("type");
                    String title = musicObject.getString("title");
                    String format = musicObject.getString("format");
                    String musicUrl = musicObject.getString("url");
                    animeMusics.add(new AnimeMusic(
                            type,
                            title,
                            format,
                            musicUrl
                    ));
                }
            }

            Constants.animeDetails = new AnimeDetails(
                    name,
                    thumbnail,
                    sequel,
                    prequel,
                    animeCharacters,
                    animeMusics
            );

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * AllAnime api to parse available episodes of anime
     *
     * @param allAnimeID - AllAnime anime ID
     */
    public static void getEpisodes(String allAnimeID) {
        Constants.episodes = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();

        String queryParameter = "query ($showId: String!) { show( _id: $showId ) { " +
                "name, " +
                "thumbnail, " +
                "description," +
                "availableEpisodesDetail " +
                " }}";

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

            for (int i = episodes.length() - 1; i >= 0; i--) {
                Constants.episodes.add(
                        episodes.getString(i)
                );
            }

            Constants.ALL_ANIME_TOTAL_EPISODES = episodes.length();

            String name = showObject.getString("name");
            String thumbnail = showObject.getString("thumbnail");
            Constants.allAnime = new AllAnime(
                    allAnimeID,
                    name,
                    thumbnail
            );

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
                        "\",\"translationType\":\"" +
                        Constants.subOrDub +
                        "\",\"episodeString\":\"" +
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

    public static String getTitle(String showID, String episodeNumber) {

        OkHttpClient client = new OkHttpClient();
        Constants.servers = new ArrayList<>();

        String baseUrl = "https://api.allanime.day/api";
        String queryUrl = baseUrl + "?variables=" +
                Uri.encode("{\"showId\":\"" +
                        showID +
                        "\",\"translationType\":\"" +
                        Constants.subOrDub +
                        "\",\"episodeString\":\"" +
                        episodeNumber +
                        "\"}") +
                "&query=" +
                Uri.encode("query($showId:String!,$translationType:VaildTranslationTypeEnumType!,$episodeString:String!){episode(showId:$showId,translationType:$translationType,episodeString:$episodeString){" +
                        "episodeInfo{notes}" +
                        "}}");

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
            JSONObject episodeInfo = jsonObject
                    .getJSONObject("data")
                    .getJSONObject("episode")
                    .getJSONObject("episodeInfo");
            return episodeInfo.getString("notes");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return "NULL";

    }

}
