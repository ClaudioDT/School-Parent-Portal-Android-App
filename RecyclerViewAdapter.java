package com.example.engineer.parent_portal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Engineer on 29/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;

    List<MyNotificationAdapter> myNotificationAdapter;
    public RecyclerViewAdapter(List<MyNotificationAdapter> myNotificationAdapter, Context context){
        super();
        this.myNotificationAdapter = myNotificationAdapter;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyNotificationAdapter myNotificationAdapter1 =  myNotificationAdapter.get(position);


        holder.txtName.setText(myNotificationAdapter1.getNotify_name());
        holder.txtDate.setText(myNotificationAdapter1.getNotify_date());
        holder.txtDescription.setText(myNotificationAdapter1.getNotify_description());
    }

    @Override
    public int getItemCount() {
        return myNotificationAdapter.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtName;
        public TextView txtDate;
        public TextView txtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName        = (TextView) itemView.findViewById(R.id.textView4);
            txtDate        = (TextView) itemView.findViewById(R.id.textView6);
            txtDescription = (TextView) itemView.findViewById(R.id.textView8);
        }
    }
}
