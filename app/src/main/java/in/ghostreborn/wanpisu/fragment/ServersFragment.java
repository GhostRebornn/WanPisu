package in.ghostreborn.wanpisu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.adapter.ServersAdapter;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.parser.AllAnimeParser;

public class ServersFragment extends Fragment {

    RecyclerView serversRecycler;
    ProgressBar serverProgress;
    Button closeButton;
    FrameLayout layout;

    public ServersFragment(FrameLayout layout){
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servers, container, false);
        serversRecycler = view.findViewById(R.id.servers_recycler);
        serverProgress = view.findViewById(R.id.server_progress);
        closeButton = view.findViewById(R.id.close_button);

        closeButton.setOnClickListener(v -> {
            FragmentManager manager = requireActivity()
                    .getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(
                    Objects.requireNonNull(manager.findFragmentById(R.id.server_fragment_container))
            );
            transaction.commit();
            layout.setVisibility(View.GONE);
        });

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
                serverProgress.setVisibility(View.GONE);
                ServersAdapter adapter = new ServersAdapter(getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                serversRecycler.setLayoutManager(manager);
                serversRecycler.setAdapter(adapter);
            });
        });
    }

}