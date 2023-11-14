package com.example.prakmobile10tugas.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.prakmobile10tugas.databases.Note
import com.example.prakmobile10tugas.databases.NoteDao
import com.example.prakmobile10tugas.databases.NoteRoomDatabase
import com.example.prakmobile10tugas.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var mNotesDao: NoteDao
    private lateinit var executorService: ExecutorService
    private var updateId: Int = 0
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        with(binding) {
            btnAdd.setOnClickListener {
                insert(
                    Note(
                        title = txtTitle.text.toString(),
                        description = txtDesc.text.toString(),
                        date = txtDate.text.toString()
                    )
                )
                setEmptyField()
            }

//            btnUpdate.setOnClickListener {
//                update(
//                    Note(
//                        id = updateId,
//                        title = txtTitle.text.toString(),
//                        description = txtDesc.text.toString(),
//                        date = txtDate.text.toString()
//                    )
//                )
//                updateId = 0
//                setEmptyField()
//            }

            // Tombol "Lihat List"
            btnAdd.setOnClickListener {
                // Intent untuk berpindah ke com.example.prakmobile10tugas.ui.ListActivity
                val intent = Intent(this@MainActivity, ListActivity::class.java)

                // Menambahkan data ke intent
                intent.putExtra("title", txtTitle.text.toString())
                intent.putExtra("description", txtDesc.text.toString())
                intent.putExtra("date", txtDate.text.toString())

                // Memulai com.example.prakmobile10tugas.ui.ListActivity dengan intent
                startActivity(intent)
            }

            getAllNotes()
        }
    }

    private fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    private fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }

    private fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }

    private fun getAllNotes() {
        mNotesDao.getAllNotes().observe(this, Observer { notes ->
            // Handle the data as needed, or simply observe the changes
        })
    }

    override fun onResume() {
        super.onResume()
        getAllNotes()
    }

    private fun setEmptyField() {
        with(binding) {
            txtTitle.setText("")
            txtDesc.setText("")
            txtDate.setText("")
        }
    }
}
