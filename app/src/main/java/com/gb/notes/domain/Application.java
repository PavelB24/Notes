package com.gb.notes.domain;

public class Application extends android.app.Application {
    private final NotesRepository repository = new NotesRepository();

    public NotesRepository getRepository() {
        return repository;
    }
}
