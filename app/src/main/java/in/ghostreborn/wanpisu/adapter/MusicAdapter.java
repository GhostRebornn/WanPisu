package in.ghostreborn.wanpisu.adapter;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.model.AnimeMusic;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    ArrayList<AnimeMusic> animeMusics;
    public MusicAdapter(ArrayList<AnimeMusic> animeMusics){
        this.animeMusics = animeMusics;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list, parent, false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        holder.musicPlayButton.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = new MediaPlayer();
            String url = animeMusics.get(position).getUrl();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeMusics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button musicPlayButton;

        public ViewHolder(View itemView) {
            super(itemView);
            musicPlayButton = itemView.findViewById(R.id.music_play_button);
        }
    }

}