package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class EpisodeGroupAdapter extends RecyclerView.Adapter<EpisodeGroupAdapter.ViewHolder> {

    int pages;
    RecyclerView episodeRecycler;
    Activity activity;
    FragmentManager fragmentManager;
    FrameLayout layout;

    public EpisodeGroupAdapter(
            int pages,
            RecyclerView episodeRecycler,
            Activity activity,
            FragmentManager fragmentManager,
            FrameLayout layout
    ) {
        this.pages = pages;
        this.episodeRecycler = episodeRecycler;
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.layout = layout;
    }

    @NonNull
    @Override
    public EpisodeGroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_group_list, parent, false);
        return new EpisodeGroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeGroupAdapter.ViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition();
        String page = pos + 1 + "";
        holder.episodeGroupTextView.setText(page);
        holder.episodeGroupTextView.setOnClickListener(v -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> handler.post(() -> {
                Constants.ANIME_CURRENT_PAGE = pos;
                EpisodeAdapter adapter = new EpisodeAdapter(
                        activity,
                        fragmentManager,
                        layout
                );
                LinearLayoutManager manager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
                episodeRecycler.setLayoutManager(manager);
                episodeRecycler.setAdapter(adapter);
            }));
        });
    }

    @Override
    public int getItemCount() {
        return pages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView episodeGroupTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            episodeGroupTextView = itemView.findViewById(R.id.anime_episode_group_text_view);
        }
    }

}