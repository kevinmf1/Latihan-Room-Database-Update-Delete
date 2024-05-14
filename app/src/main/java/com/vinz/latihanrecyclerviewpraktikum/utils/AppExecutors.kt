package com.vinz.latihanrecyclerviewpraktikum.utils

import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Kelas AppExecutors adalah kelas yang berfungsi untuk mengelola eksekusi thread dalam aplikasi.
 * Kelas ini memiliki konstruktor yang menerima tiga Executor sebagai parameter: diskIO, networkIO, dan mainThread.
 *
 * diskIO: Executor untuk operasi I/O disk.
 * networkIO: Executor untuk operasi I/O jaringan.
 * mainThread: Executor untuk operasi yang berjalan di thread utama.
 *
 * Konstruktor kedua adalah konstruktor default yang membuat Executor untuk diskIO, networkIO, dan mainThread.
 *
 * Fungsi diskIO(), networkIO(), dan mainThread() digunakan untuk mendapatkan Executor yang sesuai.
 *
 * Kelas MainThreadExecutor adalah kelas internal yang mengimplementasikan Executor dan menjalankan command di thread utama.
 */
class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor,
    private val networkIO: Executor,
    private val mainThread: Executor
) {

    companion object {
        private const val THREAD_COUNT = 3
    }

    // Konstruktor default yang membuat Executor untuk diskIO, networkIO, dan mainThread
    constructor() : this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(THREAD_COUNT),
        MainThreadExecutor()
    )

    // Mendapatkan Executor untuk operasi I/O disk
    fun diskIO(): Executor = diskIO

    // Mendapatkan Executor untuk operasi I/O jaringan
    fun networkIO(): Executor = networkIO

    // Mendapatkan Executor untuk operasi yang berjalan di thread utama
    fun mainThread(): Executor = mainThread

    // Kelas internal yang mengimplementasikan Executor dan menjalankan command di thread utama
    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}