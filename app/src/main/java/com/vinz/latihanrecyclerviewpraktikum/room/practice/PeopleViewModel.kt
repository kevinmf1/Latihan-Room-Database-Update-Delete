package com.vinz.latihanrecyclerviewpraktikum.room.practice

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

// Kelas PeopleViewModel adalah kelas yang bertugas untuk menghubungkan Repository dan UI.
// Kelas ini mewarisi ViewModel, yang merupakan kelas dari Android Architecture Components yang digunakan untuk menyimpan dan mengelola data yang terkait dengan UI.
class PeopleViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {

    // Fungsi insertPeople digunakan untuk memasukkan data orang ke dalam database.
    // Fungsi ini menerima parameter berupa objek PeopleEntity dan memanggil fungsi insertPlayer dari PeopleRepository.
    fun insertPeople(people: PeopleEntity) {
        peopleRepository.insertPlayer(people)
    }

    // Fungsi getAllPeople digunakan untuk mendapatkan semua data orang dari database.
    // Fungsi ini mengembalikan LiveData yang berisi daftar semua orang.
    // LiveData adalah kelas dari Android Architecture Components yang memungkinkan kita untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
    fun getAllPeople(): LiveData<List<PeopleEntity>> {
        return peopleRepository.getAllPeople()
    }

    fun updatePeople(peopleEntity: PeopleEntity) {
        peopleRepository.updatePeople(peopleEntity)
    }

    fun deletePeople(peopleEntity: PeopleEntity) {
        peopleRepository.deletePeople(peopleEntity)
    }
}