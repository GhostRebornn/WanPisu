package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class ServersFragment extends Fragment {

    TextView testText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servers, container, false);
        testText = view.findViewById(R.id.test_text);
        getServers();
        return view;
    }

    private void getServers() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            AllAnimeParser.getServers(
                    Constants.ANIME_ID,
                    Constants.ANIME_EPISODE
            );
            handler.post(() -> {
                for (String server : Constants.servers) {
                    testText.append(server + "\n\n");
                }
            });
        });
    }

}