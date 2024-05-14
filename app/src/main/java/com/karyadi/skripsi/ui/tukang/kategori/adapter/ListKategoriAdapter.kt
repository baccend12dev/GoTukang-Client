package com.karyadi.skripsi.ui.tukang.kategori.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemListKategoriBinding
import com.karyadi.skripsi.model.DetailKategoriTukang
import com.karyadi.skripsi.utils.Constant

class ListKategoriAdapter : RecyclerView.Adapter<ListKategoriAdapter.ListKategoriViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var onDeleteClickCallback: OnDeleteClickCallback
    private lateinit var onUpdateClickCallback: OnUpdateClickCallback
    var listDetailKategori = mutableListOf<DetailKategoriTukang>()


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setOnDeleteClickCallback(onDeleteClickCallback: OnDeleteClickCallback){
        this.onDeleteClickCallback = onDeleteClickCallback
    }

    fun setOnUpdateClickCallback(onUpdateClickCallback: OnUpdateClickCallback){
        this.onUpdateClickCallback = onUpdateClickCallback
    }

    fun setDetailKategori(kategoriTukang: List<DetailKategoriTukang>){
        this.listDetailKategori.clear()
        this.listDetailKategori.addAll(kategoriTukang)
    }

    fun deleteItem(position: Int) {
        Log.d("Position Removed", position.toString())
        this.listDetailKategori.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListKategoriViewHolder {
        val itemListKategoriBinding = ItemListKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListKategoriViewHolder(itemListKategoriBinding)
    }

    override fun onBindViewHolder(holder: ListKategoriViewHolder, position: Int) {
        val data = listDetailKategori[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return listDetailKategori.size
    }

    inner class ListKategoriViewHolder(private var binding: ItemListKategoriBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailKategoriTukang, position: Int){
            binding.apply {

                tvKategori.text = data.nama_kategori

                Glide.with(root.context)
                    .load("${Constant.IMAGE_KATEGORI}${data.gambar_kategori}")
                    .into(imgKategori)

                btnDelete.setOnClickListener {
                    onDeleteClickCallback.onDeleteClicked(listDetailKategori[adapterPosition], position)
//                    popupDelete(root.context)
//                    deleteData(root.context, data)
                }

                btnEdit.setOnClickListener {
                    onUpdateClickCallback.onUpdateClikced(listDetailKategori[adapterPosition])
                }

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(listDetailKategori[adapterPosition])

                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailKategoriTukang)
    }

    interface OnDeleteClickCallback{
        fun onDeleteClicked(data: DetailKategoriTukang, position: Int)
    }

    interface OnUpdateClickCallback{
        fun onUpdateClikced(data: DetailKategoriTukang)
    }


}