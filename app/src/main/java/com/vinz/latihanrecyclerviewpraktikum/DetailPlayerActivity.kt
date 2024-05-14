package com.vinz.latihanrecyclerviewpraktikum

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.vinz.latihanrecyclerviewpraktikum.room.PlayerDatabase

class DetailPlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        // Mengambil data nama, deskripsi, dan gambar dari intent
        val getDataName = intent.getStringExtra("name")
        val getDataDescription = intent.getStringExtra("description")
        val getDataImage = intent.getIntExtra("image", 0)

        // Mengambil data dari intent yang terdapat di dalam activity Room
        val getDataPlayer = intent.getParcelableExtra<PlayerDatabase>("player")

        // Menghubungkan variabel dengan komponen di layout
        val playerName = findViewById<MaterialTextView>(R.id.player_name)
        val playerDescription = findViewById<MaterialTextView>(R.id.player_description)
        val playerImage = findViewById<ShapeableImageView>(R.id.player_image)

        // Menampilkan data pemain
        when {

            // Jika getDataPlayer tidak null, maka akan menampilkan data yang berasal dari getDataPlayer (activity Room)
            getDataPlayer != null -> {
                playerName.text = getDataPlayer.name
                playerDescription.text = getDataPlayer.description
                Glide.with(playerImage)
                    .load(getDataPlayer.image)
                    .into(playerImage)
            }

            // Jika semua kondisi null, maka akan menampilkan data yang berasal dari MainActivity
            else -> {
                playerName.text = getDataName
                playerDescription.text = getDataDescription
                playerImage.setImageResource(getDataImage)
            }
        }

        // Mendapatkan referensi ke tombol bagikan
        val btnShare = findViewById<Button>(R.id.bagikan_btn)

        // Menetapkan aksi ketika tombol bagikan diklik
        btnShare.setOnClickListener {

            // Membuat intent untuk berbagi teks
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Player Name: $getDataName")
                type = "text/plain"
            }

            // Memeriksa apakah WhatsApp terinstal
            val whatsappInstalled = isPackageInstalled("com.whatsapp") || isPackageInstalled("com.whatsapp.w4b")
            if (whatsappInstalled) {

                // Jika WhatsApp terinstal, atur paket intent ke "com.whatsapp" dan mulai activity
                sendIntent.setPackage("com.whatsapp")
                startActivity(sendIntent)
            } else {

                // Jika WhatsApp tidak terinstal, tampilkan pesan toast
                Toast.makeText(this, "WhatsApp tidak terinstal.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk memeriksa apakah paket tertentu terinstal
    private fun isPackageInstalled(packageName: String): Boolean {
        return try {
            // Mencoba mendapatkan informasi paket
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            // Jika paket tidak ditemukan, kembalikan false
            false
        }
    }
}