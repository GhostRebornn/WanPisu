package in.ghostreborn.wanpisu.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.database.UserAnimeDatabase;

public class WanPisuUtils {

    public static boolean checkAnime(String animeID, SQLiteDatabase db) {
        String query = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id.equals(animeID)) {
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public static void addAnime(Context context, FloatingActionButton detailAddFloatingButton){
        UserAnimeDatabase database = new UserAnimeDatabase(context);
        SQLiteDatabase db = database.getWritableDatabase();
        if(WanPisuUtils.checkAnime(Constants.ANIME_ID, database.getReadableDatabase())){
            String whereClause = Constants.TABLE_ANIME_ID + " = ?";
            String[] whereArgs = {Constants.ANIME_ID};
            db.delete(Constants.TABLE_NAME, whereClause, whereArgs);
            db.close();
            Toast.makeText(context, "Removed!", Toast.LENGTH_SHORT).show();
            detailAddFloatingButton.setImageResource(R.drawable.plus);
        }else {
            ContentValues values = new ContentValues();
            values.put(Constants.TABLE_ANIME_ID, Constants.ANIME_ID);
            values.put(Constants.TABLE_ANIME_NAME, Constants.animeDetails.getEnglishName());
            values.put(Constants.TABLE_ANIME_THUMBNAIL, Constants.animeDetails.getThumbnail());
            long rowID = db.insert(Constants.TABLE_NAME, null, values);
            Log.e("TAG", "rowID: " + rowID);
            db.close();
            Toast.makeText(context, "Added!", Toast.LENGTH_SHORT).show();
            detailAddFloatingButton.setImageResource(R.drawable.minus);
        }

    }

}
