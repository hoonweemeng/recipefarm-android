package com.app.recipefarm.recipedetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;

import java.util.ArrayList;
public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<String> itemList;
    private final ActionListener listener;

    // Interface for callbacks
    public interface ActionListener {
        void onSelect(int id);
    }

    public RecipeDetailAdapter(Context context, ArrayList<String> itemList, ActionListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_recipedetail_card, parent,false);
        return new RecipeDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapter.ViewHolder holder, int position) {
        String detail = itemList.get(position);

        // set textview text
        holder.tvTitle.setText((position + 1) + ".");
        holder.tvDetail.setText(detail);

        // callback when actionCard is clicked
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onSelect(position));
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDetail;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.recipeformListId);
            this.tvDetail = itemView.findViewById(R.id.recipeformListDetail);
            this.itemView = itemView;
        }
    }
}
