package com.example.mustafa.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.popularmovies.NetworkConnection.ReviewsTask;
import com.example.mustafa.popularmovies.NetworkConnection.TrailersTask;
import com.example.mustafa.popularmovies.DataItems.MovieItem;
import com.example.mustafa.popularmovies.RoomDatabase.MovieViewModel;
import com.example.mustafa.popularmovies.RoomDatabase.ViewModelFactory;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    TextView movieName,movieDate,movieRate,movieOverview;
    ImageView moviePoster,mark;
    private MovieItem movieItem;
   public static RecyclerView trailerRecycle,reviewsRecycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieName=findViewById(R.id.movie_name);
        movieDate=findViewById(R.id.date);
        movieRate=findViewById(R.id.rate);
        movieOverview=findViewById(R.id.review);
        moviePoster=findViewById(R.id.image);

        Intent intent = getIntent();

        movieItem=intent.getParcelableExtra("movie");

        movieName.setText(movieItem.getMovie_title());
        Picasso.with(this).load(movieItem.getImage()).resize(300, 500).into(moviePoster);
        movieDate.setText(movieItem.getRelease_date());
        movieRate.setText(movieItem.getRating());
        movieOverview.setText(movieItem.getOverview());

        TrailersTask trailersTask=new TrailersTask(this);
        trailersTask.execute(movieItem.getId());

        ReviewsTask reviewsTask=new ReviewsTask(this);
        reviewsTask.execute(movieItem.getId());

        Toast.makeText(this,movieItem.getId()+"",Toast.LENGTH_SHORT);

        trailerRecycle=(RecyclerView) findViewById(R.id.trailers);
        reviewsRecycle=(RecyclerView) findViewById(R.id.reviews);

        mark=(ImageView) findViewById(R.id.likedButton);


        getMovieItem();





    }


    public void liked(View view) {
        if(movieItem.getLiked()==false){
            movieItem.setLiked(true);
            MovieViewModel movieViewModel= ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(),0)).get(MovieViewModel.class);
            movieViewModel.insert(movieItem);
            mark.setImageResource(R.drawable.ic_heart_red);
        }else {
            MovieViewModel movieViewModel= ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(),0)).get(MovieViewModel.class);
            movieViewModel.delete(movieItem);
            movieItem.setLiked(false);
            mark.setImageResource(R.drawable.ic_heart_white);
        }

    }

    public void getMovieItem() {

        MovieViewModel movieViewModel= ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(),movieItem.getId())).get(MovieViewModel.class);
        movieViewModel.getmMovie().observe(this, new Observer<MovieItem>() {
            @Override
            public void onChanged(@Nullable MovieItem movieItem) {
                if(movieItem!=null){
                    access(movieItem);
                }

                        }
        });

    }

    private void access(MovieItem movieItem){
        Toast.makeText(this,"enterde",Toast.LENGTH_SHORT);
        this.movieItem.setLiked(true);
        if(movieItem.getLiked()){
            mark.setImageResource(R.drawable.ic_heart_red);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
