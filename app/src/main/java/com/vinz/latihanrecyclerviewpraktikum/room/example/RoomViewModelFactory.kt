package com.vinz.latihanrecyclerviewpraktikum.room.example

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinz.latihanrecyclerviewpraktikum.utils.DependencyInjection

/**
 * Kelas RoomViewModelFactory adalah kelas yang berfungsi sebagai pabrik untuk membuat instance ViewModel.
 * Kelas ini memiliki konstruktor privat yang menerima AppRepository sebagai parameter.
 *
 * Fungsi create(modelClass: Class<T>) digunakan untuk membuat instance ViewModel.
 * Fungsi ini memeriksa apakah modelClass dapat ditugaskan dari AppViewModel.
 * Jika ya, maka akan membuat instance baru dari AppViewModel dan mengembalikannya.
 * Jika tidak, maka akan melempar IllegalArgumentException.
 *
 * Objek pendamping untuk RoomViewModelFactory dibuat di dalamnya, yang berisi variabel instance yang akan menyimpan instance dari RoomViewModelFactory.
 *
 * Fungsi statis getInstance(context: Context) digunakan untuk mendapatkan instance dari RoomViewModelFactory.
 * Jika instance null, maka akan dibuat instance baru.
 * Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread.
 *
 * Mengembalikan instance dari RoomViewModelFactory.
 */

class RoomViewModelFactory private constructor(private val appRepository: AppRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: RoomViewModelFactory? = null
        fun getInstance(context: Context): RoomViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: RoomViewModelFactory(DependencyInjection.provideRepository(context))
            }.also { instance = it }
    }
}