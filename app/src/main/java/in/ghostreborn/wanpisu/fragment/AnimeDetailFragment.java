package in.ghostreborn.wanpisu.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.database.UserAnimeDatabase;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;
import in.ghostreborn.wanpisu.ui.EpisodeActivity;

public class AnimeDetailFragment extends Fragment {

    TextView detailAnimeNameText;
    TextView detailAnimeSynopsisText;
    ImageView detailAnimeImageView;
    FloatingActionButton detailWatchFloatingButton;
    FloatingActionButton detailAddFloatingButton;
    ProgressBar detailsFragmentProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_detail, container, false);

        detailAnimeNameText = view.findViewById(R.id.detail_anime_name_text);
        detailAnimeSynopsisText = view.findViewById(R.id.detail_anime_synopsis_text);
        detailAnimeImageView = view.findViewById(R.id.detail_anime_image_view);
        detailWatchFloatingButton = view.findViewById(R.id.detail_watch_floating_button);
        detailAddFloatingButton = view.findViewById(R.id.detail_add_floating_button);

        detailAddFloatingButton.setOnClickListener(v -> {
            UserAnimeDatabase database = new UserAnimeDatabase(getContext());
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.TABLE_ANIME_ID, Constants.ANIME_ID);
            values.put(Constants.TABLE_ANIME_NAME, Constants.animeDetails.getEnglishName());
            values.put(Constants.TABLE_ANIME_THUMBNAIL, Constants.animeDetails.getThumbnail());
            long rowID = db.insert(Constants.TABLE_NAME, null, values);
            Log.e("TAG", "rowID: " + rowID);
            db.close();
        });

        detailsFragmentProgress = view.findViewById(R.id.detail_fragment_progress);

        getDetails();
        return view;
    }

    private void getDetails() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getAnimeDetails(Constants.ANIME_ID);
            handler.post(() -> {
                detailAnimeNameText.setText(Constants.animeDetails.getEnglishName());
                Picasso.get().load(Constants.animeDetails.getThumbnail()).into(detailAnimeImageView);
                detailWatchFloatingButton.setOnClickListener(v -> requireContext().startActivity(
                        new Intent(getContext(), EpisodeActivity.class)
                ));
                detailsFragmentProgress.setVisibility(View.GONE);
            });
        });
    }

}