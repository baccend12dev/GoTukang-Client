package com.karyadi.skripsi.data

import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.*

interface DataSource {

    fun getDataTukangNilai(callback: ResponseCallback<List<DetailKategoriNilai>>)

    fun getDataTukang(callback: ResponseCallback<List<DetailKategoriNilai>>)

    fun getDataKategori(callback: ResponseCallback<List<DetailKategoriNilai>>)

    fun getDataPelangganById(data: Pelanggan, responseCallback: ResponseCallback<List<Pelanggan>>)

    fun getDataTukangById(data: Tukang, responseCallback: ResponseCallback<List<Tukang>>)

    fun registerPelanggan(data: Pelanggan, responseCallback: ResponseCallback<Pelanggan>)

    fun loginPelanggan(email: String, password: String, responseCallback: ResponseCallback<Pelanggan>)

    fun updatePelanggan(data: Pelanggan, responseCallback: ResponseCallback<Pelanggan>)

    fun registerTukang(data: Tukang, responseCallback: ResponseCallback<Tukang>)

    fun loginTukang(email: String, password: String, responseCallback: ResponseCallback<Tukang>)

    fun updateTukang(data: Tukang, responseCallback: ResponseCallback<Tukang>)

    fun getListDetailKategori(data: Tukang, callback: ResponseCallback<List<DetailKategoriTukang>>)

    fun getListDetailKategoriInPelanggan(data: DetailKategoriNilai, callback: ResponseCallback<List<DetailKategoriNilai>>)

    fun insertDataDetailKategoriTukang(data: DetailKategori, responseCallback: ResponseCallback<DetailKategori>)

    fun deleteDataDetailKategori(data: DetailKategoriTukang, responseCallback: ResponseCallback<Int>)

    fun updateDataDetailKategori(data: DetailKategori, responseCallback: ResponseCallback<DetailKategori>)

    fun getDataTukangByKategori(data: DetailKategoriNilai, callback: ResponseCallback<List<DetailKategoriNilai>>)

    fun getDataUkuran(callback: ResponseCallback<List<UkuranDetailKategori>>)

    fun getUkuranByDetailKategori(data: UkuranDetailKategori, callback: ResponseCallback<List<UkuranDetailKategori>>)

    fun insertDataUkuranDetailKategori(data: UkuranDetailKategori, responseCallback: ResponseCallback<UkuranDetailKategori>)

    fun deleteDataUkuranDetailKategori(data: UkuranDetailKategori, responseCallback: ResponseCallback<ResponseDeleteSuccess>)

    fun insertDataRating(data: Rating, responseCallback: ResponseCallback<Rating>)

    fun getDataPesananById(data: Pesanan, responseCallback: ResponseCallback<Pesanan>)

    fun getDataDetailPesananById(data: Pesanan, responseCallback: ResponseCallback<List<DetailPesanan>>)

    fun getDataPesananByPelanggan(data: Pelanggan, callback: ResponseCallback<List<Pesanan>>)

    fun getDataPesananByTukang(data: Tukang, callback: ResponseCallback<List<Pesanan>>)

    fun insertDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>)

    fun updateDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>)

    fun deleteDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>)

    fun getDataUkuranByPesanan(data: Pesanan, callback: ResponseCallback<List<UkuranDetailPesanan>>)

    fun getDataUkuranPesananByDetailKategori(data: Pesanan, callback: ResponseCallback<List<UkuranDetailPesanan>>)

    fun insertDataUkuranDetailPesanan(data: UkuranDetailPesanan, responseCallback: ResponseCallback<UkuranDetailPesanan>)

    fun updateDatakuranDetailPesanan(data: UkuranDetailPesanan, responseCallback: ResponseCallback<UkuranDetailPesanan>)

    fun deleteDatakuranDetailPesanan(data: UkuranDetailPesanan, responseCallback: ResponseCallback<UkuranDetailPesanan>)


}
