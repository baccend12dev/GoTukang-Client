package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nilai(
    val alamat_tukang: String? = null,
    val email_tukang: String? = null,
    val foto_tukang: String? = null,
    val hari_buka: String? = null,
    val id_nilai: Int? = 0,
    val id_tukang: Int? = 0,
    val jam_buka: String? = null,
    val jam_tutup: String? = null,
    val jangkauan_kategori_tukang: String? = null,
    val keterangan_toko: String? = null,
    val latitude_tukang: String? = null,
    val longitude_tukang: String? = null,
    val nama_tukang: String? = null,
    val nama_toko: String? = null,
    val nilai_akhir: Double?,
    val password_tukang: String? = null,
    val spesifikasi_tukang: String? = null,
    val telp_tukang: String? = null
) :Parcelable