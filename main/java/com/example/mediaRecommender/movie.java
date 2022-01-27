package com.example.mediaRecommender;

import android.os.Parcel;
import android.os.Parcelable;

public class movie implements Parcelable {
    String title, poster, cast, crew, genres, description;

    public movie(String title, String poster, String cast, String crew, String genres, String description) {
        this.title = title;
        this.poster = poster;
        this.cast = cast;
        this.crew = crew;
        this.genres = genres;
        this.description = description;
    }

    protected movie(Parcel in) {
        title = in.readString();
        poster = in.readString();
        cast = in.readString();
        crew = in.readString();
        genres = in.readString();
        description = in.readString();
    }

    public static final Creator<movie> CREATOR = new Creator<movie>() {
        @Override
        public movie createFromParcel(Parcel in) {
            return new movie(in);
        }

        @Override
        public movie[] newArray(int size) {
            return new movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(cast);
        parcel.writeString(crew);
        parcel.writeString(genres);
        parcel.writeString(description);
    }
}
