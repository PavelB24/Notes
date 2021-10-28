package com.gb.notes.ui
import androidx.appcompat.app.AppCompatActivity
import com.gb.notes.domain.FragmentsCall
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.os.Bundle
import com.gb.notes.R
import com.gb.notes.domain.NotesRepository
import com.google.android.material.navigation.NavigationBarView

import kotlin.Throws
import com.squareup.moshi.Moshi
import com.gb.notes.domain.NoteEntity

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.ArrayList

class MainActivity : AppCompatActivity(), FragmentsCall {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var bottomNavigationItemView: BottomNavigationView
    private  val  LOCAL_REPOSITORY_NAME = "repo.bin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        setNavigation()
        if (repository.allNotes.isEmpty()) {
            try {
                toInitNotesInRepository()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            }
        }
        fragmentManager = supportFragmentManager
        bottomNavigationItemView.selectedItemId = R.id.notes_item_menu
        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.popBackStack()
        }
    }

    private fun setNavigation() {
        bottomNavigationItemView = findViewById(R.id.navigation_bar)
        val savedData = Bundle()
        savedData.putParcelable(NotesRepository::class.java.canonicalName, repository)
        bottomNavigationItemView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.notes_item_menu -> {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction().replace(
                            R.id.container_for_fragment_land_1,
                            NoteListFragment.getInstance(savedData)
                        ).commit()
                    } else {
                        fragmentManager.beginTransaction().replace(
                            R.id.container_for_fragment,
                            NoteListFragment.getInstance(savedData)
                        ).commit()
                    }
                    fragmentManager.popBackStack()
                }
                R.id.data_manager_item_menu -> {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction().replace(
                            R.id.container_for_fragment_land_1,
                            DataManagerFragment.getInstance(savedData)
                        ).commit()
                    } else {
                        fragmentManager.beginTransaction().replace(
                            R.id.container_for_fragment,
                            DataManagerFragment.getInstance(savedData)
                        ).commit()
                    }
                    fragmentManager.popBackStack()
                }
                R.id.profile_item_menu -> {
                    if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        fragmentManager.beginTransaction()
                            .replace(R.id.container_for_fragment_land_1, ProfileFragment()).commit()
                    } else {
                        fragmentManager.beginTransaction()
                            .replace(R.id.container_for_fragment, ProfileFragment()).commit()
                    }
                    fragmentManager.popBackStack()
                }
            }
            true
        })
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun toInitNotesInRepository() {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(NoteEntity::class.java)
        val fileInputStream = openFileInput(LOCAL_REPOSITORY_NAME)
        val objectInputStream = ObjectInputStream(fileInputStream)
        val size = objectInputStream.readInt()
        val list: MutableList<NoteEntity?> = ArrayList()
        for (i in 0 until size) {
            val json: String = objectInputStream.readObject() as String
            list.add(jsonAdapter.fromJson(json))
            Log.d("@@@", list.toString())
        }
        repository.addAll(list)
        Log.d("@@@", "size " + repository.allNotes.size)
        objectInputStream.close()
        fileInputStream.close()
        Log.d("@@@", "Восстановлен")
    }

    override fun callEditionFragment(data: Bundle?) {
        fragmentManager.popBackStack()
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction()
                .add(R.id.container_for_fragment_land_2, NoteEditFragment.getInstance(data))
                .addToBackStack(null).commit()
        } else {
            fragmentManager.beginTransaction()
                .add(R.id.container_for_fragment, NoteEditFragment.getInstance(data))
                .addToBackStack(null).commit()
        }
    }

    override fun callSettingsFragment() {
        //TODO вписать этот вызов в настройки профиля
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction()
                .replace(R.id.container_for_fragment_land_2, SettingsFragment())
                .addToBackStack(null).commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.container_for_fragment, SettingsFragment()).addToBackStack(null)
                .commit()
        }
    }

    override fun callNoteViewFragment(data: Bundle) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction()
                .add(R.id.container_for_fragment_land_2, NoteViewFragment.getInstance(data))
                .addToBackStack(null).commit()
        } else {
            fragmentManager.beginTransaction()
                .add(R.id.container_for_fragment, NoteViewFragment.getInstance(data))
                .addToBackStack(null).commit()
        }
    }

    @Throws(IOException::class)
    private fun serializeNotes() {
        val fos = openFileOutput(LOCAL_REPOSITORY_NAME, MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fos)
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(NoteEntity::class.java)
        objectOutputStream.writeInt(repository.allNotes.size)
        var json: String? = null
        for (note in repository.allNotes) {
            json = jsonAdapter.toJson(note)
            objectOutputStream.writeObject(json)
        }
        objectOutputStream.close()
        fos.close()
        Log.d("@@@", "Записан")
    }

    private val repository: NotesRepository
        get() = (application as Application).repository

    override fun onStop() {
        try {
            serializeNotes()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        super.onStop()
    }

    override fun onDestroy() {
        Toast.makeText(this, "До свидания!", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}