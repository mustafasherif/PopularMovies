package com.example.mustafa.popularmovies.Adabters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mustafa.popularmovies.DataItems.ReviewItem;
import com.example.mustafa.popularmovies.R;

import java.util.ArrayList;

public class ReviewsAdabter extends RecyclerView.Adapter<ReviewsAdabter.ReviewsHolder> {

    private Context context;
    private ArrayList<ReviewItem>reviewItems;

    public ReviewsAdabter(Context mcontext, ArrayList<ReviewItem> reviewItems) {
        context=mcontext;
        this.reviewItems=reviewItems;
    }

    @NonNull
    @Override
    public ReviewsAdabter.ReviewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        ReviewsHolder holder=new ReviewsHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdabter.ReviewsHolder holder, int position) {
        ReviewItem reviewItem=reviewItems.get(position);
        holder.auther_name.setText(reviewItem.getAuthor());
        holder.content.setText(reviewItem.getContent());

    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder  {
        TextView auther_name,content;

        public ReviewsHolder(View itemView) {
            super(itemView);
            auther_name=(TextView)itemView.findViewById(R.id.auther_name);
            content=(TextView)itemView.findViewById(R.id.content);
        }


    }
}
