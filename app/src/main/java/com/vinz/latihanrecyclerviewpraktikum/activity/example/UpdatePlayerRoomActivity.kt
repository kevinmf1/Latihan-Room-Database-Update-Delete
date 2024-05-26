package com.vinz.latihanrecyclerviewpraktikum.activity.example

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.room.example.AppViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity
import com.vinz.latihanrecyclerviewpraktikum.room.example.RoomViewModelFactory
import com.vinz.latihanrecyclerviewpraktikum.utils.reduceFileImage
import com.vinz.latihanrecyclerviewpraktikum.utils.uriToFile
import java.io.File

class UpdatePlayerRoomActivity : AppCompatActivity() {
    // Mendeklarasikan variabel untuk menyimpan URI gambar saat ini dan foto lama.
    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null

    // Mendeklarasikan ViewModel untuk interaksi dengan database dan komponen UI lainnya.
    private lateinit var appViewModel: AppViewModel
    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextInputEditText
    private lateinit var playerDescription: TextInputEditText
    private lateinit var playerImageInput: TextInputEditText
    private lateinit var getData: PlayerEntity

    // Mendeklarasikan imagePickerLauncher untuk memilih gambar dari galeri atau kamera.
    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            playerImage.visibility = View.VISIBLE
            currentImageUri = firstImage.uri

            // Menggunakan Glide untuk memuat gambar ke ImageView.
            Glide.with(playerImage)
                .load(firstImage.uri)
                .into(playerImage)
        } else {
            View.GONE
        }
    }

    // Fungsi onCreate dipanggil ketika activity dibuat.
    // Fungsi ini digunakan untuk melakukan inisialisasi awal untuk activity.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_player_room)

        // Mendapatkan data pemain dari intent.
        getData = intent.getParcelableExtra("player")!!

        // Mendapatkan instance ViewModel.
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout.
        playerImage = findViewById(R.id.player_image)
        playerName = findViewById(R.id.player_name_edit)
        playerDescription = findViewById(R.id.player_desc_edit)
        playerImageInput = findViewById(R.id.player_image_edit)

        // Mengatur teks untuk setiap komponen TextInputEditText.
        playerName.setText(getData!!.name)
        playerDescription.setText(getData.description)
        playerImageInput.setText("Gambar berhasil ditambahkan")

        // Mendapatkan foto lama dari data pemain.
        oldPhoto = getData.image

        // Menggunakan Glide untuk memuat gambar ke ImageView.
        Glide.with(playerImage)
            .load(getData.image)
            .into(playerImage)

        // Menangani aksi klik.
        onClick()
    }

    // Fungsi onClick digunakan untuk menangani aksi klik pada tombol dan TextInputEditText.
    private fun onClick() {
        val btnSavedPlayer = findViewById<MaterialButton>(R.id.saved_player)
        btnSavedPlayer.setOnClickListener {
            // Jika semua input valid, simpan data.
            if (validateInput()) {
                savedData()
            }
        }

        // Menangani aksi klik pada TextInputEditText untuk membuka image picker.
        val openImagePicker = findViewById<TextInputEditText>(R.id.player_image_edit)
        openImagePicker.setOnClickListener {
            imagePickerLauncher.launch(
                ImagePickerConfig {
                    mode = ImagePickerMode.SINGLE
                    returnMode = ReturnMode.ALL
                    isFolderMode = true
                    folderTitle = "Galeri"
                    isShowCamera = false
                    imageTitle = "Tekan untuk memilih gambar"
                    doneButtonText = "Selesai"
                }
            )
        }
    }

    // Fungsi validateInput digunakan untuk memvalidasi input dari pengguna.
    private fun validateInput(): Boolean {
        var error = 0

        // Jika input kosong, tambahkan pesan error.
        if (playerName.text.toString().isEmpty()) {
            error++
            playerName.error = "Nama pemain tidak boleh kosong"
        }

        if (playerDescription.text.toString().isEmpty()) {
            error++
            playerDescription.error = "Deskripsi pemain tidak boleh kosong"
        }

        if (playerImageInput.text.toString().isEmpty()) {
            error++
            playerImageInput.error = "Gambar tidak boleh kosong"
        }

        // Jika tidak ada error, kembalikan true. Jika ada, kembalikan false.
        return error == 0
    }

    // Fungsi savedData digunakan untuk menyimpan data pemain yang diperbarui ke database.
    private fun savedData() {
        // Mengurangi ukuran file gambar.
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        // Membuat objek PlayerEntity baru dengan data yang diperbarui.
        val player = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
            PlayerEntity(
                id = getData.id,
                name = playerName.text.toString(),
                description = playerDescription.text.toString(),
                image = it
            )
        }

        // Memperbarui data pemain di database.
        if (player != null) appViewModel.updatePlayer(player)

        // Menampilkan pesan bahwa data pemain berhasil diubah.
        Toast.makeText(
            this@UpdatePlayerRoomActivity,
            "Data pemain berhasil diubah",
            Toast.LENGTH_SHORT
        ).show()

        // Menutup activity.
        finish()
    }
}