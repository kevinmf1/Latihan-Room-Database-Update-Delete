package com.vinz.latihanrecyclerviewpraktikum.room.example

import androidx.lifecycle.LiveData
import com.vinz.latihanrecyclerviewpraktikum.utils.AppExecutors

// Kelas AppRepository adalah kelas yang berfungsi sebagai repositori untuk mengakses data dari database.
// Kelas ini memiliki konstruktor privat yang menerima AppDao dan AppExecutors sebagai parameter.
class AppRepository private constructor(private val appDao: AppDao, private val appExecutors: AppExecutors) {

    // Fungsi getAllPlayer() digunakan untuk mendapatkan semua data pemain dari database.
    // Fungsi ini mengembalikan LiveData yang berisi daftar semua pemain.
    fun getAllPlayer(): LiveData<List<PlayerEntity>> = appDao.getAllPlayer()

    // Fungsi insertPlayer(player: PlayerEntity) digunakan untuk memasukkan data pemain ke dalam database.
    // Fungsi ini menjalankan operasi insert di thread yang berbeda menggunakan AppExecutors.
    fun insertPlayer(player: PlayerEntity) {
        appExecutors.diskIO().execute { appDao.insertPlayer(player) }
    }

    // Fungsi deletePlayer(player: PlayerEntity) digunakan untuk menghapus data pemain dari database.
    // Fungsi ini menjalankan operasi delete di thread yang berbeda menggunakan AppExecutors.
    fun deletePlayer(player: PlayerEntity) {
        appExecutors.diskIO().execute { appDao.deletePlayer(player) }
    }

    // Fungsi updatePlayer(player: PlayerEntity) digunakan untuk memperbarui data pemain dalam database.
    // Fungsi ini menjalankan operasi update di thread yang berbeda menggunakan AppExecutors.
    fun updatePlayer(player: PlayerEntity) {
        appExecutors.diskIO().execute { appDao.updatePlayer(player) }
    }

    // Ini adalah objek companion yang berisi fungsi getInstance.
    // Fungsi getInstance digunakan untuk mendapatkan instance dari AppRepository.
    // Jika instance sudah ada, fungsi ini akan mengembalikan instance tersebut.
    // Jika instance belum ada, fungsi ini akan membuat instance baru.
    companion object {
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(
            appDao: AppDao,
            appExecutors: AppExecutors
        ): AppRepository =
            // Jika instance sudah ada, kembalikan instance tersebut.
            // Jika instance belum ada, buat instance baru.
            instance ?: synchronized(this) {
                instance ?: AppRepository(appDao, appExecutors)
            }.also { instance = it }
    }
}