package com.vinz.latihanrecyclerviewpraktikum.activity.practice

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinz.latihanrecyclerviewpraktikum.DetailPlayerActivity
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.activity.example.AddPlayerRoomActivity
import com.vinz.latihanrecyclerviewpraktikum.activity.example.PopUpFragment
import com.vinz.latihanrecyclerviewpraktikum.activity.example.UpdatePlayerRoomActivity
import com.vinz.latihanrecyclerviewpraktikum.adapter.PeopleAdapterRoom
import com.vinz.latihanrecyclerviewpraktikum.adapter.PlayerAdapterRoom
import com.vinz.latihanrecyclerviewpraktikum.room.example.AppViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity
import com.vinz.latihanrecyclerviewpraktikum.room.example.RoomViewModelFactory
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleEntity
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleViewModelFactory


class PeopleRoomActivity : AppCompatActivity() {

    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var peopleViewModel: PeopleViewModel

    // Mendeklarasikan adapter untuk RecyclerView.
    // Adapter digunakan untuk mengatur bagaimana item dalam RecyclerView ditampilkan.
    private lateinit var peopleAdapterRoom: PeopleAdapterRoom

    // Mendeklarasikan RecyclerView untuk menampilkan daftar orang.
    // RecyclerView adalah komponen UI yang digunakan untuk menampilkan daftar item dalam format yang dapat digulir.
    private lateinit var recyclerView: RecyclerView

    // Fungsi onCreate dipanggil ketika activity dibuat.
    // Fungsi ini digunakan untuk melakukan inisialisasi awal untuk activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_people_room)

        // Mendapatkan instance ViewModel.
        // ViewModelProvider digunakan untuk membuat ViewModel.
        val factory = PeopleViewModelFactory.getInstance(this)
        peopleViewModel = ViewModelProvider(this, factory)[PeopleViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout.
        // findViewById digunakan untuk mendapatkan referensi ke komponen di layout berdasarkan ID-nya.
        recyclerView = findViewById(R.id.rv_people_room)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data orang dan memperbarui RecyclerView.
        // LiveData digunakan untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
        peopleViewModel.getAllPeople().observe(this) { peopleData ->
            if (peopleData != null) {
                peopleAdapterRoom = PeopleAdapterRoom(peopleData)
                recyclerView.adapter = peopleAdapterRoom

                // Menangani aksi klik pada item di RecyclerView.
                peopleAdapterRoom.setOnItemClickCallback(object : PeopleAdapterRoom.OnItemClickCallback {
                    override fun onItemClicked(data: PeopleEntity) {
                        showSelectedPeople(data)
                    }

                    override fun onItemMore(data: PeopleEntity) {
                        PopUpPracticeFragment(data).show(supportFragmentManager, PopUpPracticeFragment.TAG)
                    }
                })
            }
        }

        // Menangani aksi klik pada tombol tambah orang.
        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add_people)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddPeopleRoomActivity::class.java)
            startActivity(intent)
        }

        // Menangani aksi klik pada tombol kembali.
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Fungsi untuk menampilkan detail orang yang dipilih.
    private fun showSelectedPeople(data: PeopleEntity) {
        // Membuat intent untuk berpindah ke DetailPeopleActivity.
        val navigateToDetail = Intent(this, DetailPeopleActivity::class.java)

        // Menambahkan dan membawa data orang ke intent dengan tujuan ke DetailPeopleActivity.
        navigateToDetail.putExtra("people", data)

        // Memulai activity baru.
        startActivity(navigateToDetail)
    }

    // Fungsi onRestart dipanggil ketika activity dimulai kembali setelah berhenti.
    // Fungsi ini digunakan untuk melakukan pembaruan data atau UI yang diperlukan.
    override fun onRestart() {
        super.onRestart()

        // Memperbarui daftar orang.
        peopleViewModel.getAllPeople()
    }
}