package com.vinz.latihanrecyclerviewpraktikum.room.practice

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinz.latihanrecyclerviewpraktikum.utils.DependencyInjection

// Kelas PeopleViewModelFactory adalah kelas yang bertugas untuk membuat instance dari PeopleViewModel.
// Kelas ini mewarisi ViewModelProvider.NewInstanceFactory, yang merupakan kelas dari Android Architecture Components yang digunakan untuk membuat instance dari ViewModel.
class PeopleViewModelFactory private constructor(private val peopleRepository: PeopleRepository) :
    ViewModelProvider.NewInstanceFactory() {

    // Fungsi create digunakan untuk membuat instance dari ViewModel.
    // Fungsi ini menerima parameter berupa Class yang merupakan kelas dari ViewModel yang ingin dibuat.
    // Jika kelas yang diberikan adalah PeopleViewModel, maka fungsi ini akan membuat instance dari PeopleViewModel.
    // Jika kelas yang diberikan bukan PeopleViewModel, maka fungsi ini akan melempar IllegalArgumentException.
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeopleViewModel::class.java)) {
            return PeopleViewModel(peopleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    // Ini adalah objek companion yang berisi fungsi getInstance.
    // Fungsi getInstance digunakan untuk mendapatkan instance dari PeopleViewModelFactory.
    // Jika instance sudah ada, fungsi ini akan mengembalikan instance tersebut.
    // Jika instance belum ada, fungsi ini akan membuat instance baru.
    companion object {
        @Volatile
        private var instance: PeopleViewModelFactory? = null
        fun getInstance(context: Context): PeopleViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: PeopleViewModelFactory(
                    DependencyInjection.providePeopleRepository(
                        context
                    )
                )
            }.also { instance = it }
    }
}