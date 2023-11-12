package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;
import in.ghostreborn.wanpisu.parser.JikanParser;

public class DetailActivity extends AppCompatActivity {

    TextView detailNameText;
    ImageView detailImageView;
    Button watchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailNameText = findViewById(R.id.detail_name_text);
        detailImageView = findViewById(R.id.detail_image_view);
        watchButton = findViewById(R.id.watch_button);
        getEpisodes();
        getDetails();

    }

    private void getEpisodes(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getEpisodes(Constants.ANIME_ID);
            handler.post(() -> watchButton.setOnClickListener(v -> startActivity(new Intent(
                    DetailActivity.this,
                    EpisodeActivity.class
            ))));
        });
    }

    private void getDetails() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            JikanParser.parseAnimeFull(Constants.ANIME_MAL_ID);
            handler.post(() -> {
                Jikan jikan = Constants.jikan;
                detailNameText.setText(jikan.getTitle());
                Picasso.get().load(jikan.getThumbnail()).into(detailImageView);
            });
        });
    }

}