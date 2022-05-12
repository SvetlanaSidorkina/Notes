package com.example.notes.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Note implements Parcelable {

    public int id;
    private final String title;
    private String detail;
    private Date createdAt;

    public Note(String title, String detail, Date createdAt) {
        this.title = title;
        this.detail = detail;
        this.createdAt = createdAt;
    }

    public Note(String title, Date createdAt) {
        this.title = title;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer setId(int id) {
            return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(title, note.title) && Objects.equals(detail, note.detail) && Objects.equals(createdAt, note.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, detail, createdAt);
    }
}
