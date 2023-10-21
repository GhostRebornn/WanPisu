package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.AllAnimeAdapter;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class AnimeSearchFragment extends Fragment {

    RecyclerView allAnimeRecycler;
    SearchView allAnimeSearchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_search,container, false);
        allAnimeRecycler = view.findViewById(R.id.all_anime_recycler);
        allAnimeSearchView = view.findViewById(R.id.all_anime_search);
        searchAnime("");

        allAnimeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAnime(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void searchAnime(String anime){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.searchAnime(anime);
            handler.post(() -> {
                allAnimeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                allAnimeRecycler.setAdapter(new AllAnimeAdapter());
            });
        });
    }

}