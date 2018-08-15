package com.example.mustafa.popularmovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.mustafa.popularmovies.Adabters.MoviesAdapter;
import com.example.mustafa.popularmovies.NetworkConnection.ConnectionTask;
import com.example.mustafa.popularmovies.DataItems.MovieItem;
import com.example.mustafa.popularmovies.RoomDatabase.MovieViewModel;
import com.example.mustafa.popularmovies.RoomDatabase.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.mustafa.popularmovies.BuildConfig.APIKey;


public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    public static ProgressBar mProgressBar;
    public static MoviesAdapter mMoviesAdapter;
    public static ArrayList<MovieItem> movieItems,storedMovieItems;

    //Api key is stored in gradle
    private String popular_URL = "https://api.themoviedb.org/3/movie/popular?api_key="+APIKey+"&language=en-US&page=1";
    private String top_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key="+APIKey+"&language=en-US&page=1";
    ConnectionTask connectionTask;
    private int state=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView) findViewById(R.id.mRecycleView);
        recyclerView.setHasFixedSize(true);
        mProgressBar=(ProgressBar) findViewById(R.id.mainprogressBar);
        movieItems= new ArrayList();
        storedMovieItems=new ArrayList();
        mMoviesAdapter=new MoviesAdapter(this,movieItems);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(mMoviesAdapter);

        final SharedPreferences preferences=getPreferences(Context.MODE_PRIVATE);

        MovieViewModel movieViewModel= ViewModelProviders.of(this, new ViewModelFactory(this.getApplication(),0)).get(MovieViewModel.class);
        movieViewModel.getmAllMovies().observe(this, new Observer<List<MovieItem>>() {
            @Override
            public void onChanged(@Nullable List<MovieItem> mMovieItems) {
                storedMovieItems= (ArrayList<MovieItem>) mMovieItems;
                mMoviesAdapter.changeSorting(storedMovieItems);
                if(preferences.getInt("state",0)==R.id.Favorite){
                    mMoviesAdapter.notifyDataSetChanged();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.most_popular:
                connectionTask=new ConnectionTask(getApplicationContext());
                if(isOnline()){
                    connectionTask.execute(popular_URL);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
                }
                state=R.id.most_popular;
                return true;
            case R.id.top_rated:
                connectionTask=new ConnectionTask(getApplicationContext());
                if(isOnline()){
                    connectionTask.execute(top_URL);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
                }
                state=R.id.top_rated;
                return true;

            case R.id.Favorite:
                state=R.id.Favorite;
                mProgressBar.setVisibility(View.VISIBLE);
                mMoviesAdapter.changeSorting(storedMovieItems);
                mMoviesAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.INVISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        SharedPreferences preferences=getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putInt("state",state).apply();




    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        SharedPreferences preferences=getPreferences(Context.MODE_PRIVATE);
        state = preferences.getInt("state",0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(state==R.id.most_popular||state==0){
            connectionTask=new ConnectionTask(getApplicationContext());
            if(isOnline()){
                connectionTask.execute(popular_URL);
            } else {
                Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
            }
        }else if (state==R.id.top_rated){

            connectionTask=new ConnectionTask(getApplicationContext());
            if(isOnline()){
                connectionTask.execute(top_URL);
            } else {
                Toast.makeText(getApplicationContext(), R.string.check_connection, Toast.LENGTH_SHORT).show();
            }

        }else if(state==R.id.Favorite){
            mProgressBar.setVisibility(View.VISIBLE);
            mMoviesAdapter.notifyDataSetChanged();
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    }


}
