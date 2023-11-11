package in.ghostreborn.wanpisu.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

}
