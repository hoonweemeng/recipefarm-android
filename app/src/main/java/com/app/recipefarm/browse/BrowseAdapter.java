package com.app.recipefarm.browse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;
import com.app.recipefarm.models.base.Recipe;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class BrowseAdapter extends RecyclerView.Adapter<BrowseAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Recipe> recipeList;
    private OnSelectRecipeListener onSelectRecipeListener;

    // Interface for callbacks
    public interface OnSelectRecipeListener {
        void onSelect(String recipeId);
    }

    public BrowseAdapter(Context context, ArrayList<Recipe> recipeList, OnSelectRecipeListener onSelectRecipeListener) {
        this.context = context;
        this.recipeList = recipeList;
        this.onSelectRecipeListener = onSelectRecipeListener;
    }

    @NonNull
    @Override
    public BrowseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_recipe_card, parent,false);
        return new BrowseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        // set textview text
        holder.tvTitle.setText(recipe.title);
        holder.tvDuration.setText(recipe.getDuration());

        // get image from firebase and set it to imageview
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(recipe.getImagePath());
        Glide.with(context)
                .load(storageReference)
                .into(holder.ivImage);

        // callback when recipeCard is clicked
        holder.itemView.setOnClickListener(v -> onSelectRecipeListener.onSelect(recipe.recipeId));

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDuration;
        TextView tvTitle;
        ImageView ivImage;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivImage = itemView.findViewById(R.id.recipeCardImage);
            this.tvTitle = itemView.findViewById(R.id.recipeCardTitle);
            this.tvDuration = itemView.findViewById(R.id.recipeCardDuration);
            this.itemView = itemView;
        }
    }
}
