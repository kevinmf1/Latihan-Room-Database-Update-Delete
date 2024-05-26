package com.vinz.latihanrecyclerviewpraktikum.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vinz.latihanrecyclerviewpraktikum.R
import com.vinz.latihanrecyclerviewpraktikum.room.practice.PeopleEntity

// Kelas PeopleAdapterRoom adalah kelas yang bertugas untuk mengatur tampilan data orang pada RecyclerView.
// Kelas ini menerima daftar orang sebagai parameter konstruktor dan mewarisi RecyclerView.Adapter.
class PeopleAdapterRoom(private var playerList: List<PeopleEntity>) :
    RecyclerView.Adapter<PeopleAdapterRoom.PlayerViewHolder>() {

    // Deklarasi variabel onItemClickCallback yang akan digunakan untuk menangani klik pada item RecyclerView.
    private lateinit var onItemClickCallback: OnItemClickCallback

    // Fungsi setOnItemClickCallback digunakan untuk mengatur callback untuk klik item.
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Interface OnItemClickCallback digunakan untuk mendefinisikan metode callback yang akan dipanggil ketika item diklik.
    interface OnItemClickCallback {
        fun onItemClicked(data: PeopleEntity)
        fun onItemMore(data: PeopleEntity)
    }

    // Kelas PlayerViewHolder adalah kelas yang bertugas untuk menyimpan referensi ke tampilan item pada RecyclerView.
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val peopleName: TextView = itemView.findViewById(R.id.people_name)
        val peopleImage: ImageView = itemView.findViewById(R.id.people_image)
        val btnMore: ImageView = itemView.findViewById(R.id.btn_more)
        val btnFav: ImageView = itemView.findViewById(R.id.btn_fav)
    }


    // Fungsi onCreateViewHolder digunakan untuk membuat ViewHolder baru untuk item RecyclerView.
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_people, parent, false)
        return PlayerViewHolder(view)
    }

    private var fav = false

    // Fungsi onBindViewHolder digunakan untuk mengatur tampilan item pada RecyclerView berdasarkan data orang.
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val data = playerList[position]

        holder.peopleName.text = data.name

        val uri = Uri.fromFile(data.image)
        holder.peopleImage.setImageURI(uri)

        // Menetapkan onClickListener pada itemView untuk memanggil callback ketika item diklik.
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(playerList[holder.absoluteAdapterPosition]) }

        holder.btnMore.setOnClickListener { onItemClickCallback.onItemMore(playerList[holder.absoluteAdapterPosition]) }

        holder.btnFav.setOnClickListener {
            fav = !fav
            holder.btnFav.setImageResource(if (fav) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
        }

    }

    // Fungsi getItemCount digunakan untuk mendapatkan jumlah item pada RecyclerView.
    override fun getItemCount(): Int = playerList.size
}