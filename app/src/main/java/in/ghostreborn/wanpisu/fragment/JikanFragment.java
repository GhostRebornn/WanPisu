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
import in.ghostreborn.wanpisu.model.Jikan;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;
import in.ghostreborn.wanpisu.ui.EpisodeActivity;

public class JikanFragment extends Fragment {

    ImageView jikanAnimeImageView;
    TextView jikanAnimeNameText;
    TextView jikanAnimeTitlesText;
    TextView jikanAnimeTypeText;
    TextView jikanAnimeSourceText;
    TextView jikanAnimeEpisodesText;
    TextView jikanAnimeStatusText;
    TextView jikanAnimeAiredText;
    TextView jikanAnimeDurationText;
    TextView jikanAnimeRatingText;
    TextView jikanAnimeScoreText;
    TextView jikanAnimeSeasonText;
    TextView jikanAnimeBroadcastText;
    TextView jikanAnimeSynopsisText;
    FloatingActionButton watchFloatingButton;
    FloatingActionButton addFloatingButton;
    ProgressBar jikanFragmentProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jikan, container, false);
        findViews(view);

        addFloatingButton.setOnClickListener(v -> {
            UserAnimeDatabase database = new UserAnimeDatabase(getContext());
            SQLiteDatabase db = database.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.TABLE_ANIME_ID, Constants.allAnime.getId());
            values.put(Constants.TABLE_ANIME_NAME, Constants.allAnime.getName());
            values.put(Constants.TABLE_ANIME_THUMBNAIL, Constants.allAnime.getThumbnail());
            long rowID = db.insert(Constants.TABLE_NAME, null, values);
            Log.e("TAG", "rowID: " + rowID);
            db.close();
        });

        getJikans();
        return view;
    }

    private void findViews(View view){
        jikanAnimeImageView = view.findViewById(R.id.anime_image_view);
        jikanAnimeNameText = view.findViewById(R.id.jikan_anime_name_text);
        jikanAnimeTitlesText = view.findViewById(R.id.jikan_anime_titles_text);
        jikanAnimeTypeText = view.findViewById(R.id.jikan_anime_type_text);
        jikanAnimeSourceText = view.findViewById(R.id.jikan_anime_source_text);
        jikanAnimeEpisodesText = view.findViewById(R.id.jikan_anime_episodes_text);
        jikanAnimeStatusText = view.findViewById(R.id.jikan_anime_status_text);
        jikanAnimeAiredText = view.findViewById(R.id.jikan_anime_aired_text);
        jikanAnimeDurationText = view.findViewById(R.id.jikan_anime_duration_text);
        jikanAnimeRatingText = view.findViewById(R.id.jikan_anime_rating_text);
        jikanAnimeScoreText = view.findViewById(R.id.jikan_anime_score_text);
        jikanAnimeSeasonText = view.findViewById(R.id.jikan_anime_season_text);
        jikanAnimeBroadcastText = view.findViewById(R.id.jikan_anime_broadcast_text);
        jikanAnimeSynopsisText = view.findViewById(R.id.jikan_anime_synopsis_text);
        watchFloatingButton = view.findViewById(R.id.jikan_watch_floating_button);
        addFloatingButton = view.findViewById(R.id.jikan_add_floating_button);
        jikanFragmentProgress = view.findViewById(R.id.jikan_fragment_progress);
    }

    private void getJikans() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getEpisodes(Constants.ANIME_ID, false);
            handler.post(() -> {
                Jikan jikan = Constants.jikan;
                Picasso.get().load(jikan.getThumbnail()).into(jikanAnimeImageView);
                jikanAnimeNameText.setText(jikan.getTitle());
                StringBuilder titles = new StringBuilder();
                for (String title: jikan.getTitles()){
                    titles.append(title).append(" ");
                }
                jikanAnimeTitlesText.setText(titles);
                jikanAnimeTypeText.setText(jikan.getType());
                jikanAnimeSourceText.setText(jikan.getSource());
                jikanAnimeEpisodesText.setText(jikan.getEpisodes());
                jikanAnimeStatusText.setText(jikan.getStatus());
                jikanAnimeAiredText.setText(jikan.getAired());
                jikanAnimeDurationText.setText(jikan.getDuration());
                jikanAnimeRatingText.setText(jikan.getRating());
                jikanAnimeScoreText.setText(jikan.getScore());
                jikanAnimeSeasonText.setText(jikan.getSeason());
                jikanAnimeBroadcastText.setText(jikan.getBroadcast());
                jikanAnimeSynopsisText.setText(jikan.getSynopsis());
                watchFloatingButton.setOnClickListener(v -> requireContext().startActivity(
                        new Intent(getContext(), EpisodeActivity.class)
                ));
                jikanFragmentProgress.setVisibility(View.GONE);
            });
        });
    }

}