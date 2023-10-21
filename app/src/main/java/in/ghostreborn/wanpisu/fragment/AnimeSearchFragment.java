package in.ghostreborn.wanpisu.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
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

    class AnimeSearchAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return AllAnimeParser.searchAnime("");
        }

        @Override
        protected void onPostExecute(String output) {
            super.onPostExecute(output);
        }
    }
}