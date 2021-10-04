package com.gb.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;

public class NoteEdit extends Fragment {
    Button applyButton;
    EditText title;
    EditText description;
    NoteEntity note;
    DatePicker datePicker;
    UUID uuid;
    Bundle data;
    private final String BUNDLE_KEY = NoteEdit.class.getCanonicalName();
    private final String KEY_FROM_EDITOR = NoteEntity.class.getCanonicalName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_edit_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyButton = view.findViewById(R.id.apply_button);
        title = view.findViewById(R.id.title_edittext);
        description = view.findViewById(R.id.description_edittext);
        datePicker = view.findViewById(R.id.date_picker_actions);
        toFillTheNote();
    applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuid = UUID.randomUUID();
                //Редактируем заметку ниже
                if (toCheckIfEdit() && !(title.getText().toString().isEmpty() && description.getText().toString().isEmpty())) {
                    note = new NoteEntity(note.getId(), title.getText().toString(), description.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data.putParcelable(BUNDLE_KEY, data);
                    getParentFragmentManager().setFragmentResult(KEY_FROM_EDITOR , data);
                    getParentFragmentManager().popBackStackImmediate();

                    //Создаём новую заметку
                } else if (!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()) {
                    note = new NoteEntity(uuid.toString(), title.getText().toString(), description.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data= new Bundle();
                    data.putParcelable(BUNDLE_KEY, data);
                    getParentFragmentManager().setFragmentResult(KEY_FROM_EDITOR , data);
                    getParentFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.warning_toast, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toFillTheNote() {
        if (toCheckIfEdit()) {
            title.setText(note.getTitle());
            description.setText(note.getDetail());
        }
    }

    private boolean toCheckIfEdit() {
        //Если есть бандл с классом заметка
        if (!getArguments().isEmpty()) {
            if (note == null) {
                data= getArguments();
                note = data.getParcelable(NoteEntity.class.getCanonicalName());
            }
            return true;
        } else {
            return false;
        }

    }

    public static NoteEdit getInstance(){
        NoteEdit noteEdit= new NoteEdit();
        noteEdit.getParentFragmentManager().setFragmentResultListener(NoteList.class.getCanonicalName(), noteEdit.getActivity(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if(!result.isEmpty()){
                noteEdit.setArguments(result);}
            }
        });
        return noteEdit;
    }

}