package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);
        episodeRecycler = findViewById(R.id.episode_recycler);
        isJikan = !Constants.ANIME_MAL_ID.equals("null");

        getEpisodes();

        FloatingActionButton nextFloatingButton = findViewById(R.id.next_episodes_fab);
        nextFloatingButton.setOnClickListener(v -> {
            if (Constants.ANIME_CURRENT_PAGE!=Constants.ANIME_TOTAL_PAGES-1){
                Constants.ANIME_CURRENT_PAGE++;
                Constants.ALL_ANIME_EPISODE_ADD = Constants.ANIME_CURRENT_PAGE * 100;
                getEpisodes();
            }
        });

        FloatingActionButton previousFloatingButton = findViewById(R.id.previous_episodes_fab);
        previousFloatingButton.setOnClickListener(v -> {
            if (Constants.ANIME_CURRENT_PAGE!=0){
                Constants.ANIME_CURRENT_PAGE--;
                Constants.ALL_ANIME_EPISODE_ADD = Constants.ANIME_CURRENT_PAGE * 100;
                getEpisodes();
            }
        });

    }

    private void getTotalNext(boolean isJikan) {
        if (!isJikan) {
            Constants.ANIME_TOTAL_PAGES = Constants.episodes.size() / 100;
        }
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

                getTotalNext(isJikan);

            });
        });
    }


}