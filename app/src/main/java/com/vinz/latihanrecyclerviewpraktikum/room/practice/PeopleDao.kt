package com.vinz.latihanrecyclerviewpraktikum.room.practice

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// Anotasi @Dao digunakan untuk memberi tahu Room bahwa interface ini adalah DAO (Data Access Object).
// DAO adalah interface yang berfungsi sebagai jembatan antara aplikasi dan database SQLite.
@Dao
interface PeopleDao {

    // Fungsi ini digunakan untuk memasukkan data orang ke dalam database.
    // Anotasi @Insert memberi tahu Room bahwa fungsi ini digunakan untuk memasukkan data.
    // Parameter onConflict digunakan untuk menentukan apa yang harus dilakukan Room jika data yang dimasukkan memiliki konflik dengan data yang sudah ada di database.
    // OnConflictStrategy.IGNORE berarti jika ada konflik, Room akan mengabaikan operasi insert.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPeople(peopleEntity: PeopleEntity)

    // Fungsi ini digunakan untuk mendapatkan semua data orang dari database.
    // Anotasi @Query digunakan untuk memberi tahu Room bahwa fungsi ini digunakan untuk menjalankan query SQL.
    // Query "SELECT * FROM PeopleEntity ORDER BY id DESC" berarti memilih semua data dari tabel PeopleEntity dan mengurutkannya berdasarkan id dalam urutan menurun.
    // Fungsi ini mengembalikan LiveData yang berisi daftar semua orang. LiveData adalah kelas dari Android Architecture Components yang memungkinkan kita untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
    @Query("SELECT * FROM PeopleEntity ORDER BY id DESC")
    fun getAllPeople() : LiveData<List<PeopleEntity>>

    @Update
    fun updatePeople(peopleEntity: PeopleEntity)

    @Delete
    fun deletePeople(peopleEntity: PeopleEntity)
}