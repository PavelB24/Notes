package com.gb.notes.domain;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gb.notes.domain.Interfaces.OnNoteClickListener;
import com.gb.notes.R;

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


    //Передаём в класс дату из репозитория, чтобы работать с ней далее
    public void setData(List<NoteEntity> dataFromRepo) {
        Log.d(TAG, dataFromRepo.toString() + 2);
        Log.d(TAG, data.toString() + 3);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new NotesDiffCallback(data, dataFromRepo), true);
        data = dataFromRepo;
        result.dispatchUpdatesTo(this);


    }


    @Override
    //А этот метод переносит данные из объекта в поля холдера (поля в данном случае 2 текствьюшки из основной вьюшки холдера)
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteEntity note = getNote(position);
        setTextInHolderItems(holder, note);
        setHolderItemsListeners(holder, note);
    }

    private void setHolderItemsListeners(NoteViewHolder holder, NoteEntity note) {
        //Ставим обработку для каждого холдера, вызывая кликлисенер вьюшки-> передаём метод из интерфейса listener, а в метод кладём заметку note, созданную выше в методе
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onNoteClick(note);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO
                listener.onNoteLongClick(note, view);
                return true;
            }
        });
    }

    private void setTextInHolderItems(NoteViewHolder holder, NoteEntity note) {
        holder.titleTextView.setText(note.getTitle());
        holder.descriptionTextView.setText(note.getDetail());
    }

    public NoteEntity getNote(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //Мы принимаем интерфейс из активити(фрагмента) и ставим его в поля класса для дальнейшей работы
    public void setOnItemClickListener(OnNoteClickListener listener) {
        this.listener = listener;

    }


}

