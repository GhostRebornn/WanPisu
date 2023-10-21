package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.Details;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class DetailActivity extends AppCompatActivity {

    TextView detailNameText;
    TextView detailDescText;
    ImageView detailImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailNameText = findViewById(R.id.detail_name_text);
        detailDescText = findViewById(R.id.detail_desc_text);
        detailImageView = findViewById(R.id.detail_image_view);

        getDetails();

    }

    private void getDetails(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {

            AllAnimeParser.getEpisodes(Constants.ANIME_ID);
            Details details = Constants.details.get(0);

            handler.post(() -> {
                detailNameText.setText(details.getName());
                detailDescText.setText(details.getDescription());
                Picasso.get().load(details.getThumbnail()).into(detailImageView);
            });
        });
    }

}