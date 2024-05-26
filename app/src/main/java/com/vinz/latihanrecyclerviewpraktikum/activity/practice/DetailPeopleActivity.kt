package com.vinz.latihanrecyclerviewpraktikum.activity.practice

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleEntity

// Kelas DetailPeopleActivity adalah kelas yang mewarisi AppCompatActivity.
// Kelas ini digunakan untuk menampilkan detail dari orang yang dipilih.
class DetailPeopleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur layout untuk activity ini
        setContentView(R.layout.activity_detail_people)

        // Mengambil data orang dari intent yang dikirimkan dari PeopleRoomActivity
        val getPeopleData = intent.getParcelableExtra<PeopleEntity>("people")!!

        // Menghubungkan variabel dengan komponen di layout
        val peopleName = findViewById<TextView>(R.id.people_name)
        val playerImage = findViewById<ImageView>(R.id.people_image)

        // Mengubah file gambar menjadi URI
        val uri = Uri.fromFile(getPeopleData.image)

        // Menampilkan data orang ke dalam komponen di layout
        peopleName.text = getPeopleData.name
        playerImage.setImageURI(uri)

        // Menghubungkan tombol kembali dengan fungsi onBackPressed
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}