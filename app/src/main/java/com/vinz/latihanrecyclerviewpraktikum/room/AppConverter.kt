package com.vinz.latihanrecyclerviewpraktikum.room

import androidx.room.TypeConverter
import java.io.File

/**
 * Anotasi TypeConverter digunakan untuk memberi tahu Room bagaimana mengubah objek kustom menjadi tipe yang dapat disimpan dalam database SQLite
 */

class AppConverter {

    // Fungsi ini mengubah objek File menjadi String (path file)
    @TypeConverter
    fun fromFile(file: File?): String? {
        // Mengembalikan path dari file jika file tidak null, jika null maka mengembalikan null
        return file?.path
    }

    // Fungsi ini mengubah String (path file) menjadi objek File
    @TypeConverter
    fun toFile(path: String?): File? {
        // Membuat objek File dari path jika path tidak null, jika null maka mengembalikan null
        return if (path != null) File(path) else null
    }
}