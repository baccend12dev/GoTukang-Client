package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pesanan(
    val id_pesanan: Int?,
    val id_pelanggan: Int?,
    val id_tukang: Int?,
    val id_detail_kategori: Int?,
    val tanggal_pesanan: String?,
    val tanggal_pesanan_selesai: String?,
    val lama_waktu_pengerjaan: String?,
    val catatan_pesanan: String?,
//    val desain_tukang: String?,
//    val bahan_tukang: String?,
//    val asal_bahan: String?,
//    val panjang_bahan: String?,
//    val lebar_bahan: String?,
//    val status_bahan: String?,
//    val harga_bahan: Int?,
    val ongkos_tukang: Int?,
    val jumlah_tukang: Int?,
//    val biaya_tukang: Int?,
    val total_biaya: Int?,
    val status_pesanan: String?,
) : Parcelable