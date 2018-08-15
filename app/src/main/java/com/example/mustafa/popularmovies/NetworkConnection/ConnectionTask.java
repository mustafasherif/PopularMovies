package com.example.mustafa.popularmovies.NetworkConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.mustafa.popularmovies.MainActivity;
import com.example.mustafa.popularmovies.DataItems.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;


import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ConnectionTask extends AsyncTask<String,Void,Boolean> {

    Context mcontext;


    public ConnectionTask(Context context){

        mcontext=context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        MainActivity.mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        if (isOnline()) {
            HttpsURLConnection httpsURLConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.connect();

                InputStream stream = httpsURLConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer stringBuffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {

                    stringBuffer.append(line);

                }

                parseResult(stringBuffer.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (httpsURLConnection != null) {
                    httpsURLConnection.disconnect();
                }

                try {
                    if (reader != null) {
                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return true;
        } else {
            return false;
        }


    }


    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        MainActivity.mProgressBar.setVisibility(View.INVISIBLE);
        if (s) {
            MainActivity.mMoviesAdapter.changeSorting(MainActivity.movieItems);
            MainActivity.mMoviesAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(mcontext, "check connection", Toast.LENGTH_SHORT);
        }


    }

    private void parseResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray posters = jsonObject.optJSONArray("results");
            MovieItem item;
            MainActivity.movieItems.clear();
            for (int i = 0; i < posters.length(); i++) {
                JSONObject poster = posters.optJSONObject(i);
                item = new MovieItem();
                item.setId(poster.getInt("id"));
                item.setImage("http://image.tmdb.org/t/p/w185/" + poster.getString("poster_path"));
                item.setOverview(poster.getString("overview"));
                item.setMovie_title(poster.getString("title"));
                item.setRelease_date(poster.getString("release_date"));
                item.setRating(poster.getString("vote_average"));
                item.setLiked(false);
                MainActivity.movieItems.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mcontext.getSystemService(CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}