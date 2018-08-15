package com.example.mustafa.popularmovies.Adabters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mustafa.popularmovies.R;
import com.example.mustafa.popularmovies.DataItems.TrailerItem;

import java.util.ArrayList;

public class TrailerAdabter extends RecyclerView.Adapter<TrailerAdabter.TrailerHolder> {

    private Context context;
    private ArrayList<TrailerItem>trailerItems;

    public TrailerAdabter(Context context,ArrayList<TrailerItem>trailerItems){
        this.context=context;
        this.trailerItems=trailerItems;
    }

    @NonNull
    @Override
    public TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        TrailerHolder holder=new TrailerHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerHolder holder, int position) {
        TrailerItem trailerItem=trailerItems.get(position);
        holder.textView.setText(trailerItem.getName());
    }

    @Override
    public int getItemCount() {
        return trailerItems.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public TrailerHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.trailer_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TrailerItem trailerItem=trailerItems.get(getAdapterPosition());
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerItem.getId()));

            context.startActivity(appIntent);

        }

    }
}
