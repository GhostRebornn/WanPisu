package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.EpisodeAdapter;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;
import in.ghostreborn.wanpisu.parser.JikanParser;

public class EpisodeActivity extends AppCompatActivity {

    public static RecyclerView episodeRecycler;
    FrameLayout layout;
    EpisodeAdapter adapter;
    LinearLayoutManager manager;
    boolean isJikan;
    ProgressBar episodeProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);
        episodeRecycler = findViewById(R.id.episode_recycler);
        episodeProgress = findViewById(R.id.episode_progress);
        isJikan = !Constants.ANIME_MAL_ID.equals("null") && !Constants.ANIME_MAL_ID.equals("");

        getEpisodes();

    }

    private void getEpisodes() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            if (isJikan){
                AllAnimeParser.getEpisodes(Constants.ANIME_ID, false);
                JikanParser.getEpisodes(Constants.ANIME_MAL_ID, Constants.ANIME_CURRENT_PAGE+1 + "");
            }else {
                AllAnimeParser.getEpisodes(Constants.ANIME_ID, true);
            }
            handler.post(() -> {
                adapter = new EpisodeAdapter(
                        EpisodeActivity.this,
                        getSupportFragmentManager(),
                        layout,
                        isJikan
                );
                manager = new LinearLayoutManager(EpisodeActivity.this);
                episodeRecycler.setLayoutManager(manager);
                episodeRecycler.setAdapter(adapter);

                episodeProgress.setVisibility(View.GONE);

            });
        });
    }


}