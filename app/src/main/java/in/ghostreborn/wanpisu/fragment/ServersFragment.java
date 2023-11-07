package in.ghostreborn.wanpisu.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

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
        new SeversAsync().execute();

        return view;
    }

    class SeversAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Constants.servers = new ArrayList<>();
            AllAnimeParser.getServers("ReooPAxPMsHM4KPMY", "1");
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            testText.setText("");
            for (String server: Constants.servers){
                if (server.contains("--")){
                    server = AllAnimeParser.decryptAllAnimeServer(server.substring(2));
                    if (server.contains("clock")){
                        server ="https://embed.ssbcontent.site/apivtwo/clock.json?id=" + server.substring(18);
                    }
                }
                testText.append(server + "\n\n");
            }
        }
    }

}