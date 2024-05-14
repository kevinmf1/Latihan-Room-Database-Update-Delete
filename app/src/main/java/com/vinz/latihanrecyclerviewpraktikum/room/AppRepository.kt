package com.vinz.latihanrecyclerviewpraktikum.room

import androidx.lifecycle.LiveData
import com.vinz.latihanrecyclerviewpraktikum.utils.AppExecutors

/**
 * Kelas AppRepository adalah kelas yang berfungsi sebagai repositori untuk mengakses data dari database.
 * Kelas ini memiliki konstruktor privat yang menerima AppDao dan AppExecutors sebagai parameter.
 *
 * Fungsi getAllPlayer() digunakan untuk mendapatkan semua data pemain dari database.
 * Fungsi ini mengembalikan LiveData yang berisi daftar semua pemain.
 *
 * Fungsi insertPlayer(player: PlayerDatabase) digunakan untuk memasukkan data pemain ke dalam database.
 * Fungsi ini menjalankan operasi insert di thread yang berbeda menggunakan AppExecutors.
 *
 * Objek pendamping untuk AppRepository dibuat di dalamnya, yang berisi variabel instance yang akan menyimpan instance dari AppRepository.
 *
 * Fungsi statis getInstance(appDao: AppDao, appExecutors: AppExecutors) digunakan untuk mendapatkan instance dari AppRepository.
 * Jika instance null, maka akan dibuat instance baru.
 * Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread.
 *
 * Mengembalikan instance dari AppRepository.
 */

class AppRepository private constructor(private val appDao: AppDao, private val appExecutors: AppExecutors) {

    // Mendapatkan semua data pemain dari database
    fun getAllPlayer(): LiveData<List<PlayerDatabase>> = appDao.getAllPlayer()

    // Memasukkan data pemain ke dalam database
    fun insertPlayer(player: PlayerDatabase) {
        // Menjalankan operasi insert di thread yang berbeda
        appExecutors.diskIO().execute { appDao.insertPlayer(player) }
    }

    companion object {
        // Variabel untuk menyimpan instance dari AppRepository
        @Volatile
        private var instance: AppRepository? = null

        // Mendapatkan instance dari AppRepository
        fun getInstance(
            appDao: AppDao,
            appExecutors: AppExecutors
        ): AppRepository =
            // Jika instance null, maka akan dibuat instance baru
            instance ?: synchronized(this) {
                instance ?: AppRepository(appDao, appExecutors)
            }.also { instance = it }
    }
}