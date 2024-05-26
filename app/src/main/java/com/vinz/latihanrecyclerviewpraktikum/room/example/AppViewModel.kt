package com.vinz.latihanrecyclerviewpraktikum.room.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

// Kelas AppViewModel adalah kelas yang bertugas untuk menghubungkan Repository dan UI.
// Kelas ini mewarisi ViewModel, yang merupakan kelas dari Android Architecture Components yang digunakan untuk menyimpan dan mengelola data yang terkait dengan UI.
class AppViewModel(private val appRepository: AppRepository) : ViewModel() {

    // Fungsi insertPlayer digunakan untuk memasukkan data pemain ke dalam database.
    // Fungsi ini menerima parameter berupa objek PlayerEntity dan memanggil fungsi insertPlayer dari AppRepository.
    fun insertPlayer(player: PlayerEntity) {
        appRepository.insertPlayer(player)
    }

    // Fungsi getAllPlayer digunakan untuk mendapatkan semua data pemain dari database.
    // Fungsi ini mengembalikan LiveData yang berisi daftar semua pemain.
    // LiveData adalah kelas dari Android Architecture Components yang memungkinkan kita untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
    fun getAllPlayer(): LiveData<List<PlayerEntity>> {
        return appRepository.getAllPlayer()
    }

    // Fungsi deletePlayer digunakan untuk menghapus data pemain dari database.
    // Fungsi ini menerima parameter berupa objek PlayerEntity dan memanggil fungsi deletePlayer dari AppRepository.
    fun deletePlayer(player: PlayerEntity) {
        appRepository.deletePlayer(player)
    }

    // Fungsi updatePlayer digunakan untuk memperbarui data pemain dalam database.
    // Fungsi ini menerima parameter berupa objek PlayerEntity dan memanggil fungsi updatePlayer dari AppRepository.
    fun updatePlayer(player: PlayerEntity) {
        appRepository.updatePlayer(player)
    }
}