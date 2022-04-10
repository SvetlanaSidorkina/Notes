package com.example.notes.domain;

public class Note {

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


}
