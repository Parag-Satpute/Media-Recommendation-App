package com.example.mediaRecommender;

import android.os.Parcel;
import android.os.Parcelable;

public class book implements Parcelable {
    String title, poster, author, description, publisher, publishedDate;

    public book(String title, String poster, String author, String description, String publisher, String publishedDate) {
        this.title = title;
        this.poster = poster;
        this.author = author;
        this.description = description;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
    }

    protected book(Parcel in) {
        title = in.readString();
        poster = in.readString();
        author = in.readString();
        description = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
    }

    public static final Creator<book> CREATOR = new Creator<book>() {
        @Override
        public book createFromParcel(Parcel in) {
            return new book(in);
        }

        @Override
        public book[] newArray(int size) {
            return new book[size];
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
        parcel.writeString(author);
        parcel.writeString(description);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
    }
}
