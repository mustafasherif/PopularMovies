package com.example.mustafa.popularmovies.NetworkConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.BuildConfig;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.example.mustafa.popularmovies.Adabters.TrailerAdabter;
import com.example.mustafa.popularmovies.MovieDetails;
import com.example.mustafa.popularmovies.DataItems.TrailerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class TrailersTask extends AsyncTask<Integer,Void,Boolean> {

    Context mcontext;
    public static ArrayList<TrailerItem> trailerItems;
    public static TrailerAdabter mTrailerAdabter;





    public TrailersTask(Context context){

        trailerItems=new ArrayList();
        mcontext=context;

    }



    @Override
    protected Boolean doInBackground(Integer... ints) {

        if (isOnline()) {
            HttpsURLConnection httpsURLConnection = null;
            BufferedReader reader = null;

            try {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("https")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(""+ints[0])
                        .appendPath("videos")
                        .appendQueryParameter("api_key", com.example.mustafa.popularmovies.BuildConfig.APIKey);
                String url_string = builder.build().toString();
                URL url = new URL(url_string);
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
        if (s) {

            mTrailerAdabter=new TrailerAdabter(mcontext,trailerItems);
            MovieDetails.trailerRecycle.setLayoutManager(new LinearLayoutManager(mcontext));
            MovieDetails.trailerRecycle.setAdapter(mTrailerAdabter);
        } else {
            Toast.makeText(mcontext, "check connection", Toast.LENGTH_SHORT);
        }


    }

    private void parseResult(String result) {
        TrailerItem trailerItem ;

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray TrailersJsonArray = jsonObject.optJSONArray("results");

            for (int i = 0; i < TrailersJsonArray.length(); i++) {
                trailerItem=new TrailerItem();
                JSONObject trailer = TrailersJsonArray.optJSONObject(i);
                trailerItem.setId(trailer.getString("key"));
                trailerItem.setName(trailer.getString("name"));
                trailerItems.add(trailerItem);
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