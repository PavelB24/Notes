package com.gb.notes.domain;

import androidx.recyclerview.widget.DiffUtil;


import java.util.List;

public class NotesDiffCallback extends DiffUtil.Callback {
   private List<NoteEntity> oldList;
   private List<NoteEntity> newList;
   private final String TAG = "@@@";

   public NotesDiffCallback(List<NoteEntity> oldList, List<NoteEntity> newList){
       this.oldList=oldList;
       this.newList=newList;
   }
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getId().equals(oldList.get(oldItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        NoteEntity newNote= newList.get(newItemPosition);
        NoteEntity oldNote= oldList.get(oldItemPosition);
        return (newNote.getTitle().equals(oldNote.getTitle()))&&(newNote.getDetail().equals(oldNote.getDetail()))&&(newNote.getDateAsString().equals(oldNote.getDateAsString()));
    }

    public void setNewList(List<NoteEntity> newList) {
        this.newList = newList;
    }

    public void setOldList(List<NoteEntity> oldList) {
        this.oldList = oldList;
    }
}
