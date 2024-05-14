package com.vinz.latihanrecyclerviewpraktikum.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.File

/**
 * Kelas PlayerDatabase adalah kelas data yang berfungsi sebagai entitas dalam database Room.
 * Kelas ini memiliki beberapa properti yang mewakili kolom dalam tabel database.
 *
 * Anotasi @Entity digunakan untuk memberi tahu Room bahwa kelas ini adalah tabel dalam database.
 *
 * Anotasi @PrimaryKey digunakan untuk menentukan bahwa properti id adalah kunci utama dalam tabel.
 * autoGenerate = true berarti Room akan menghasilkan nilai id secara otomatis setiap kali kita memasukkan entitas baru ke dalam tabel.
 *
 * Anotasi @ColumnInfo digunakan untuk menentukan nama kolom dalam tabel.
 *
 * Kelas ini juga mengimplementasikan Parcelable, yang memungkinkan kita untuk mempassing objek PlayerDatabase antar komponen Android.
 */

@Entity
data class PlayerDatabase(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "player_name")
    val name: String,

    @ColumnInfo(name = "player_desc")
    val description: String,

    @ColumnInfo(name = "player_image")
    val image: File
) : Parcelable {
    // Konstruktor sekunder untuk membuat objek PlayerDatabase dari Parcel
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        File(parcel.readString()!!)
    )

    // Fungsi untuk menulis data objek PlayerDatabase ke Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(image.path)
    }

    // Fungsi untuk mendeskripsikan jenis konten khusus yang ditangani oleh Parcelable
    override fun describeContents(): Int {
        return 0
    }

    // Objek pendamping untuk PlayerDatabase yang berisi fungsi untuk membuat objek PlayerDatabase dari Parcel dan Array
    companion object CREATOR : Parcelable.Creator<PlayerDatabase> {
        // Fungsi untuk membuat objek PlayerDatabase dari Parcel
        override fun createFromParcel(parcel: Parcel): PlayerDatabase {
            return PlayerDatabase(parcel)
        }

        // Fungsi untuk membuat array dari objek PlayerDatabase
        override fun newArray(size: Int): Array<PlayerDatabase?> {
            return arrayOfNulls(size)
        }
    }
}