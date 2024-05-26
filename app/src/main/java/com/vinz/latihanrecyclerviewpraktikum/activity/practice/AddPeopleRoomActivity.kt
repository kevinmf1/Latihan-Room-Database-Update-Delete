package com.vinz.latihanrecyclerviewpraktikum.activity.practice

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.ReturnMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.room.example.RoomViewModelFactory
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleEntity
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleViewModel
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleViewModelFactory
import com.vinz.latihanrecyclerviewpraktikum.utils.reduceFileImage
import com.vinz.latihanrecyclerviewpraktikum.utils.uriToFile

class AddPeopleRoomActivity : AppCompatActivity() {

    // Mendeklarasikan variabel untuk menyimpan URI gambar yang dipilih
    private var currentImageUri: Uri? = null

    // Mendeklarasikan ImageView untuk menampilkan gambar yang dipilih
    private lateinit var peopleImage: ImageView

    // Mendeklarasikan ViewModel untuk interaksi dengan database
    private lateinit var peopleViewModel: PeopleViewModel

    // Mendeklarasikan EditText untuk input nama orang
    private lateinit var peopleName: TextInputEditText

    // Mendeklarasikan EditText untuk input gambar orang
    private lateinit var peopleImageInput: TextInputEditText

    // Mendeklarasikan image picker untuk memilih gambar dari galeri
    private val imagePickerLauncher = registerImagePicker {
        val firstImage = it.firstOrNull() ?: return@registerImagePicker
        if (firstImage.uri.toString().isNotEmpty()) {
            // Menampilkan ImageView jika gambar berhasil dipilih
            peopleImage.visibility = View.VISIBLE
            // Menyimpan URI gambar yang dipilih
            currentImageUri = firstImage.uri
            // Menampilkan pesan bahwa gambar berhasil dimasukkan
            peopleImageInput.setText("Gambar berhasil ditambahkan")

            // Menggunakan library Glide untuk menampilkan gambar yang dipilih
            Glide.with(peopleImage)
                .load(firstImage.uri)
                .into(peopleImage)

        } else {
            // Menyembunyikan ImageView jika tidak ada gambar yang dipilih
            View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_people_room)

        // Mendapatkan instance ViewModel
        val factory = PeopleViewModelFactory.getInstance(this)
        peopleViewModel = ViewModelProvider(this, factory)[PeopleViewModel::class.java]

        // Menghubungkan variabel dengan komponen di layout
        peopleImage = findViewById(R.id.people_image)
        peopleName = findViewById(R.id.people_name_edit)
        peopleImageInput = findViewById(R.id.people_image_edit)

        // Memanggil fungsi onClick untuk menangani aksi klik
        onClick()
    }

    private fun onClick() {
        // Menangani aksi klik pada EditText untuk memilih gambar
        val openImagePicker = findViewById<TextInputEditText>(R.id.people_image_edit)
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
        val btnSavedPeople = findViewById<MaterialButton>(R.id.saved_people)
        btnSavedPeople.setOnClickListener {
            // Memvalidasi input dan menyimpan data jika valid
            if (validateInput()) {
                savedData()
            }
        }
    }

    // Fungsi untuk memvalidasi input
    private fun validateInput(): Boolean {
        var error = 0

        // Memeriksa apakah nama orang kosong
        if (peopleName.text.toString().isEmpty()) {
            error++
            peopleName.error = "Nama orang tidak boleh kosong"
        }

        // Memeriksa apakah gambar orang kosong
        if (peopleImageInput.text.toString().isEmpty()) {
            error++
            peopleImageInput.error = "Gambar tidak boleh kosong"
        }

        // Mengembalikan true jika tidak ada error, false jika ada error
        return error == 0
    }

    // Fungsi untuk menyimpan data orang
    private fun savedData() {
        // Mengubah URI gambar menjadi file dan mengurangi ukuran file
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        // Membuat objek orang dengan data yang diinputkan
        val people = imageFile?.let {
            PeopleEntity(
                id = 0,
                name = peopleName.text.toString(),
                image = imageFile
            )
        }

        // Menyimpan data orang ke database
        if (people != null) peopleViewModel.insertPeople(people)

        // Menampilkan pesan bahwa data orang berhasil ditambahkan
        Toast.makeText(
            this@AddPeopleRoomActivity,
            "Data orang berhasil ditambahkan",
            Toast.LENGTH_SHORT
        ).show()

        // Mengakhiri activity
        finish()
    }
}