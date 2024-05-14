package com.karyadi.skripsi.ui.tukang.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemCardKategoriBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.utils.Constant

class KategoriAdapter : RecyclerView.Adapter<KategoriAdapter.KategoriViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    var listKategori = mutableListOf<DetailKategoriNilai>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setKategori(kategori: List<DetailKategoriNilai>){
//        if (kategori == null) return
        this.listKategori.clear()
        this.listKategori.addAll(kategori)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategoriViewHolder {
        val itemCardKategoriBinding = ItemCardKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KategoriViewHolder(itemCardKategoriBinding)
    }

    override fun onBindViewHolder(holder: KategoriViewHolder, position: Int) {
        val data = listKategori[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listKategori.size
    }

    inner class KategoriViewHolder(private var binding: ItemCardKategoriBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailKategoriNilai){
            binding.apply {
                tvNamaKategori.text = data.nama_kategori
                Glide.with(itemView.context)
                    .load("${Constant.IMAGE_KATEGORI}${data.gambar_kategori}")
                    .into(ivGambarKategori)

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(listKategori[adapterPosition])
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailKategoriNilai)
    }
}