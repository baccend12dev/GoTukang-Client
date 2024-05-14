package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailKategoriTukang(
    val id_detail_kategori: Int? = 0,
    val id_tukang: Int? = 0,
    val id_kategori: Int? = 0,
    val alamat_tukang: String? = "",
    val bahan_tukang: String? = "",
    val email_tukang: String? = "",
    val foto_tukang: String? = "",
    val gambar_kategori: String? = "",
    val harga_bahan: String? = "",
    val hari_buka: String? = "",
    val jam_buka: String? = "",
    val jam_tutup: String? = "",
    val keterangan_toko: String? = "",
    val jangkauan_kategori_tukang: String? = "",
    val spesifikasi_tukang: String? = "",
    val perkiraan_lama_waktu_pengerjaan: String? = "",
    val keterangan_kategori: String? = "",
    val latitude_tukang: String? = "",
    val longitude_tukang: String? = "",
    val nama_kategori: String? = "",
    val nama_tukang: String? = "",
    val nama_toko: String? = "",
    val ongkos_tukang: String? = "",
    val password_tukang: String? = "",
    val telp_tukang: String? = ""
) : Parcelable