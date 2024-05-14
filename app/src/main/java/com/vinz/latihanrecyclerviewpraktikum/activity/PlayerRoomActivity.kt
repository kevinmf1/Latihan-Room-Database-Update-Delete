package com.vinz.latihanrecyclerviewpraktikum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vinz.latihanrecyclerviewpraktikum.DetailPlayerActivity
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.adapter.PlayerAdapterRoom
import com.vinz.latihanrecyclerviewpraktikum.room.AppViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.PlayerDatabase
import com.vinz.latihanrecyclerviewpraktikum.room.RoomViewModelFactory

class PlayerRoomActivity : AppCompatActivity() {

    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var appViewModel: AppViewModel
    // Mendeklarasikan adapter untuk RecyclerView
    private lateinit var playerAdapterRoom: PlayerAdapterRoom
    // Mendeklarasikan RecyclerView untuk menampilkan daftar pemain
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_room)

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.rv_player_room)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati perubahan data pemain dan memperbarui RecyclerView
        appViewModel.getAllPlayer().observe(this) { playerData ->
            if (playerData != null) {
                playerAdapterRoom = PlayerAdapterRoom(playerData)
                recyclerView.adapter = playerAdapterRoom

                // Menangani aksi klik pada item di RecyclerView
                playerAdapterRoom.setOnItemClickCallback(object :
                    PlayerAdapterRoom.OnItemClickCallback {
                    override fun onItemClicked(data: PlayerDatabase) {
                        showSelectedPlayer(data)
                    }
                })
            }
        }

        // Menangani aksi klik pada tombol tambah pemain
        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add_player)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddPlayerRoomActivity::class.java)
            startActivity(intent)
        }
    }

    // Fungsi untuk menampilkan detail pemain yang dipilih
    private fun showSelectedPlayer(data: PlayerDatabase) {
        // Membuat intent untuk berpindah ke DetailPlayerActivity
        val navigateToDetail = Intent(this, DetailPlayerActivity::class.java)

        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
        navigateToDetail.putExtra("player", data)

        // Memulai activity baru
        startActivity(navigateToDetail)
    }

    // Fungsi yang dipanggil ketika activity di-restart
    override fun onRestart() {
        super.onRestart()

        // Memperbarui daftar pemain
        appViewModel.getAllPlayer()
    }

}