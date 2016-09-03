package com.github.ppartisan.popularmoviesi;

import android.os.Parcel;
import android.os.Parcelable;

public final class MovieModel implements Parcelable {

    public final String title, releaseDate, imageUrl, synopsis;
    public final double averageVote;

    private MovieModel(String title, String releaseDate, String imageUrl, double averageVote, String synopsis) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.imageUrl = imageUrl;
        this.averageVote = averageVote;
        this.synopsis = synopsis;
    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        releaseDate = in.readString();
        imageUrl = in.readString();
        synopsis = in.readString();
        averageVote = in.readDouble();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @Override
    public String toString() {
        return "Title: " + title +
                "\nRelease Date: " + releaseDate +
                "\nImage Url: " + imageUrl +
                "\nAve. Vote: " + averageVote +
                "\nSynopsis: " + synopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(imageUrl);
        parcel.writeString(synopsis);
        parcel.writeDouble(averageVote);
    }

    public final static class Builder {

        private String title, releaseDate, imageUrl, synopsis;
        private double averageVote;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder releaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder synopsis(String synopsis) {
            this.synopsis = synopsis;
            return this;
        }

        public Builder averageVote(double averageVote) {
            this.averageVote = averageVote;
            return this;
        }

        public MovieModel build() {
            return new MovieModel(title, releaseDate, imageUrl, averageVote, synopsis);
        }

    }

}
