package in.ghostreborn.wanpisu.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AllAnimeAdapter;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.database.UserAnimeDatabase;
import in.ghostreborn.wanpisu.model.AllAnime;

public class UserAnimeFragment extends Fragment {

    RecyclerView userAnimeRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_anime, container, false);

        userAnimeRecycler = view.findViewById(R.id.user_anime_recycler);

        getUsersAnime();
        return view;
    }

    private void getUsersAnime(){
        Constants.userAnimes = new ArrayList<>();
        SQLiteDatabase db;
        try (UserAnimeDatabase database = new UserAnimeDatabase(getContext())) {
            db = database.getReadableDatabase();
            String query = "SELECT * FROM " + Constants.TABLE_NAME;
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String anime = cursor.getString(1);
                String thumbnail = cursor.getString(2);
                String desc = cursor.getString(3);
                Constants.userAnimes.add(new AllAnime(id, "",anime,thumbnail, desc));
            }
            cursor.close();
        }finally {
            userAnimeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
            userAnimeRecycler.setAdapter(new AllAnimeAdapter(Constants.userAnimes));
        }
    }

}