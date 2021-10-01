package com.gb.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class NoteEditActivity extends AppCompatActivity {
    Button applyButton;
    EditText title;
    EditText description;
    NoteEntity note;
    Random random = new  Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        applyButton = findViewById(R.id.apply_button);
        title = findViewById(R.id.title_edittext);
        description = findViewById(R.id.description_edittext);
        toFillTheNote();

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                //Редактируем заметку ниже
                if (toCheckIfEdit() && !(title.getText().toString().isEmpty() && description.getText().toString().isEmpty())) {
                    note= new NoteEntity(note.getId(), title.getText().toString(), description.getText().toString());
                    data.putExtra(NoteEntity.class.getCanonicalName(), note);
                    setResult(Activity.RESULT_OK, data);
                    NoteEditActivity.this.finish();
                    //Создаём новую заметку
                } else if(!title.getText().toString().isEmpty() && !description.getText().toString().isEmpty()){
                    note= new NoteEntity(random.nextInt(), title.getText().toString(), description.getText().toString());
                    data.putExtra(NoteEntity.class.getCanonicalName(), note);
                    setResult(Activity.RESULT_OK, data);
                    NoteEditActivity.this.finish();
                } else {
                   Toast.makeText(NoteEditActivity.this, R.string.warning_toast, Toast.LENGTH_SHORT).show();
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
        //Если есть экстра с классом заметки
        if (getIntent().hasExtra(NoteEntity.class.getCanonicalName())) {
            if (note == null) {
                note = getIntent().getParcelableExtra(NoteEntity.class.getCanonicalName());
            }
            return true;
        } else {
            return false;
        }

    }
}