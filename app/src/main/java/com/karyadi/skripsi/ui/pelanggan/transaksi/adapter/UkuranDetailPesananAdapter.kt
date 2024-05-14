package com.karyadi.skripsi.ui.pelanggan.transaksi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemListUkuranPesananTukangBinding
import com.karyadi.skripsi.model.UkuranDetailPesanan
import com.karyadi.skripsi.utils.Constant

class UkuranDetailPesananAdapter : RecyclerView.Adapter<UkuranDetailPesananAdapter.UkuranDetailPesananViewHolder>() {

    val listUkuranPesanan = mutableListOf<UkuranDetailPesanan>()

    fun setUkuranPesanan(listUkuranPesanan: List<UkuranDetailPesanan>){
        this.listUkuranPesanan.clear()
        this.listUkuranPesanan.addAll(listUkuranPesanan)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UkuranDetailPesananViewHolder {
        val itemListUkuranPesananTukangBinding = ItemListUkuranPesananTukangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UkuranDetailPesananViewHolder(itemListUkuranPesananTukangBinding)
    }

    override fun onBindViewHolder(holder: UkuranDetailPesananViewHolder, position: Int) {
        val data = listUkuranPesanan[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listUkuranPesanan.size
    }

    inner class UkuranDetailPesananViewHolder(private var binding: ItemListUkuranPesananTukangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UkuranDetailPesanan){
            binding.apply {
                tvNamaUkuran.text = data.nama_ukuran
                tvUkuran.text = data.ukuran_pesanan.toString()

                Glide.with(root.context)
                    .load("${Constant.IMAGE_UKURAN}${data.gambar_ukuran}")
                    .into(imgUkuran)

            }
        }
    }
}