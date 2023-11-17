package in.ghostreborn.wanpisu.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.database.UserAnimeDatabase;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class DetailActivity extends AppCompatActivity {

    TextView detailAnimeNameText;
    TextView detailAnimeSynopsisText;
    ImageView detailAnimeImageView;
    FloatingActionButton detailWatchFloatingButton;
    FloatingActionButton detailAddFloatingButton;
    ProgressBar detailsFragmentProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailAnimeNameText = findViewById(R.id.detail_anime_name_text);
        detailAnimeSynopsisText = findViewById(R.id.detail_anime_synopsis_text);
        detailAnimeImageView = findViewById(R.id.detail_anime_image_view);
        detailWatchFloatingButton = findViewById(R.id.detail_watch_floating_button);
        detailAddFloatingButton = findViewById(R.id.detail_add_floating_button);

        detailAddFloatingButton.setOnClickListener(v -> {
            UserAnimeDatabase database = new UserAnimeDatabase(DetailActivity.this);
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.TABLE_ANIME_ID, Constants.ANIME_ID);
            values.put(Constants.TABLE_ANIME_NAME, Constants.animeDetails.getEnglishName());
            values.put(Constants.TABLE_ANIME_THUMBNAIL, Constants.animeDetails.getThumbnail());
            long rowID = db.insert(Constants.TABLE_NAME, null, values);
            Log.e("TAG", "rowID: " + rowID);
            db.close();
        });

        detailsFragmentProgress = findViewById(R.id.detail_fragment_progress);

        getDetails();
    }

    private void getDetails() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getAnimeDetails(Constants.ANIME_ID);
            handler.post(() -> {
                detailAnimeNameText.setText(Constants.animeDetails.getEnglishName());
                Picasso.get().load(Constants.animeDetails.getThumbnail()).into(detailAnimeImageView);
                detailWatchFloatingButton.setOnClickListener(v -> startActivity(
                        new Intent(DetailActivity.this, EpisodeActivity.class)
                ));
                detailsFragmentProgress.setVisibility(View.GONE);
            });
        });
    }

}