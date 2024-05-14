package com.karyadi.skripsi.ui.pelanggan.dashboard.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemListTukangBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.utils.Constant
import java.text.DecimalFormat

class RekomendasiTukangAdapter : RecyclerView.Adapter<RekomendasiTukangAdapter.RekomendasiTukangViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private lateinit var dataPelanggan: Pelanggan

    fun setupDataPelanggan(data: Pelanggan) {
        dataPelanggan = data
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    var listTukang = mutableListOf<DetailKategoriNilai>()

    fun setTukang(tukang: List<DetailKategoriNilai>) {
        this.listTukang.clear()
        this.listTukang.addAll(tukang)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RekomendasiTukangViewHolder {
        val itemListTukangBinding = ItemListTukangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RekomendasiTukangViewHolder(itemListTukangBinding)
    }

    override fun onBindViewHolder(
        holder: RekomendasiTukangViewHolder,
        position: Int
    ) {
        val data = listTukang[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listTukang.size
    }

    inner class RekomendasiTukangViewHolder(private var binding: ItemListTukangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailKategoriNilai) {
            binding.apply {
                tvNamaToko.text = data.nama_toko
                tvNamaTukang.text = data.nama_tukang
                val df = DecimalFormat("#.#")
                val extraRating = data.nilai_akhir
                val rating = df.format(extraRating)
//                tvRating.text = rating.toString()
                tvRating.text = extraRating.toString()
                tvJarak.text = " " + getHasilOlahDataLongLat(dataPelanggan, data) + " km"

                Glide.with(itemView.context)
                    .load("${Constant.IMAGE_TUKANG}${data.foto_tukang}")
                    .into(imgProfile)


                root.setOnClickListener {

                    onItemClickCallback.onItemClicked(listTukang[adapterPosition])

//                    Log.d("Test", "CLICK FROM ADAPTER")

//                    val intent = Intent(binding.root.context, DetailTukangPelangganActivity::class.java)

                    //==============================
                    // Using Gson
                    // val extraData = Gson().toJson(data)
                    // intent.putExtra(DetailTukangActivity.EXTRA_TUKANG, extraData)
                    //==============================

                    //==============================
                    // Using Parcelable
//                    intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_TUKANG, data)

//                    binding.root.context.startActivity(intent)
                    //==============================
                }
            }
        }


    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailKategoriNilai)
    }

    fun getHasilOlahDataLongLat(dataPelanggan: Pelanggan, dataNilai: DetailKategoriNilai): String {

        val lat1 = dataPelanggan.latitude_pelanggan
        val long1 = dataPelanggan.longitude_pelanggan
        val lat2 = dataNilai.latitude_tukang
        val long2 = dataNilai.longitude_tukang

        Log.d("Lat1 : ", lat1.toString())
        Log.d("Long1 : ", long1.toString())
        Log.d("Lat2 : ", lat2.toString())
        Log.d("Long2 : ", long2.toString())


        if (lat1 != null && long2 != null){
            val lati1 : Double = lat1!!.toDouble()
            val longi1 : Double = long1!!.toDouble()
            val lati2 : Double = lat2!!.toDouble()
            val longi2 : Double = long2!!.toDouble()

            Log.d("Lati1 : ", lati1.toString())
            Log.d("Longi1 : ", longi1.toString())
            Log.d("Lati2 : ", lati2.toString())
            Log.d("Longi2 : ", longi2.toString())

            val longDiff : Double = longi1 - longi2
            Log.d("Different Longitude", longDiff.toString())

            var distance: Double = (Math.sin(deg2rad(lati1)) * Math.sin(deg2rad(lati2))) + (Math.cos(deg2rad(lati1)) * Math.cos(deg2rad(lati2)) * Math.cos(deg2rad(longDiff)))

            distance = Math.acos(distance)
            distance = rad2deg(distance)
            distance = distance * 60 * 1.1515
            distance = distance * 1.609344
            Log.d("in km : ", distance.toString())

            val df = DecimalFormat("#.#")
            Log.d("with decimal format: ", (df.format(distance)).toString())

//        val x = 0.92837
//        val df = DecimalFormat("#.##")
//        println(df.format(x))

//        val hasilOlah = latPelanggan!! + longPelanggan!! + latTukang!! + longTukang!!

            return (df.format(distance)).toString()
        } else{

            var hasil = 0

            return hasil.toString()
        }

    }

    private fun deg2rad(lat: Double): Double {
        return (lat * Math.PI / 180.0)
    }

    private fun rad2deg(distance: Double): Double {
        return (distance * 180.0 / Math.PI)
    }
}