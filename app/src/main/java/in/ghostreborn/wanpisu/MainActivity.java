package in.ghostreborn.wanpisu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import in.ghostreborn.wanpisu.fragment.AnimeSearchFragment;
import in.ghostreborn.wanpisu.fragment.UserAnimeFragment;

public class MainActivity extends AppCompatActivity {

    Fragment fragment;
    int previouslySelected = 0;

    // TODO drop grouping anime episodes and
    //  show a arrow on both sides to move towards next
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigationView = findViewById(R.id.main_bottom_navigation);
        navigationView.setSelectedItemId(R.id.navigation_home);
        navigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == previouslySelected){
                return true;
            }

            if (itemId == R.id.navigation_home){
                fragment = new AnimeSearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container, fragment)
                        .commit();
            }else if (itemId == R.id.navigation_user){
                fragment = new UserAnimeFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container, fragment)
                        .commit();
            }
            previouslySelected = itemId;
            return true;
        });

    }




}