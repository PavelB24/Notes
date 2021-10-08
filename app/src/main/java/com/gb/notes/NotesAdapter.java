package com.gb.notes;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    List<NoteEntity> data = new ArrayList<>();
    OnNoteClickListener listener;
    private final String TAG = "@@@";

    @NonNull
    @Override
    //Метод нужен чтобы создать вьюшку, раздув её разметку, создать объект холдера и передать туда эту вьюшку
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    //Передаём в класс дату из репозитория, чтобы работать с ней далее
    public void setData(List<NoteEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    //А этот метод переносит данные из объекта в поля холдера (поля в данном случае 2 текствьюшки из основной вьюшки холдера)
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = getNote(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDetail());
        //Ставим обработку для каждого холдера, вызывая кликлисенер вьюшки-> передаём метод из интерфейса listener, а в метод кладём заметку note, созданную выше в методе
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickEdit(note);
            }
        });
        holder.deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickDelete(note);
            }
        });

    }

    public NoteEntity getNote(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //Мы принимаем интерфейс из активити и ставим его в поля класса для дальнейшей работы
    public void setOnItemClickListener(OnNoteClickListener listener) {
        this.listener = listener;

    }

    public interface OnNoteClickListener {
        void onClickEdit(NoteEntity note);

        void onClickDelete(NoteEntity note);
    }

    public interface OnDeleteNoteButtonClickListener {
        void onClickDelete(NoteEntity note);
    }

}

