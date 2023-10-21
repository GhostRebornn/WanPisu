package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.model.AllAnime;
import in.ghostreborn.wanpisu.ui.DetailActivity;

public class AllAnimeAdapter extends RecyclerView.Adapter<AllAnimeAdapter.ViewHolder> {

    @NonNull
    @Override
    public AllAnimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anime_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllAnimeAdapter.ViewHolder holder, int position) {

        AllAnime allAnime = Constants.allAnimes.get(position);
        holder.animeTextView.setText(allAnime.getName());
        Picasso.get().load(allAnime.getThumbnail()).into(holder.animeImageView);
        holder.itemView.setOnClickListener(v -> {
            Constants.ANIME_ID = allAnime.getId();
            Context context = holder.itemView.getContext();
            context.startActivity(new Intent(
                context, DetailActivity.class
            ));
        });
    }

    @Override
    public int getItemCount() {
        return Constants.allAnimes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.anime_text_view);
            animeImageView = itemView.findViewById(R.id.anime_image_view);
        }
    }

}