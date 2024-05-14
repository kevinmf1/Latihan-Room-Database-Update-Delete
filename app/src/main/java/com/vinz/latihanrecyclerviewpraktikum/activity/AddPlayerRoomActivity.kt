package com.vinz.latihanrecyclerviewpraktikum.activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.vinz.latihanrecyclerviewpraktikum.room.AppViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.PlayerDatabase
import com.vinz.latihanrecyclerviewpraktikum.room.RoomViewModelFactory
import com.vinz.latihanrecyclerviewpraktikum.utils.reduceFileImage
import com.vinz.latihanrecyclerviewpraktikum.utils.uriToFile

class AddPlayerRoomActivity : AppCompatActivity() {

    // Mendeklarasikan variabel untuk menyimpan URI gambar yang dipilih
    private var currentImageUri: Uri? = null
    // Mendeklarasikan ImageView untuk menampilkan gambar yang dipilih
    private lateinit var playerImage: ImageView
    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var appViewModel: AppViewModel
    // Mendeklarasikan EditText untuk input nama pemain
    private lateinit var playerName: TextInputEditText
    // Mendeklarasikan EditText untuk input deskripsi pemain
    private lateinit var playerDescription: TextInputEditText
    // Mendeklarasikan EditText untuk input gambar pemain
    private lateinit var playerImageInput: TextInputEditText

    // Mendeklarasikan image picker untuk memilih gambar dari galeri
    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            // Menampilkan ImageView jika gambar berhasil dipilih
            playerImage.visibility = View.VISIBLE
            // Menyimpan URI gambar yang dipilih
            currentImageUri = firstImage.uri
            // Menampilkan pesan bahwa gambar berhasil dimasukkan
            playerImageInput.setText("Gambar berhasil dimasukkan")

            // Menggunakan library Glide untuk menampilkan gambar yang dipilih
            Glide.with(playerImage)
                .load(firstImage.uri)
                .into(playerImage)
        } else {
            // Menyembunyikan ImageView jika tidak ada gambar yang dipilih
            View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_player_room)

        // Mendapatkan instance ViewModel
        val factory = RoomViewModelFactory.getInstance(this)
        appViewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        playerImage = findViewById(R.id.player_image)
        playerName = findViewById(R.id.player_name_edit)
        playerDescription = findViewById(R.id.player_desc_edit)
        playerImageInput = findViewById(R.id.player_image_edit)

        // Memanggil fungsi onClick untuk menangani aksi klik
        onClick()
    }

    private fun onClick() {
        // Menangani aksi klik pada EditText untuk memilih gambar
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

        // Menangani aksi klik pada tombol simpan
        val btnSavedPlayer = findViewById<MaterialButton>(R.id.saved_player)
        btnSavedPlayer.setOnClickListener {
            // Memvalidasi input dan menyimpan data jika valid
            if (validateInput()) {
                savedData()
            }
        }
    }

    // Fungsi untuk memvalidasi input
    private fun validateInput(): Boolean {
        var error = 0

        // Memeriksa apakah nama pemain kosong
        if (playerName.text.toString().isEmpty()) {
            error++
            playerName.error = "Nama pemain tidak boleh kosong"
        }

        // Memeriksa apakah deskripsi pemain kosong
        if (playerDescription.text.toString().isEmpty()) {
            error++
            playerDescription.error = "Deskripsi pemain tidak boleh kosong"
        }

        // Memeriksa apakah gambar pemain kosong
        if (playerImageInput.text.toString().isEmpty()) {
            error++
            playerImageInput.error = "Gambar tidak boleh kosong"
        }

        // Mengembalikan true jika tidak ada error, false jika ada error
        return error == 0
    }

    // Fungsi untuk menyimpan data pemain
    private fun savedData() {
        // Mengubah URI gambar menjadi file dan mengurangi ukuran file
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        // Membuat objek pemain dengan data yang diinputkan
        val player = imageFile?.let {
            PlayerDatabase(
                id = 0,
                name = playerName.text.toString(),
                description = playerDescription.text.toString(),
                image = imageFile
            )
        }

        // Menyimpan data pemain ke database
        if (player != null) appViewModel.insertPlayer(player)

        // Menampilkan pesan bahwa data pemain berhasil ditambahkan
        Toast.makeText(
            this@AddPlayerRoomActivity,
            "Data pemain berhasil ditambahkan",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}