package in.ghostreborn.wanpisu.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.fragment.AnimeDetailFragment;
import in.ghostreborn.wanpisu.fragment.JikanFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.e("TAG", "MALID: " + Constants.ANIME_MAL_ID);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (Constants.ANIME_MAL_ID.equals("null")){
            transaction.add(R.id.details_frame_layout, new AnimeDetailFragment());
        }else {
            transaction.add(R.id.details_frame_layout, new JikanFragment());
        }
        transaction.commit();

    }

}