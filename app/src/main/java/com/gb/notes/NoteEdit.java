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
    EditText titleEditText;
    EditText descriptionEditText;
    NoteEntity note;
    DatePicker datePicker;
    UUID uuid;
    Bundle data;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_edit_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyButton = view.findViewById(R.id.apply_button);
        titleEditText = view.findViewById(R.id.title_edittext);
        descriptionEditText = view.findViewById(R.id.description_edittext);
        datePicker = view.findViewById(R.id.date_picker_actions);
        toFillTheNote();
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuid = UUID.randomUUID();
                //Редактируем заметку ниже
                if (toCheckIfEdit() && !(titleEditText.getText().toString().isEmpty() && descriptionEditText.getText().toString().isEmpty())) {
                    note = new NoteEntity(note.getId(), titleEditText.getText().toString(), descriptionEditText.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data.putParcelable(NoteEntity.class.getCanonicalName(), note);
                    getParentFragmentManager().setFragmentResult(NoteEdit.class.getCanonicalName(), data);
                    getParentFragmentManager().popBackStackImmediate();

                    //Создаём новую заметку
                } else if (!titleEditText.getText().toString().isEmpty() && !descriptionEditText.getText().toString().isEmpty()) {
                    note = new NoteEntity(uuid.toString(), titleEditText.getText().toString(), descriptionEditText.getText().toString(), datePicker.getDayOfMonth(), datePicker.getMonth(), datePicker.getYear());
                    data = new Bundle();
                    data.putParcelable(NoteEntity.class.getCanonicalName(), note);
                    getParentFragmentManager().setFragmentResult(NoteEdit.class.getCanonicalName(), data);
                    getParentFragmentManager().popBackStackImmediate();
                } else {
                    Toast.makeText(getActivity(), R.string.warning_toast, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toFillTheNote() {
        if (toCheckIfEdit()) {
            note = data.getParcelable(NoteEntity.class.getCanonicalName());
            titleEditText.setText(note.getTitle());
            descriptionEditText.setText(note.getDetail());

        }
    }

    private boolean toCheckIfEdit() {
        //Если есть бандл с классом заметка
        if (!(getArguments()==null)) {
            if (data == null) {
                data = getArguments();
            }
            return true;
        } else {
            return false;
        }

    }

    public static NoteEdit getInstance(Bundle data) {
        NoteEdit noteEdit = new NoteEdit();
        noteEdit.setArguments(data);
        return noteEdit;
    }

}