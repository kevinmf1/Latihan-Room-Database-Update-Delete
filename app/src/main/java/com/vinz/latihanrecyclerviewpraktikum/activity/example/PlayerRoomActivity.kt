package com.vinz.latihanrecyclerviewpraktikum.activity.example

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinz.latihanrecyclerviewpraktikum.DetailPlayerActivity
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.activity.practice.PeopleRoomActivity
import com.vinz.latihanrecyclerviewpraktikum.adapter.PlayerAdapterRoom
import com.vinz.latihanrecyclerviewpraktikum.room.example.AppViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity
import com.vinz.latihanrecyclerviewpraktikum.room.example.RoomViewModelFactory

class PlayerRoomActivity : AppCompatActivity() {

    // Mendeklarasikan ViewModel untuk interaksi dengan database
    // ViewModel digunakan untuk menyimpan dan mengelola data yang terkait dengan UI.
    private lateinit var appViewModel: AppViewModel

    // Mendeklarasikan adapter untuk RecyclerView.
    // Adapter digunakan untuk mengatur bagaimana item dalam RecyclerView ditampilkan.
    private lateinit var playerAdapterRoom: PlayerAdapterRoom

    // Mendeklarasikan RecyclerView untuk menampilkan daftar pemain.
    // RecyclerView adalah komponen UI yang digunakan untuk menampilkan daftar item dalam format yang dapat digulir.
    private lateinit var recyclerView: RecyclerView

    // Fungsi onCreate dipanggil ketika activity dibuat.
    // Fungsi ini digunakan untuk melakukan inisialisasi awal untuk activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_room)

        // Mendapatkan instance ViewModel.
        // ViewModelProvider digunakan untuk membuat ViewModel.
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout.
        // findViewById digunakan untuk mendapatkan referensi ke komponen di layout berdasarkan ID-nya.
        recyclerView = findViewById(R.id.rv_player_room)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data pemain dan memperbarui RecyclerView.
        // LiveData digunakan untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
        appViewModel.getAllPlayer().observe(this) { playerData ->
            if (playerData != null) {
                playerAdapterRoom = PlayerAdapterRoom(playerData)
                recyclerView.adapter = playerAdapterRoom

                // Menangani aksi klik pada item di RecyclerView.
                playerAdapterRoom.setOnItemClickCallback(object :
                    PlayerAdapterRoom.OnItemClickCallback {
                    override fun onItemClicked(data: PlayerEntity) {
                        showSelectedPlayer(data)
                    }

                    override fun onMoreClicked(data: PlayerEntity, position: Int) {
                        PopUpFragment(data, position).show(supportFragmentManager, PopUpFragment.TAG)
                    }
                })
            }
        }

        onClick()
    }

    private fun onClick() {
        // Menangani aksi klik pada tombol tambah pemain.
        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add_player)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddPlayerRoomActivity::class.java)
            startActivity(intent)
        }

        // Menangani aksi klik pada tombol navigasi.
        val btnNavigate = findViewById<ImageView>(R.id.btn_practice)
        btnNavigate.setOnClickListener {
            val intent = Intent(this, PeopleRoomActivity::class.java)
            startActivity(intent)
        }
    }

    // Fungsi untuk menampilkan detail pemain yang dipilih.
    private fun showSelectedPlayer(data: PlayerEntity) {
        val navigateToDetail = Intent(this, DetailPlayerActivity::class.java)

        navigateToDetail.putExtra("player", data)

        startActivity(navigateToDetail)
    }

    // Fungsi onRestart dipanggil ketika activity dimulai kembali setelah berhenti.
    // Fungsi ini digunakan untuk melakukan pembaruan data atau UI yang diperlukan.
    override fun onRestart() {
        super.onRestart()

        appViewModel.getAllPlayer()
    }

    // Fungsi onResume dipanggil ketika activity mulai berinteraksi dengan pengguna.
    // Fungsi ini digunakan untuk melakukan pembaruan data atau UI yang diperlukan.
    override fun onResume() {
        super.onResume()

        appViewModel.getAllPlayer()
    }

}