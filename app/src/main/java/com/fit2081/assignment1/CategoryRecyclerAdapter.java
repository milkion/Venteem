package com.fit2081.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CustomViewHolder>{

    ArrayList<EventCategory> data = new ArrayList<>();

    public void setCategory(ArrayList<EventCategory> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCategoryId.setText(data.get(position).getCategoryId());
        holder.tvCategoryName.setText(data.get(position).getCategoryName());
        holder.tvEventCount.setText(String.valueOf(data.get(position).getEventCount()));

        if (data.get(position).isActive()){
            holder.tvActive.setText("Active");
        } else {
            holder.tvActive.setText("Inactive");
        }
    }

    @Override
    public int getItemCount() {
        if (this.data != null){
            return this.data.size();
        }

        return 0;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvEventCount;
        public TextView tvActive;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tv_id);
            tvCategoryName = itemView.findViewById(R.id.tv_name);
            tvEventCount = itemView.findViewById(R.id.tv_eventCount);
            tvActive = itemView.findViewById(R.id.tv_active);

        }
    }

}
