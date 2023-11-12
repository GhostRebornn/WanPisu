package in.ghostreborn.wanpisu.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.ViewHolder> {

    @NonNull
    @Override
    public EpisodeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_group_list, parent, false);
        return new EpisodeGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeGroupAdapter.ViewHolder holder, int position) {
        holder.episodeGroupTextView.setText(Constants.episodeGroup.get(position));
    }

    @Override
    public int getItemCount() {
        return Constants.episodeGroup.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView episodeGroupTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.anime_episode_group_text_view);
        }
    }

}