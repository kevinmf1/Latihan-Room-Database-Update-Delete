package com.vinz.latihanrecyclerviewpraktikum.room.practice

import androidx.lifecycle.LiveData
import com.vinz.latihanrecyclerviewpraktikum.utils.AppExecutors

// Kelas PeopleRepository adalah kelas yang bertugas untuk menghubungkan Dao dan ViewModel.
// Kelas ini menggunakan pola desain Repository, yang merupakan pola desain yang disarankan oleh Google untuk memastikan bahwa aplikasi Anda dapat bekerja dengan berbagai sumber data.
class PeopleRepository private constructor(
    // Variabel appDao adalah instance dari PeopleDao yang akan digunakan untuk mengakses database.
    private val appDao: PeopleDao,
    // Variabel appExecutors adalah instance dari AppExecutors yang akan digunakan untuk menjalankan operasi database di thread yang berbeda dari thread UI.
    private val appExecutors: AppExecutors
) {

    // Fungsi getAllPeople digunakan untuk mendapatkan semua data orang dari database.
    // Fungsi ini mengembalikan LiveData yang berisi daftar semua orang.
    fun getAllPeople(): LiveData<List<PeopleEntity>> = appDao.getAllPeople()

    // Fungsi insertPlayer digunakan untuk memasukkan data orang ke dalam database.
    // Fungsi ini menjalankan operasi insert di thread yang berbeda dari thread UI menggunakan AppExecutors.
    fun insertPlayer(peopleEntity: PeopleEntity) {
        appExecutors.diskIO().execute { appDao.insertPeople(peopleEntity) }
    }

    fun updatePeople(peopleEntity: PeopleEntity) {
        appExecutors.diskIO().execute {
            appDao.updatePeople(peopleEntity)
        }
    }

    fun deletePeople(peopleEntity: PeopleEntity) {
        appExecutors.diskIO().execute {
            appDao.deletePeople(peopleEntity)
        }
    }

    // Ini adalah objek companion yang berisi fungsi getInstance.
    // Fungsi getInstance digunakan untuk mendapatkan instance dari PeopleRepository.
    // Jika instance sudah ada, fungsi ini akan mengembalikan instance tersebut.
    // Jika instance belum ada, fungsi ini akan membuat instance baru.
    companion object {
        @Volatile
        private var instance: PeopleRepository? = null

        fun getInstance(
            peopleDao: PeopleDao,
            appExecutors: AppExecutors
        ): PeopleRepository =
            // Jika instance sudah ada, kembalikan instance tersebut.
            // Jika instance belum ada, buat instance baru.
            instance ?: synchronized(this) {
                instance ?: PeopleRepository(peopleDao, appExecutors)
            }.also { instance = it }
    }
}