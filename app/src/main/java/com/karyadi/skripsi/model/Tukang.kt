package com.karyadi.skripsi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tukang(
    val id_tukang: Int?,
    val nama_tukang: String?,
    val email_tukang: String?,
    val password_tukang: String?,
    val telp_tukang: String?,
    val nama_jasa: String?,
    val keterangan_jasa: String?,
//    @SerializedName("keterangan_jasa") var keterangan_jasa: String? = null,
    val latitude_tukang: String?,
    val longitude_tukang: String?,
    val alamat_tukang: String?,
    val spesifikasi_tukang: String?,
    val jangkauan_kategori_tukang: String?,
    val foto_tukang: String?
//    @SerializedName("hari_buka") var hari_buka: String? = null,
//    @SerializedName("jam_buka") var jam_buka: String? = null,
//    @SerializedName("jam_tutup") var jam_tutup: String? = null,

) : Parcelable