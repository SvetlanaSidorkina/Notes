package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private final String title;
    private final String detail;

    public Note(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(detail);
    }

    protected Note(Parcel in) {
        title = in.readString();
        detail = in.readString();
    }
    
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
