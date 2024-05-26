package com.vinz.latihanrecyclerviewpraktikum.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.room.example.PlayerEntity

class PlayerAdapterRoom(private var playerList: List<PlayerEntity>) :
    RecyclerView.Adapter<PlayerAdapterRoom.PlayerViewHolder>() {

    // Deklarasi variabel untuk callback ketika item diklik
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var stateFav = false

    // Fungsi untuk mengatur callback ketika item diklik
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Interface untuk callback ketika item diklik
    interface OnItemClickCallback {
        fun onItemClicked(data: PlayerEntity)
        fun onMoreClicked(data: PlayerEntity, position: Int)
    }

    // Kelas ViewHolder untuk menyimpan referensi view yang digunakan dalam RecyclerView
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName: MaterialTextView = itemView.findViewById(R.id.player_name)
        val playerDescription: MaterialTextView = itemView.findViewById(R.id.player_description)
        val playerImage: ShapeableImageView = itemView.findViewById(R.id.player_image)
        val btnMore: ImageView = itemView.findViewById(R.id.btn_more)
        val btnFav: ImageView = itemView.findViewById(R.id.btn_fav)
    }

    // Fungsi untuk membuat ViewHolder (Melakukan setting untuk XML yang akan kita gunakan untuk menampilkan data)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_room, parent, false)
        return PlayerViewHolder(view)
    }

    // Fungsi untuk mengikat data dengan ViewHolder (memasukkan data yang kita miliki ke dalam XML ViewHolder)
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val data = playerList[position]

        holder.playerName.text = data.name
        holder.playerDescription.text = data.description

        // Mengatur image
        val uri = Uri.fromFile(data.image)
        holder.playerImage.setImageURI(uri)

        // Mengatur aksi ketika item diklik
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(playerList[holder.absoluteAdapterPosition]) }

        // Mengatur aksi ketika button more diklik
        holder.btnMore.setOnClickListener { onItemClickCallback.onMoreClicked(playerList[holder.absoluteAdapterPosition], holder.absoluteAdapterPosition) }

        // Mengatur aksi ketika botton favorit diklik
        holder.btnFav.setOnClickListener {
            stateFav = !stateFav
            holder.btnFav.setImageResource(if (stateFav) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }
    }

    // Fungsi untuk mendapatkan jumlah item
    override fun getItemCount(): Int = playerList.size

    // Fungsi untuk memendekkan teks jika melebihi panjang maksimum
    private fun String.shorten(maxLength: Int): String {
        return if (this.length <= maxLength) this else "${this.substring(0, maxLength)}..."
    }
}