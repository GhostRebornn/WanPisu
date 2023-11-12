package in.ghostreborn.wanpisu.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;
import in.ghostreborn.wanpisu.ui.PlayerActivity;

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.ViewHolder> {

    Context context;

    public ServersAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ServersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.servers_list,
                        parent,
                        false
                );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServersAdapter.ViewHolder holder, int position) {
        holder.serversTextView.setText(Constants.servers.get(position).getName());
        holder.itemView.setOnClickListener(v -> {
            Constants.ANIME_SERVER = Constants.servers.get(position).getUrl();
            context.startActivity(
                    new Intent(context, PlayerActivity.class)
            );
        });
    }

    @Override
    public int getItemCount() {
        return Constants.servers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView serversTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serversTextView = itemView.findViewById(R.id.server_text);
        }
    }
}
