package com.gb.notes.ui

import android.app.Application
import com.gb.notes.domain.NotesRepository

class Application : Application() {
    val repository = NotesRepository()
}