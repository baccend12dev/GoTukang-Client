package com.karyadi.skripsi.ui.pelanggan.detail.viewmodel

import com.karyadi.skripsi.core.BaseViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.utils.SingleLiveEvent

class KategoriTukangInPelangganViewModel(private val repository: Repository) : BaseViewModel() {

    var listDetailKategori = SingleLiveEvent<List<DetailKategoriNilai>>()
    var messageFailed = SingleLiveEvent<String>()
    var messageSuccess = SingleLiveEvent<String>()
    var onSuccessState = SingleLiveEvent<Boolean>()

    fun getListDetailKategori(data: DetailKategoriNilai){

        repository.getListDetailKategoriInPelanggan(data, object : ResponseCallback<List<DetailKategoriNilai>>{
            override fun onSuccess(data: List<DetailKategoriNilai>) {
                listDetailKategori.postValue(data)
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                eventErrorMessage.postValue(errorMessage)
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