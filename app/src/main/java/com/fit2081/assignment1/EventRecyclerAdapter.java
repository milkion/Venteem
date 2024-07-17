package com.fit2081.assignment1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.CustomViewHolder>{

    ArrayList<Event> data = new ArrayList<>();

    public void setEvent(ArrayList<Event> data) {
        this.data = data;
    }

    @NonNull
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_layout, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_cat_id.setText(String.valueOf(data.get(position).getCategoryId()));
        holder.tv_event_name.setText(data.get(position).getEventName());
        holder.tv_event_id.setText(String.valueOf(data.get(position).getEventId()));
        holder.tv_tickets_available.setText(String.valueOf(data.get(position).getTicketsAvailable()));

        if (data.get(position).isActive()){
            holder.tv_e_active.setText("Active");
        } else {
            holder.tv_e_active.setText("Inactive");
        }

        holder.itemView.setOnClickListener(v ->{
            String eventName = data.get(position).getEventName();

            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("eventName", eventName);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (this.data != null){
            return this.data.size();
        }

        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_event_id;
        public TextView tv_event_name;
        public TextView tv_cat_id;
        public TextView tv_tickets_available;
        public TextView tv_e_active;

        public CustomViewHolder(@NonNull View itemView){
            super(itemView);
            tv_event_id = itemView.findViewById(R.id.tv_event_id);
            tv_event_name = itemView.findViewById(R.id.tv_event_name);
            tv_cat_id = itemView.findViewById(R.id.tv_cat_id);
            tv_tickets_available = itemView.findViewById(R.id.tv_tickets_available);
            tv_e_active = itemView.findViewById(R.id.tv_e_active);

        }
    }
}
