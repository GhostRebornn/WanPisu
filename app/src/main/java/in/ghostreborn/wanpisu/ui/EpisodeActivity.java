package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.EpisodeAdapter;
import in.ghostreborn.wanpisu.adapter.EpisodeGroupAdapter;

public class EpisodeActivity extends AppCompatActivity {

    public static RecyclerView episodeRecycler;
    public static RecyclerView episodeGroupRecycler;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);

        episodeRecycler = findViewById(R.id.episode_recycler);
        episodeGroupRecycler = findViewById(R.id.episode_group_recycler);

        EpisodeAdapter adapter = new EpisodeAdapter(this,getSupportFragmentManager(), layout);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(manager);
        episodeRecycler.setAdapter(adapter);

        EpisodeGroupAdapter groupAdapter = new EpisodeGroupAdapter();
        LinearLayoutManager groupManager = new LinearLayoutManager(this);
        episodeGroupRecycler.setAdapter(groupAdapter);
        episodeGroupRecycler.setLayoutManager(groupManager);

    }
}