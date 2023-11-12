package in.ghostreborn.wanpisu.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.constants.Constants;

public class WanPisuUtils {

    public static boolean checkAnime(String animeID, SQLiteDatabase db) {
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            Log.e("TAG", "id: " + id);
            if (id.equals(animeID)) {
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public static void setupEpisodeGroups(){
        int currentEpisode = 1;
        int lastEpisode = Constants.ALL_ANIME_TOTAL_EPISODES;

        if (lastEpisode < 100){
            return;
        }

        Constants.episodeGroup = new ArrayList<>();
        String episodeGroup;
        for (int i = 0; i < lastEpisode; i += 100) {
            episodeGroup = (i + 1) + " - " + (i + 100);
            Constants.episodeGroup.add(episodeGroup);
            currentEpisode = i + 1;
        }
        episodeGroup = currentEpisode + " - " + lastEpisode;
        if (Constants.episodeGroup.size() > 2) {
            Constants.episodeGroup.remove(Constants.episodeGroup.size() - 1);
            Constants.episodeGroup.add(episodeGroup);
        } else {
            Constants.episodeGroup.add(episodeGroup);
        }
    }

}
