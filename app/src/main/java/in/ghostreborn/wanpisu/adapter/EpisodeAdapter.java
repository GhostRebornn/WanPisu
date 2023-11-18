package in.ghostreborn.wanpisu.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.fragment.ServersFragment;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

/**
 * Adapter used for the list view of episodes of anime
 */
public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder> {

    Activity activity;
    FragmentManager fragmentManager;
    FrameLayout layout;
    String episodeNumber;
    int pos;
    String episodeText;

    public EpisodeAdapter(Activity activity, FragmentManager fragmentManager, FrameLayout layout) {
        this.activity = activity;
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
        pos = holder.getAbsoluteAdapterPosition() + (Constants.ANIME_CURRENT_PAGE * 25);

        episodeNumber = Constants.episodes.get(pos);
        episodeText = "Episode " + episodeNumber;

        getTitle(holder, episodeNumber);

        holder.animeTextView.setText(episodeText);
        holder.animeEpisodeTextView.setText(episodeNumber);
        holder.itemView.setOnClickListener(v -> {
            Constants.ANIME_EPISODE = episodeNumber;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.server_fragment_container, new ServersFragment(layout));
            transaction.commit();
            layout.setVisibility(View.VISIBLE);
        });
    }

    private void getTitle(EpisodeAdapter.ViewHolder holder, String episode) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            String title = AllAnimeParser.getTitle(Constants.ANIME_ID, episode);
            handler.post(() -> {
                if (!title.equals("null")&&!title.equals("NULL")){
                    holder.animeTextView.setText(title);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        if (Constants.ANIME_CURRENT_PAGE + 1 < Constants.ANIME_TOTAL_PAGES) {
            return 25;
        } else {
            return Constants.ALL_ANIME_TOTAL_EPISODES % 25;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView animeTextView;
        public TextView animeEpisodeTextView;
        public ImageView animeImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            animeTextView = itemView.findViewById(R.id.episode_text_view);
            animeEpisodeTextView = itemView.findViewById(R.id.episode_number_text);
            animeImageView = itemView.findViewById(R.id.episode_image_view);
        }
    }

}