package in.ghostreborn.wanpisu.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AllAnimeAdapter;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class AnimeSearchFragment extends Fragment {

    RecyclerView allAnimeRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_search,container, false);
        allAnimeRecycler = view.findViewById(R.id.all_anime_recycler);
        new AnimeSearchAsync().execute();
        return view;
    }

    class AnimeSearchAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            AllAnimeParser.searchAnime("");
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
            allAnimeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
            allAnimeRecycler.setAdapter(new AllAnimeAdapter());
        }
    }
}