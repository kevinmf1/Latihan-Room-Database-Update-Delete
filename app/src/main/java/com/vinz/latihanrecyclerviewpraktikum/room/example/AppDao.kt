package com.vinz.latihanrecyclerviewpraktikum.room.example

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity

/**
 * Interface AppDao adalah Data Access Object (DAO) yang berfungsi sebagai jembatan antara aplikasi dan database SQLite.
 * DAO berisi metode yang menyediakan akses ke operasi database seperti insert dan query.
 *
 * Anotasi @Dao digunakan untuk memberi tahu Room bahwa interface ini adalah DAO.
 */

@Dao
interface AppDao {

    /**
     * Fungsi insertPlayer digunakan untuk memasukkan data pemain ke dalam database.
     * Anotasi @Insert digunakan untuk memberi tahu Room bahwa fungsi ini digunakan untuk memasukkan data.
     * Parameter onConflict digunakan untuk menentukan apa yang harus dilakukan Room jika data yang dimasukkan memiliki konflik dengan data yang sudah ada di database.
     * OnConflictStrategy.IGNORE berarti jika ada konflik, Room akan mengabaikan operasi insert.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPlayer(player: PlayerEntity)

    /**
     * Fungsi getAllPlayer digunakan untuk mendapatkan semua data pemain dari database.
     * Anotasi @Query digunakan untuk memberi tahu Room bahwa fungsi ini digunakan untuk menjalankan query SQL.
     * Query "SELECT * from playerdatabase ORDER BY player_name ASC" berarti memilih semua data dari tabel playerdatabase dan mengurutkannya berdasarkan player_name dalam urutan ascending.
     * Fungsi ini mengembalikan LiveData yang berisi daftar semua pemain. LiveData adalah kelas dari Android Architecture Components yang memungkinkan kita untuk mengamati perubahan data dalam database dan secara otomatis memperbarui UI jika ada perubahan.
     */
    @Query("SELECT * from playerentity")
    fun getAllPlayer(): LiveData<List<PlayerEntity>>

    /**
     * Fungsi deletePlayer digunakan untuk menghapus data pemain dari database.
     * Anotasi @Delete digunakan untuk memberi tahu Room bahwa fungsi ini digunakan untuk menghapus data.
    */
    @Delete
    fun deletePlayer(playerEntity: PlayerEntity)

    /**
     * Fungsi updatePlayer digunakan untuk memperbarui data pemain dalam database.
     * Anotasi @Update digunakan untuk memberi tahu Room bahwa fungsi ini digunakan untuk memperbarui data.
    */
    @Update
    fun updatePlayer(playerEntity: PlayerEntity)
}