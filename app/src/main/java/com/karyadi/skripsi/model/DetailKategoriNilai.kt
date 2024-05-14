package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailKategoriNilai(
    val id_detail_kategori: Int?,
    val id_tukang: Int?,
    val id_kategori: Int?,
    val alamat_tukang: String?,
//    val bahan_tukang: String?,
    val email_tukang: String?,
    val foto_tukang: String?,
    val gambar_kategori: String?,
//    val harga_bahan: Int?,
//    val hari_buka: String?,
//    val id_nilai: Int?,
//    val jam_buka: String?,
//    val jam_tutup: String?,
    val jangkauan_kategori_tukang: String?,
    val keterangan_kategori: String?,
    val keterangan_toko: String?,
    val latitude_tukang: String?,
    val longitude_tukang: String?,
    val nama_kategori: String?,
    val nama_tukang: String?,
    val nama_toko: String?,
    val nilai_akhir: Double?,
    val ongkos_tukang: Int?,
    val password_tukang: String?,
    val perkiraan_lama_waktu_pengerjaan: String?,
    val spesifikasi_tukang: String?,
    val telp_tukang: String?
): Parcelable