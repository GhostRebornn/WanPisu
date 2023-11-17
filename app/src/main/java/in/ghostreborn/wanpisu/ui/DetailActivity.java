package in.ghostreborn.wanpisu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.CharacterAdapter;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.database.UserAnimeDatabase;
import in.ghostreborn.wanpisu.helper.WanPisuUtils;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class DetailActivity extends AppCompatActivity {

    TextView detailAnimeNameText;
    TextView detailAnimeSynopsisText;
    Button detailSequelButton;
    Button detailPrequelButton;
    ImageView detailAnimeImageView;
    FloatingActionButton detailWatchFloatingButton;
    FloatingActionButton detailAddFloatingButton;
    ProgressBar detailsFragmentProgress;
    RecyclerView charactersRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViews();

        detailAddFloatingButton.setOnClickListener(v -> WanPisuUtils.addAnime(DetailActivity.this, detailAddFloatingButton));
        detailsFragmentProgress = findViewById(R.id.detail_fragment_progress);

        checkAnime();
        getDetails();
    }

    private void findViews(){
        detailAnimeNameText = findViewById(R.id.detail_anime_name_text);
        detailPrequelButton = findViewById(R.id.details_prequel_button);
        detailSequelButton = findViewById(R.id.details_sequel_button);
        detailAnimeSynopsisText = findViewById(R.id.detail_anime_synopsis_text);
        detailAnimeImageView = findViewById(R.id.detail_anime_image_view);
        detailWatchFloatingButton = findViewById(R.id.detail_watch_floating_button);
        detailAddFloatingButton = findViewById(R.id.detail_add_floating_button);
        charactersRecycler = findViewById(R.id.characters_recycler);
    }

    private void checkAnime(){
        try(UserAnimeDatabase database = new UserAnimeDatabase(DetailActivity.this)){
            if (WanPisuUtils.checkAnime(Constants.ANIME_ID, database.getReadableDatabase())){
                detailAddFloatingButton.setImageResource(R.drawable.minus);
            }else {
                detailAddFloatingButton.setImageResource(R.drawable.plus);
            }
        }
    }

    private void getDetails() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getAnimeDetails(Constants.ANIME_ID);
            handler.post(() -> {
                detailAnimeNameText.setText(Constants.animeDetails.getEnglishName());
                String prequel = Constants.animeDetails.getPrequel();
                String sequel = Constants.animeDetails.getSequel();
                if (!prequel.equals("")){
                    detailPrequelButton.setVisibility(View.VISIBLE);
                    detailPrequelButton.setOnClickListener(v -> {
                        Constants.ANIME_ID = Constants.animeDetails.getPrequel();
                        startActivity(new Intent(
                                DetailActivity.this,
                                DetailActivity.class
                        ));
                        finish();
                    });
                }
                if (!sequel.equals("")){
                    detailSequelButton.setVisibility(View.VISIBLE);
                    detailSequelButton.setOnClickListener(v -> {
                        Constants.ANIME_ID = Constants.animeDetails.getSequel();
                        startActivity(new Intent(
                                DetailActivity.this,
                                DetailActivity.class
                        ));
                        finish();
                    });
                }
                Picasso.get().load(Constants.animeDetails.getThumbnail()).into(detailAnimeImageView);
                detailWatchFloatingButton.setOnClickListener(v -> startActivity(
                        new Intent(DetailActivity.this, EpisodeActivity.class)
                ));
                detailsFragmentProgress.setVisibility(View.GONE);

                CharacterAdapter adapter = new CharacterAdapter(Constants.animeDetails.getAnimeCharacters());
                charactersRecycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
                charactersRecycler.setAdapter(adapter);

            });
        });
    }

}