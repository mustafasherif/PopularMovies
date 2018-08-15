package com.example.mustafa.popularmovies.DataItems;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "movies")
public class MovieItem implements Parcelable{

    @PrimaryKey()
    @OnConflictStrategy
    private Integer id;
    private String image;
    private String overview;
    private String movie_title;
    private String release_date;
    private String rating;
    private Boolean liked;




    @Ignore
    public MovieItem(){
        super();
    }

    public MovieItem(int id,String image,String overview,String movie_title,String release_date, String rating,Boolean liked) {
        this.id=id;
        this.image=image;
        this.overview=overview;
        this.movie_title=movie_title;
        this.release_date=release_date;
        this.rating=rating;
        this.liked=liked;
    }


    protected MovieItem(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        image = in.readString();
        overview = in.readString();
        movie_title = in.readString();
        release_date = in.readString();
        rating = in.readString();
        byte tmpLiked = in.readByte();
        liked = tmpLiked == 0 ? null : tmpLiked == 1;
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getRating() {
        return rating;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(image);
        dest.writeString(overview);
        dest.writeString(movie_title);
        dest.writeString(release_date);
        dest.writeString(rating);
        dest.writeByte((byte) (liked == null ? 0 : liked ? 1 : 2));
    }
}