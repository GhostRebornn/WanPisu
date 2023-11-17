package in.ghostreborn.wanpisu.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ghostreborn.wanpisu.R;
import in.ghostreborn.wanpisu.model.AnimeCharacter;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    ArrayList<AnimeCharacter> animeCharacters;

    public CharacterAdapter(ArrayList<AnimeCharacter> animeCharacters) {
        this.animeCharacters = animeCharacters;
    }

    @NonNull
    @Override
    public CharacterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.characters_list, parent, false);
        return new CharacterAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.ViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition();

        AnimeCharacter animeCharacter = animeCharacters.get(pos);

        holder.characterNameTextView.setText(animeCharacter.getName());
        holder.characterRoleTextView.setText(animeCharacter.getRole());
        Picasso.get().load(animeCharacter.getImage()).into(holder.characterImageView);
    }

    @Override
    public int getItemCount() {
        return animeCharacters.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView characterNameTextView;
        public TextView characterRoleTextView;
        public ImageView characterImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            characterNameTextView = itemView.findViewById(R.id.character_name_text);
            characterRoleTextView = itemView.findViewById(R.id.character_role_text);
            characterImageView = itemView.findViewById(R.id.character_image_view);
        }
    }

}