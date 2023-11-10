package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.constants.Constants;

public class ServersAdapter extends RecyclerView.Adapter<ServersAdapter.ViewHolder> {

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
        holder.serversTextView.setText(Constants.servers.get(position));
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
