package com.app.recipefarm.action;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.recipefarm.R;
import com.app.recipefarm.action.viewmodels.ActionViewModel;

import java.util.ArrayList;


public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ActionViewModel> actionList;
    private ActionListener listener;

    public ActionAdapter(Context context, ArrayList<ActionViewModel> actionList, ActionListener listener) {
        this.context = context;
        this.actionList = actionList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_action_card, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionViewModel action = actionList.get(position);

        // set textview text
        holder.tvTitle.setText(action.title);

        // callback when actionCard is clicked
        holder.itemView.setOnClickListener(v -> listener.onSelect(action.actionId));
    }

    @Override
    public int getItemCount() {
        return actionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        View itemView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvTitle = itemView.findViewById(R.id.actionTitle);
            this.itemView = itemView;
        }
    }
}
