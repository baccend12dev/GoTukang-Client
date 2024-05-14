package com.karyadi.skripsi.ui.tukang.dashboard.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.ItemListTukangBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.tukang.dashboard.DetailTukangActivity
import com.karyadi.skripsi.utils.Constant
import java.text.DecimalFormat

class TukangAdapter : RecyclerView.Adapter<TukangAdapter.TukangViewHolder>() {

    var listTukang  = mutableListOf<DetailKategoriNilai>()
    private lateinit var dataTukang: Tukang

    //mendapatkan extraData tukang dari dashboard
    fun setupDataTukang(data: Tukang) {
        dataTukang = data
    }

    fun setTukang(tukang : List<DetailKategoriNilai>){
        this.listTukang.clear()
        this.listTukang.addAll(tukang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TukangViewHolder {
        val itemListTukangBinding = ItemListTukangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TukangViewHolder(itemListTukangBinding)
    }

    override fun onBindViewHolder(holder: TukangViewHolder, position: Int) {
        val data = listTukang[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listTukang.size
    }

    inner class TukangViewHolder(private var binding: ItemListTukangBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailKategoriNilai){
            binding.apply {
                tvNamaToko.text = data.nama_toko
                tvNamaTukang.text = data.nama_tukang
                val df = DecimalFormat("#.#")
                val extraRating = data.nilai_akhir
                val rating = df.format(extraRating)
//                tvRating.text = rating.toString()
                tvRating.text = extraRating.toString()
                tvJarak.text = " " + getHasilOlahDataLongLat(dataTukang, data) + " km"

                Glide.with(itemView.context)
                    .load("${Constant.IMAGE_TUKANG}${data.foto_tukang}")
                    .into(imgProfile)

                itemView.setOnClickListener {
                    Log.d("Test", "CLICK FROM ADAPTER")

//                Toast.makeText(binding.root.context, "Hai ", Toast.LENGTH_SHORT).show()
                    val intent = Intent(binding.root.context, DetailTukangActivity::class.java)

                    // Using Gson
//                val extraData = Gson().toJson(data)
//                intent.putExtra(DetailTukangActivity.EXTRA_TUKANG, extraData)

                    // Using Parcelable
                    intent.putExtra(DetailTukangActivity.EXTRA_DATA_NILAI, data)
                    intent.putExtra(DetailTukangActivity.EXTRA_DATA_TUKANG, dataTukang)

                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    private fun getHasilOlahDataLongLat(dataTukang: Tukang, dataNilai: DetailKategoriNilai): String {

        val lat1 = dataTukang.latitude_tukang
        val long1 = dataTukang.longitude_tukang
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