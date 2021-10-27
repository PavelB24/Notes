package com.gb.notes.ui;


import com.gb.notes.domain.NotesRepository;

public class Application extends android.app.Application {
    private final NotesRepository repository = new NotesRepository();


    public NotesRepository getRepository() {
        return repository;
    }
}
