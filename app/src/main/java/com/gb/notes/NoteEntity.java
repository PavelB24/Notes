package com.gb.notes;

public class NoteEntity {

    private int id;
    private String title;
    private String detail;

    public NoteEntity(int id, String title, String detail) {
        this.id = id;
        this.title=title;
        this.detail=detail;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
