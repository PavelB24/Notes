package com.gb.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

public class NoteEntity implements Parcelable {
    static Random random = new Random();

    private int id;
    private String title;
    private String description;


    public NoteEntity(int id, String title, String detail) {
        this.id = id;
        this.title=title;
        this.description=detail;
    }
    private NoteEntity(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    public void setId(int id){
        this.id=id;
    }
    public int toGenerateId(){
        id=random.nextInt();
        setId(id);
        return id;
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
        return description;
    }

    public void setDetail(String detail) {
        this.description = detail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
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
