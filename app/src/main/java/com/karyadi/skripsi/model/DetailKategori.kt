package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailKategori(
    val id_detail_kategori: Int?,
    val id_tukang: Int?,
    val id_kategori: Int?,
    val keterangan_kategori: String?,
    val bahan_tukang: String?,
    val harga_bahan: String,
    val ongkos_tukang: String,
    val perkiraan_lama_waktu_pengerjaan: String?
) : Parcelable