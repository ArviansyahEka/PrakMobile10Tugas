package com.example.prakmobile10tugas.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prakmobile10tugas.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var listView: ListView
    private lateinit var btnTambah: Button
    private lateinit var txtNoData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Terima data dari Intent
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val date = intent.getStringExtra("date")

        // Inisialisasi elemen UI
        listView = binding.listView
        btnTambah = binding.btnTambahListActivity
        txtNoData = binding.txtNoData

        // Set onClickListener untuk tombol "Tambah"
        btnTambah.setOnClickListener {
            // Intent untuk berpindah ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Ambil data dari sumber data Anda (misalnya, dari database atau repository)
        val data = loadData()

        // Tampilkan data jika ada, atau tampilkan pesan "Tidak ada data"
        if (data.isNotEmpty()) {
            displayData(data)
        } else {
            showNoDataMessage()
        }

        val note = "$title - $description - $date"
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListOf(note))
        binding.listView.adapter = adapter
    }


    private fun loadData(): List<String> {
        // Gantilah dengan logika pengambilan data sesuai kebutuhan Anda
        // Contoh: Mendapatkan data dari database atau sumber data lainnya
        return listOf("Data 1", "Data 2", "Data 3")
    }

    private fun displayData(data: List<String>) {
        // Gunakan ArrayAdapter atau adapter khusus untuk menghubungkan data ke ListView
        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1, data)
        listView.adapter = adapter

        // Sembunyikan teks "Tidak ada data"
        txtNoData.visibility = View.GONE
    }

    private fun showNoDataMessage() {
        // Tampilkan teks "Tidak ada data"
        txtNoData.visibility = View.VISIBLE
    }
}
