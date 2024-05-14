package com.karyadi.skripsi.data

import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.data.source.remote.RemoteDataSource
import com.karyadi.skripsi.model.*
import javax.security.auth.callback.Callback

class Repository private constructor(private val remoteDataSource: RemoteDataSource) : DataSource {

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(remoteData: RemoteDataSource): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(remoteData).apply { instance = this }
            }
    }

    override fun getDataTukangNilai(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        remoteDataSource.getDataTukangNilai(callback)
    }

    override fun getDataTukang(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        remoteDataSource.getDataTukang(callback)
    }

    override fun getDataKategori(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        remoteDataSource.getDataKategori(callback)
    }

    fun getDatakebutuhan(callback: ResponseCallback<List<PilihJenisKebutuhan>>){
        remoteDataSource.getDataPilihJenis(callback)
    }

    override fun getDataPelangganById(
        data: Pelanggan,
        responseCallback: ResponseCallback<List<Pelanggan>>
    ) {
        remoteDataSource.getDataPelangganById(data, responseCallback)
    }

    override fun getDataTukangById(
        data: Tukang,
        responseCallback: ResponseCallback<List<Tukang>>) {
        remoteDataSource.getDataTukangById(data, responseCallback)
    }

    override fun registerPelanggan(
        data: Pelanggan,
        responseCallback: ResponseCallback<Pelanggan>
    ) {
        remoteDataSource.registerPelanggan(data, responseCallback)
    }

    override fun loginPelanggan(
        email: String, password: String,
        responseCallback: ResponseCallback<Pelanggan>
    ) {
        remoteDataSource.loginPelanggan(email, password, responseCallback)
    }

    override fun updatePelanggan(
        data: Pelanggan,
        responseCallback: ResponseCallback<Pelanggan>
    ) {
        remoteDataSource.updatePelanggan(data, responseCallback)
    }

    override fun registerTukang(
        data: Tukang,
        responseCallback: ResponseCallback<Tukang>
    ) {
        remoteDataSource.registerTukang(data, responseCallback)
    }

    override fun loginTukang(
        email: String, password: String,
        responseCallback: ResponseCallback<Tukang>
    ) {
        remoteDataSource.loginTukang(email, password, responseCallback)
    }

    override fun updateTukang(data: Tukang, responseCallback: ResponseCallback<Tukang>) {
        remoteDataSource.updateTukang(data, responseCallback)
    }

    override fun getListDetailKategori(
        data: Tukang,
        callback: ResponseCallback<List<DetailKategoriTukang>>
    ) {
        remoteDataSource.getListDetailKategori(data, callback)
    }

    override fun getListDetailKategoriInPelanggan(
        data: DetailKategoriNilai, callback: ResponseCallback<List<DetailKategoriNilai>>
    ) {
        remoteDataSource.getListDetailKategoriInPelanggan(data, callback)
    }

    override fun insertDataDetailKategoriTukang(
        data: DetailKategori,
        responseCallback: ResponseCallback<DetailKategori>
    ) {
        remoteDataSource.insertDataDetailKategoriTukang(data, responseCallback)
    }

    override fun deleteDataDetailKategori(
        data: DetailKategoriTukang,
        responseCallback: ResponseCallback<Int>
    ) {
        remoteDataSource.deleteDataDetailKategori(data, responseCallback)
    }

    override fun updateDataDetailKategori(
        data: DetailKategori,
        responseCallback: ResponseCallback<DetailKategori>
    ) {
        remoteDataSource.updateDataDetailKategori(data, responseCallback)
    }

    override fun getDataTukangByKategori(
        data: DetailKategoriNilai,
        callback: ResponseCallback<List<DetailKategoriNilai>>
    ) {
        remoteDataSource.getDataTukangByKategori(data, callback)
    }

    override fun getDataUkuran(callback: ResponseCallback<List<UkuranDetailKategori>>) {
        remoteDataSource.getDataUkuran(callback)
    }

    override fun getUkuranByDetailKategori(
        data: UkuranDetailKategori,
        callback: ResponseCallback<List<UkuranDetailKategori>>
    ) {
        remoteDataSource.getUkuranByDetailKategori(data, callback)
    }

    override fun insertDataUkuranDetailKategori(
        data: UkuranDetailKategori,
        responseCallback: ResponseCallback<UkuranDetailKategori>
    ) {
        remoteDataSource.insertDataUkuranDetailKategori(data, responseCallback)
    }

    override fun deleteDataUkuranDetailKategori(
        data: UkuranDetailKategori,
        responseCallback: ResponseCallback<ResponseDeleteSuccess>
    ) {
        remoteDataSource.deleteDataUkuranDetailKategori(data, responseCallback)
    }

    override fun insertDataRating(data: Rating, responseCallback: ResponseCallback<Rating>) {
        remoteDataSource.insertDataRating(data, responseCallback)
    }

    override fun getDataPesananById(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        remoteDataSource.getDataPesananById(data, responseCallback)
    }

    override fun getDataDetailPesananById(
        data: Pesanan,
        responseCallback: ResponseCallback<List<DetailPesanan>>
    ) {
        remoteDataSource.getDataDetailPesananById(data, responseCallback)
    }

    override fun getDataPesananByPelanggan(
        data: Pelanggan,
        callback: ResponseCallback<List<Pesanan>>
    ) {
        remoteDataSource.getDataPesananByPelanggan(data, callback)
    }

    override fun getDataPesananByTukang(
        data: Tukang,
        callback: ResponseCallback<List<Pesanan>>
    ) {
        remoteDataSource.getDataPesananByTukang(data, callback)
    }

    override fun insertDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        remoteDataSource.insertDataPesanan(data, responseCallback)
    }

    override fun updateDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        remoteDataSource.updateDataPesanan(data, responseCallback)
    }

    override fun deleteDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        remoteDataSource.deleteDataPesanan(data, responseCallback)
    }

    override fun getDataUkuranByPesanan(
        data: Pesanan,
        callback: ResponseCallback<List<UkuranDetailPesanan>>
    ) {
        remoteDataSource.getDataUkuranByPesanan(data, callback)
    }

    override fun getDataUkuranPesananByDetailKategori(
        data: Pesanan,
        callback: ResponseCallback<List<UkuranDetailPesanan>>
    ) {
        remoteDataSource.getDataUkuranPesananByDetailKategori(data, callback)
    }

    override fun insertDataUkuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        remoteDataSource.insertDataUkuranDetailPesanan(data, responseCallback)
    }

    override fun updateDatakuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        remoteDataSource.updateDatakuranDetailPesanan(data, responseCallback)
    }

    override fun deleteDatakuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        remoteDataSource.deleteDatakuranDetailPesanan(data, responseCallback)
    }

}