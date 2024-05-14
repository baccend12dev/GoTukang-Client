package com.karyadi.skripsi.ui.tukang.dashboard.viewmodel

import com.karyadi.skripsi.core.BaseViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.utils.SingleLiveEvent

class DashboardTukangViewModel (private val repository: Repository) : BaseViewModel() {

    var listNilai = SingleLiveEvent<List<DetailKategoriNilai>>()
    var listKategori = SingleLiveEvent<List<DetailKategoriNilai>>()

    fun getDataTukang() {
        repository.getDataTukangNilai(object : ResponseCallback<List<DetailKategoriNilai>>{
            override fun onSuccess(data: List<DetailKategoriNilai>) {
                listNilai.postValue(data)
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

    fun getDataKategori(){
        repository.getDataKategori(object : ResponseCallback<List<DetailKategoriNilai>>{
            override fun onSuccess(data: List<DetailKategoriNilai>) {
                listKategori.postValue(data)
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

}