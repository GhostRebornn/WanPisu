package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.EpisodeAdapter;
import in.ghostreborn.wanpisu.adapter.EpisodeGroupAdapter;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class EpisodeActivity extends AppCompatActivity {

    boolean isJikan;
    FrameLayout layout;
    EpisodeAdapter adapter;
    EpisodeGroupAdapter groupAdapter;
    ProgressBar episodeProgress;
    LinearLayoutManager manager;
    GridLayoutManager groupManager;
    RecyclerView episodeRecycler;
    RecyclerView episodeGroupRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);
        episodeRecycler = findViewById(R.id.episode_recycler);
        episodeGroupRecycler = findViewById(R.id.episode_group_recycler);
        episodeProgress = findViewById(R.id.episode_progress);
        isJikan = !Constants.ANIME_MAL_ID.equals("null") && !Constants.ANIME_MAL_ID.equals("");

        getEpisodes();

    }

    private void getEpisodes() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getEpisodes(
                    Constants.ANIME_ID
            );
            handler.post(() -> {
                setEpisodeAdapter();
                setEpisodeGroupAdapter();
                episodeProgress.setVisibility(View.GONE);
            });
        });
    }

    private void setEpisodeGroupAdapter() {
        int pages = getPages(isJikan);
        if (pages != 1) {
            groupAdapter = new EpisodeGroupAdapter(
                    pages,
                    isJikan,
                    episodeRecycler,
                    EpisodeActivity.this,
                    getSupportFragmentManager(),
                    layout
            );
            groupManager = new GridLayoutManager(EpisodeActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
            episodeGroupRecycler.setLayoutManager(groupManager);
            episodeGroupRecycler.setAdapter(groupAdapter);
        }
    }

    private void setEpisodeAdapter() {
        adapter = new EpisodeAdapter(
                EpisodeActivity.this,
                getSupportFragmentManager(),
                layout
        );
        manager = new LinearLayoutManager(EpisodeActivity.this, LinearLayoutManager.VERTICAL, false);
        episodeRecycler.setLayoutManager(manager);
        episodeRecycler.setAdapter(adapter);
    }

    private int getPages(boolean isJikan) {
        if (isJikan) {
            return Constants.ANIME_TOTAL_PAGES;
        } else {
            if (Constants.ALL_ANIME_TOTAL_EPISODES % 100 == 0) {
                return Constants.ALL_ANIME_TOTAL_EPISODES / 100;
            } else {
                return (Constants.ALL_ANIME_TOTAL_EPISODES / 100) + 1;
            }
        }
    }

}