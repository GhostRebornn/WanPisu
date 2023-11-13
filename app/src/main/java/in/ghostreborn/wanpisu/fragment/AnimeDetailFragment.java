package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class AnimeDetailFragment extends Fragment {

    TextView detailAnimeNameText;
    TextView detailAnimeSynopsisText;
    ImageView detailAnimeImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_detail, container, false);

        detailAnimeNameText = view.findViewById(R.id.detail_anime_name_text);
        detailAnimeSynopsisText = view.findViewById(R.id.detail_anime_synopsis_text);
        detailAnimeImageView = view.findViewById(R.id.detail_anime_image_view);

        getDetails();
        return view;
    }

    private void getDetails() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getEpisodes(Constants.ANIME_ID, true);
            handler.post(() -> {
                AllAnime allAnime = Constants.allAnime;
                detailAnimeNameText.setText(allAnime.getName());
                detailAnimeSynopsisText.setText(allAnime.getDescription());
                Picasso.get().load(allAnime.getThumbnail()).into(detailAnimeImageView);
            });
        });
    }

}