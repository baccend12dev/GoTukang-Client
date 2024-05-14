package com.karyadi.skripsi.ui.detailkategoriInGridViewKategori.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemListTukangKategoriBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.utils.Constant

class DetailKetegoriAdapter : RecyclerView.Adapter<DetailKetegoriAdapter.DetailKategoriViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    var listTukang = mutableListOf<DetailKategoriNilai>()
    private lateinit var dataPelanggan: Pelanggan
    private lateinit var dataTukang: Tukang

    fun setupDataTukang(data: Tukang) {
        dataTukang = data
    }

    fun setupDataPelanggan(data: Pelanggan) {
        dataPelanggan = data
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setTukangByKategori(tukang: List<DetailKategoriNilai>) {
        this.listTukang.clear()
        this.listTukang.addAll(tukang)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailKategoriViewHolder {
        val itemListTukangKategoriBinding = ItemListTukangKategoriBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailKategoriViewHolder(itemListTukangKategoriBinding)
    }

    override fun onBindViewHolder(
        holder: DetailKategoriViewHolder,
        position: Int
    ) {
        val data = listTukang[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listTukang.size
    }

    inner class DetailKategoriViewHolder(private var binding: ItemListTukangKategoriBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailKategoriNilai) {
            binding.apply {
                tvNamaToko.text = data.nama_toko
                tvNamaTukang.text = data.nama_tukang
//                val df = DecimalFormat("#.#")
//                val extraRating = data.nilai_akhir
//                val rating = df.format(extraRating)
//                tvRating.text = rating.toString()

                Glide.with(itemView.context)
                    .load("${Constant.IMAGE_TUKANG}${data.foto_tukang}")
                    .into(imgProfile)

                root.setOnClickListener {

                    onItemClickCallback.onItemClicked(listTukang[adapterPosition])

//                    Toast.makeText(root.context, "Kamu memilih " + data.nama_tukang, Toast.LENGTH_SHORT).show()
//                    Log.d("Test", "CLICK FROM ADAPTER")
//                    val intent = Intent(binding.root.context, DetailTukangPelangganActivity::class.java)
//                    intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_TUKANG, data)
//                    root.context.startActivity(intent)
                }

            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailKategoriNilai)
    }
}