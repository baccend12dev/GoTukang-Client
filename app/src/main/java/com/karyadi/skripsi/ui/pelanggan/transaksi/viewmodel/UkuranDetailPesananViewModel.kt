package com.karyadi.skripsi.ui.pelanggan.transaksi.viewmodel

import com.karyadi.skripsi.core.BaseViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.model.UkuranDetailPesanan
import com.karyadi.skripsi.utils.SingleLiveEvent

class UkuranDetailPesananViewModel(private val repository: Repository) : BaseViewModel() {

    val listUkuranDetailPesanan = SingleLiveEvent<List<UkuranDetailPesanan>>()
    val dataUkuranDetailPesanan = SingleLiveEvent<UkuranDetailPesanan>()
    var messageFailed = SingleLiveEvent<String>()
    var messageSuccess = SingleLiveEvent<String>()
    var showProgress = SingleLiveEvent<Boolean>()
    var onSuccessState = SingleLiveEvent<Boolean>()


    fun getDataUkuranByPesanan(data: Pesanan){

        repository.getDataUkuranByPesanan(data, object : ResponseCallback<List<UkuranDetailPesanan>>{
            override fun onSuccess(data: List<UkuranDetailPesanan>) {
                listUkuranDetailPesanan.postValue(data)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                eventErrorMessage.postValue(errorMessage)
            }

            override fun onShowProgress() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgress() {
                eventShowProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                eventIsEmpty.postValue(check)
            }

        })
    }

    fun getDataUkuranPesananByDetailKategory(data: Pesanan){

        repository.getDataUkuranPesananByDetailKategori(data, object : ResponseCallback<List<UkuranDetailPesanan>>{
            override fun onSuccess(data: List<UkuranDetailPesanan>) {
                listUkuranDetailPesanan.postValue(data)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                eventErrorMessage.postValue(errorMessage)
            }

            override fun onShowProgress() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgress() {
                eventShowProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                eventIsEmpty.postValue(check)
            }

        })
    }

    fun insertDataUkuranDetailPesanan(data: UkuranDetailPesanan){

        repository.insertDataUkuranDetailPesanan(data, object : ResponseCallback<UkuranDetailPesanan>{
            override fun onSuccess(data: UkuranDetailPesanan) {
                dataUkuranDetailPesanan.postValue(data)
                messageSuccess.postValue("Data Berhasil Ditambahkan")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgress() {
                eventShowProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                eventIsEmpty.postValue(check)
            }

        })
    }

    fun updateDataUkuranDetailPesanan(data: UkuranDetailPesanan){

        repository.updateDatakuranDetailPesanan(data, object : ResponseCallback<UkuranDetailPesanan>{
            override fun onSuccess(data: UkuranDetailPesanan) {
                dataUkuranDetailPesanan.postValue(data)
                messageSuccess.postValue("Data Berhasil Diubah")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgress() {
                eventShowProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                eventIsEmpty.postValue(check)
            }

        })
    }

    fun deleteDataUkuranDetailPesanan(data: UkuranDetailPesanan){

        repository.deleteDatakuranDetailPesanan(data, object : ResponseCallback<UkuranDetailPesanan>{
            override fun onSuccess(data: UkuranDetailPesanan) {
                dataUkuranDetailPesanan.postValue(data)
                messageSuccess.postValue("Data Berhasil Dihapus")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                eventShowProgress.postValue(true)
            }

            override fun onHideProgress() {
                eventShowProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                eventIsEmpty.postValue(check)
            }

        })
    }

}