package com.vinz.latihanrecyclerviewpraktikum.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * Kelas AppViewModel adalah kelas yang berfungsi sebagai ViewModel dalam arsitektur MVVM (Model-View-ViewModel).
 * Kelas ini memiliki konstruktor yang menerima AppRepository sebagai parameter.
 *
 * Fungsi insertPlayer(player: PlayerDatabase) digunakan untuk memasukkan data pemain ke dalam database.
 * Fungsi ini memanggil fungsi insertPlayer di AppRepository.
 *
 * Fungsi getAllPlayer() digunakan untuk mendapatkan semua data pemain dari database.
 * Fungsi ini memanggil fungsi getAllPlayer di AppRepository dan mengembalikan LiveData yang berisi daftar semua pemain.
 */
class AppViewModel(private val appRepository: AppRepository) : ViewModel() {

    // Memasukkan data pemain ke dalam database
    fun insertPlayer(player: PlayerDatabase) {
        appRepository.insertPlayer(player)
    }

    // Mendapatkan semua data pemain dari database
    fun getAllPlayer(): LiveData<List<PlayerDatabase>> {
        return appRepository.getAllPlayer()
    }
}