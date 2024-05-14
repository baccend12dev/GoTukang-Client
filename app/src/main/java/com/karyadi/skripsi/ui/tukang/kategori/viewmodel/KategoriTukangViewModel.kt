package com.karyadi.skripsi.ui.tukang.kategori.viewmodel

import com.karyadi.skripsi.core.BaseViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.DetailKategori
import com.karyadi.skripsi.model.DetailKategoriTukang
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.utils.SingleLiveEvent

class KategoriTukangViewModel(private val repository: Repository) : BaseViewModel() {

    var listDetailKategori = SingleLiveEvent<List<DetailKategoriTukang>>()
    var dataDetailKategori = SingleLiveEvent<DetailKategori>()
    var dataListDetailKategori = SingleLiveEvent<DetailKategoriTukang>()
    var messageFailed = SingleLiveEvent<String>()
    var messageSuccess = SingleLiveEvent<String>()
    var onSuccessState = SingleLiveEvent<Boolean>()
    var onSuccessDeleteState = SingleLiveEvent<Boolean>()
    var deleteItemPosition = SingleLiveEvent<Int>()

    fun getListDetailKategori(data: Tukang){

        repository.getListDetailKategori(data, object : ResponseCallback<List<DetailKategoriTukang>>{
            override fun onSuccess(data: List<DetailKategoriTukang>) {
                listDetailKategori.postValue(data)
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

    fun insertDataDetailKategori(data: DetailKategori){
        repository.insertDataDetailKategoriTukang(data, object : ResponseCallback<DetailKategori>{
            override fun onSuccess(data: DetailKategori) {
                dataDetailKategori.postValue(data)
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
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteDataDetailKategori(data: DetailKategoriTukang, position: Int){

        repository.deleteDataDetailKategori(data, object : ResponseCallback<Int>{
            override fun onSuccess(data: Int) {
                onSuccessDeleteState.postValue(true)
                messageSuccess.postValue("Data Berhasil Dihapus")
                deleteItemPosition.postValue(position)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessDeleteState.postValue(false)
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

    fun updateDataDetailKategori(data: DetailKategori){
        repository.updateDataDetailKategori(data, object : ResponseCallback<DetailKategori>{
            override fun onSuccess(data: DetailKategori) {
                dataDetailKategori.postValue(data)
                messageSuccess.postValue("Data Berhasil Diperbarui")
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
                TODO("Not yet implemented")
            }

        })
    }


}