package com.vinz.latihanrecyclerviewpraktikum.room.practice

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity
import java.io.File

// Anotasi @Entity digunakan untuk memberi tahu Room bahwa kelas ini mewakili sebuah tabel dalam database.
@Entity
data class PeopleEntity(
    // Anotasi @PrimaryKey digunakan untuk menandai field ini sebagai primary key dari tabel.
    // autoGenerate = true berarti Room akan secara otomatis menghasilkan nilai unik untuk setiap entitas baru.
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    // Ini adalah field untuk nama orang. Tipe data String digunakan untuk teks.
    val name: String,

    // Ini adalah field untuk gambar orang. Tipe data File digunakan untuk file.
    val image: File
) : Parcelable {
    // Parcelable digunakan agar objek PeopleEntity dapat dikirimkan antar komponen Android (misalnya antar Activity).

    // Konstruktor ini digunakan saat membaca data dari Parcel.
    constructor(parcel: Parcel) : this(
        // Membaca id dari Parcel.
        parcel.readInt(),
        // Membaca nama dari Parcel.
        parcel.readString()!!,
        // Membaca path file gambar dari Parcel dan membuat objek File dari path tersebut.
        File(parcel.readString()!!)
    )

    // Fungsi ini digunakan saat menulis data ke Parcel.
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        // Menulis id ke Parcel.
        parcel.writeInt(id)
        // Menulis nama ke Parcel.
        parcel.writeString(name)
        // Menulis path file gambar ke Parcel.
        parcel.writeString(image.path)
    }

    // Fungsi ini digunakan untuk mendeskripsikan jenis konten khusus yang ditangani oleh Parcelable.
    override fun describeContents(): Int {
        return 0
    }

    // Ini adalah objek yang mengimplementasikan Parcelable.Creator, digunakan untuk membuat objek PeopleEntity dari Parcel.
    companion object CREATOR : Parcelable.Creator<PeopleEntity> {
        // Membuat objek PeopleEntity dari Parcel.
        override fun createFromParcel(parcel: Parcel): PeopleEntity {
            return PeopleEntity(parcel)
        }

        // Membuat array dari objek PeopleEntity.
        override fun newArray(size: Int): Array<PeopleEntity?> {
            return arrayOfNulls(size)
        }
    }
}