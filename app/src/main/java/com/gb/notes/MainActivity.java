package com.gb.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements FragmentsCall {
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.container_for_fragment, new NoteList()).commit();

    }

    @Override
    public void callEditionFragment() {
        fragmentManager.beginTransaction().add(R.id.container_for_fragment, NoteEdit.getInstance()).addToBackStack(null).commit();
    }
}