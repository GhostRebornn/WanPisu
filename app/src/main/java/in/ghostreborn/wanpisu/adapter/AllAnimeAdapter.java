package in.ghostreborn.wanpisu.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.ui.DetailActivity;
import in.ghostreborn.wanpisu.ui.EpisodeActivity;

/**
 * Adapter used for the list view of anime
 */
public class AllAnimeAdapter extends RecyclerView.Adapter<AllAnimeAdapter.ViewHolder> {

    ArrayList<AllAnime> allAnimes;
    public AllAnimeAdapter(ArrayList<AllAnime> allAnimes){
        this.allAnimes = allAnimes;
    }

    @NonNull
    @Override
    public AllAnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAnimeAdapter.ViewHolder holder, int position) {

        AllAnime allAnime = allAnimes.get(position);
        holder.animeTextView.setText(allAnime.getName());
        Picasso.get().load(allAnime.getThumbnail()).into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            if (holder.animeInfoLinearLayout.getVisibility() == View.VISIBLE){
                holder.animeInfoLinearLayout.setVisibility(View.GONE);
            }else {
                holder.animeInfoLinearLayout.animate()
                                .alpha(0.85f)
                                        .setDuration(250)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                holder.animeInfoLinearLayout.setVisibility(View.VISIBLE);
                            }
                        })
                        .start();
            }
        });
        holder.animeInfoButton.setOnClickListener(v -> {
            setupVariables(allAnime);
            Constants.allAnime = allAnime;
            Context context = holder.itemView.getContext();
            context.startActivity(new Intent(
                    context, DetailActivity.class
            ));
        });
        holder.animeWatchButton.setOnClickListener(v -> {
            setupVariables(allAnime);
            Context context = holder.itemView.getContext();
            context.startActivity(new Intent(
                    context, EpisodeActivity.class
            ));
        });
    }

    private void setupVariables(AllAnime allAnime){
        Constants.ANIME_ID = allAnime.getId();
        Constants.ANIME_MAL_ID = allAnime.getMalID();
    }

    @Override
    public int getItemCount() {
        return allAnimes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;
        public LinearLayout animeInfoLinearLayout;
        public Button animeInfoButton;
        public Button animeWatchButton;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.anime_text_view);
            animeInfoLinearLayout = itemView.findViewById(R.id.anime_info_linear_layout);
            animeImageView = itemView.findViewById(R.id.anime_image_view);
            animeInfoButton = itemView.findViewById(R.id.anime_info_button);
            animeWatchButton = itemView.findViewById(R.id.anime_watch_button);
        }
    }

}