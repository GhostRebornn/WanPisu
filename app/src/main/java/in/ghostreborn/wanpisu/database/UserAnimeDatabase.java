package in.ghostreborn.wanpisu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import in.ghostreborn.wanpisu.constants.Constants;

public class UserAnimeDatabase extends SQLiteOpenHelper {

    public UserAnimeDatabase(Context context) {
        super(context, "wanpisu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + Constants.TABLE_NAME +
                        "(" +
                        Constants.TABLE_ANIME_NAME + " TEXT," +
                        Constants.TABLE_ANIME_THUMBNAIL + " TEXT," +
                        Constants.TABLE_ANIME_DESC + " TEXT" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
