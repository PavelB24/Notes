package com.gb.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

public class NoteEntity implements Parcelable, Serializable {

    private String id;
    private String title;
    private String description;
    private int originDay;
    private int originMonth;
    private int originYear;
    private UUID uuid;

    public NoteEntity(String id, String title, String detail, int originDay, int originMonth, int originYear) {
        this.id = id;
        this.title = title;
        this.description = detail;
        this.originDay = originDay;
        this.originMonth = originMonth + 1;
        this.originYear = originYear;
    }
    public int getOriginDay() {
        return originDay;
    }

    public void setOriginDay(int originDay) {
        this.originDay = originDay;
    }

    public int getOriginMonth() {
        return originMonth;
    }

    public void setOriginMonth(int originMonth) {
        this.originMonth = originMonth;
    }

    public int getOriginYear() {
        return originYear;
    }

    public void setOriginYear(int originYear) {
        this.originYear = originYear;
    }
    private NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return description;
    }

    public void setDetail(String detail) {
        this.description = detail;
    }

    public String getDateAsString(){
        return originDay+"."+originMonth+"."+originYear;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
    }


    public static final Parcelable.Creator<NoteEntity> CREATOR = new Parcelable.Creator<NoteEntity>() {

        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };
}
