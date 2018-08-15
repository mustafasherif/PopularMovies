package com.example.mustafa.popularmovies.Adabters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mustafa.popularmovies.MovieDetails;
import com.example.mustafa.popularmovies.DataItems.MovieItem;
import com.example.mustafa.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {


    private Context mContext;
    private ArrayList<MovieItem> mMovieItems ;


    public MoviesAdapter(Context context,ArrayList<MovieItem> movieItems) {

        mContext=context;
        mMovieItems=movieItems;

    }


        @NonNull
        @Override
        public MoviesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        MoviesHolder holder=new MoviesHolder(row);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MoviesHolder holder, int position) {
        MovieItem movieItem =mMovieItems.get(position);
        Picasso.with(mContext).load(movieItem.getImage()).into(holder.poster);
        }

        @Override
        public int getItemCount() {
            return this.mMovieItems.size();
        }

        class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView poster;

            public MoviesHolder(View itemView) {
                super(itemView);
                poster = (ImageView) itemView.findViewById(R.id.movie_poster);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
               MovieItem item=mMovieItems.get(getAdapterPosition());
                Intent intent=new Intent(mContext,MovieDetails.class);

                intent.putExtra("movie",item);

                mContext.startActivity(intent);

            }


        }

    public void changeSorting(ArrayList<MovieItem> movieItems) {
        mMovieItems = movieItems;

    }


    }

