package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.Details;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class AnimeDetailFragment extends Fragment {

    TextView detailNameText;
    TextView detailDescText;
    ImageView detailImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anime_detail, container, false);
        detailNameText = view.findViewById(R.id.detail_name_text);
        detailDescText = view.findViewById(R.id.detail_desc_text);
        detailImageView = view.findViewById(R.id.detail_image_view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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