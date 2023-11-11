package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class PlayerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        PlayerView playerView = findViewById(R.id.player_view);

        ExoPlayer player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        MediaItem mediaItem = MediaItem.fromUri(Constants.ANIME_SERVER);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

    }

}