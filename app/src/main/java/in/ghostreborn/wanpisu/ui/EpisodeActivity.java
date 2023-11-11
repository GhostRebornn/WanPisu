package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.EpisodeAdapter;

public class EpisodeActivity extends AppCompatActivity {

    public static RecyclerView episodeRecycler;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        layout = findViewById(R.id.server_fragment_container);

        episodeRecycler = findViewById(R.id.episode_recycler);
        EpisodeAdapter adapter = new EpisodeAdapter(this,getSupportFragmentManager(), layout);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        episodeRecycler.setLayoutManager(manager);
        episodeRecycler.setAdapter(adapter);

    }
}