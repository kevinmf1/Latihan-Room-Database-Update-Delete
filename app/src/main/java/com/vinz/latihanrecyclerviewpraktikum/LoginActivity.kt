package com.vinz.latihanrecyclerviewpraktikum

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    // Deklarasi variabel untuk input username dan password
    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Menghubungkan variabel dengan komponen di layout
        usernameInput = findViewById(R.id.username_edit)
        passwordInput = findViewById(R.id.password_edit)

        // Mendapatkan referensi ke tombol login
        val loginBtn = findViewById<MaterialButton>(R.id.login_button)

        // Menetapkan aksi ketika tombol login diklik
        loginBtn.setOnClickListener {

            // Memvalidasi inputan
            if (validateInput()) {

                // Membuat intent untuk berpindah ke MainActivity
                val intent = Intent(this, MainActivity::class.java)

                // Menambahkan dan membawa data username dan password ke intent dengan tujuan ke MainActivity
                intent.putExtra("name", usernameInput.text.toString())

                // Memulai activity baru
                startActivity(intent)

                // Menutup activity saat ini
                finishAffinity()
            } else {

                // Menampilkan pesan toast jika validasi gagal
                Toast.makeText(this, "Tolong masukkan inputan dengan benar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk memvalidasi inputan
    private fun validateInput() : Boolean{
        // Set error ke 0
        var error = 0

        // Memeriksa apakah inputan username kosong
        if(usernameInput.text.toString().isEmpty()) {

            // Jika terjadi error, menambahkan 1 angka
            error++

            // Menampilkan pesan error pada inputan username
            usernameInput.error = "Tolong masukkan username kamu!"
        }

        // Memeriksa apakah inputan password kosong
        if (passwordInput.text.toString().isEmpty()) {

            // Jika terjadi error, menambahkan 1 angka
            error++

            // Menampilkan pesan error pada inputan password
            passwordInput.error = "Tolong masukkan password kamu!"
        }

        // Jika error 0, mengembalikan return true, jika bukan 0 maka mengembalikan false
        return error == 0
    }
}