package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.EpisodeAdapter;

public class EpisodeActivity extends AppCompatActivity {

    RecyclerView episodeRecycler;
    public static FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);

        episodeRecycler = findViewById(R.id.episode_recycler);
        EpisodeAdapter adapter = new EpisodeAdapter(this,getSupportFragmentManager());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(manager);
        episodeRecycler.setAdapter(adapter);

    }
}