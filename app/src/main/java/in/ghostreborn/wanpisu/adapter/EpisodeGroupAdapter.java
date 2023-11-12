package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.ViewHolder> {

    RecyclerView recyclerView;
    Activity activity;
    FragmentManager manager;
    FrameLayout layout;
    public EpisodeGroupAdapter(
            RecyclerView recyclerView,
            Activity activity,
            FragmentManager manager,
            FrameLayout layout
    ){
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.manager = manager;
        this.layout = layout;
    }

    @NonNull
    @Override
    public EpisodeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episode_group_list, parent, false);
        return new EpisodeGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeGroupAdapter.ViewHolder holder, int position) {
        holder.episodeGroupTextView.setText(Constants.episodeGroup.get(holder.getAbsoluteAdapterPosition()));
        holder.itemView.setOnClickListener(v -> {
            Constants.ALL_ANIME_EPISODE_ADD = holder.getAbsoluteAdapterPosition() * 100;
            EpisodeAdapter adapter = new EpisodeAdapter(
                    activity,
                    manager,
                    layout
            );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        });
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