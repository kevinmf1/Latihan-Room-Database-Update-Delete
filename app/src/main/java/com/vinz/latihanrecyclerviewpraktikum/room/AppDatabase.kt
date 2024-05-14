package com.vinz.latihanrecyclerviewpraktikum.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Kelas AppDatabase adalah kelas abstrak yang berfungsi sebagai holder database dan merupakan titik akses ke database SQLite yang mendasarinya.
 * Kelas ini mendeklarasikan database dengan entitas PlayerDatabase dan versi 1.
 * Kelas ini juga menggunakan konverter tipe khusus (AppConverter) untuk mengubah tipe data File menjadi String dan sebaliknya.
 *
 * Fungsi appDao() adalah fungsi abstrak yang mengembalikan AppDao.
 *
 * Objek pendamping untuk AppDatabase dibuat di dalamnya, yang berisi variabel INSTANCE yang akan menyimpan instance dari AppDatabase.
 *
 * Fungsi statis getDatabase(context: Context) digunakan untuk mendapatkan instance database.
 * Jika INSTANCE null, maka akan dibuat instance baru.
 * Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread.
 * Membuat instance baru dari AppDatabase menggunakan Room.databaseBuilder.
 *
 * Mengembalikan instance dari AppDatabase.
 */

// Mendeklarasikan database dengan entitas PlayerDatabase dan versi 1
@Database(entities = [PlayerDatabase::class], version = 1)

// Menggunakan konverter tipe khusus untuk mengubah tipe data File menjadi String dan sebaliknya
@TypeConverters(AppConverter::class)

// Membuat kelas abstrak AppDatabase yang merupakan turunan dari RoomDatabase
abstract class AppDatabase : RoomDatabase() {

    // Mendeklarasikan fungsi abstrak yang mengembalikan AppDao
    abstract fun appDao(): AppDao

    // Membuat objek pendamping untuk AppDatabase
    companion object {
        // Mendeklarasikan variabel INSTANCE yang akan menyimpan instance dari AppDatabase
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Membuat fungsi statis untuk mendapatkan instance database
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            // Jika INSTANCE null, maka akan dibuat instance baru
            if (INSTANCE == null) {
                // Menggunakan synchronized untuk mencegah akses bersamaan dari beberapa thread
                synchronized(AppDatabase::class.java) {
                    // Membuat instance baru dari AppDatabase
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    )
                        .build()
                }
            }
            // Mengembalikan instance dari AppDatabase
            return INSTANCE as AppDatabase
        }
    }
}