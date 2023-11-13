package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.fragment.ServersFragment;

/**
 * Adapter used for the list view of episodes of anime
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    Activity activity;
    FragmentManager fragmentManager;
    FrameLayout layout;
    ArrayList<String> episodes;
    public EpisodeAdapter(Activity activity, FragmentManager fragmentManager, FrameLayout layout, ArrayList<String> episodes){
        this.activity = activity;
        this.episodes = episodes;
        this.fragmentManager = fragmentManager;
        this.layout = layout;
    }

    @NonNull
    @Override
    public EpisodeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_list, parent, false);
        return new EpisodeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition() + Constants.ALL_ANIME_EPISODE_ADD;
        String episode = episodes.get(pos);
        String episodeText = "Episode " + episode;
        holder.animeTextView.setText(episodeText);
        holder.itemView.setOnClickListener(v -> {
            Constants.ANIME_EPISODE = episode;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.server_fragment_container, new ServersFragment(layout));
            transaction.commit();
            layout.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        if (Constants.ALL_ANIME_EPISODE_ADD < (Constants.ALL_ANIME_TOTAL_EPISODES - 100)){
            return 100;
        }else {
            return Constants.ALL_ANIME_TOTAL_EPISODES - Constants.ALL_ANIME_EPISODE_ADD + 1;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public ImageView animeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.episode_text_view);
            animeImageView = itemView.findViewById(R.id.episode_image_view);
        }
    }

}