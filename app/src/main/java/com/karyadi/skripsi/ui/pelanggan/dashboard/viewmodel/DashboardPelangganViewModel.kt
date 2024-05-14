package com.karyadi.skripsi.ui.pelanggan.dashboard.viewmodel

import com.karyadi.skripsi.core.BaseViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.PilihJenisKebutuhan
import com.karyadi.skripsi.utils.SingleLiveEvent

class DashboardPelangganViewModel (private val repository: Repository) : BaseViewModel() {

    val listNilai = SingleLiveEvent<List<DetailKategoriNilai>>()
    val listKategori = SingleLiveEvent<List<DetailKategoriNilai>>()
    val listDataTukangByKategori = SingleLiveEvent<List<DetailKategoriNilai>>()
    val listDataPilihJenisKebutuhan = SingleLiveEvent<List<PilihJenisKebutuhan>>()

    fun getDataTukang(){
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

    fun getDataPilihjenis() {
        repository.getDatakebutuhan(object : ResponseCallback<List<PilihJenisKebutuhan>> {
            override fun onSuccess(data: List<PilihJenisKebutuhan>) {
                listDataPilihJenisKebutuhan.postValue(data)
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

    fun getDataTukangByKategori(data: DetailKategoriNilai){
        repository.getDataTukangByKategori(data, object : ResponseCallback<List<DetailKategoriNilai>> {
            override fun onSuccess(data: List<DetailKategoriNilai>) {
                listDataTukangByKategori.postValue(data)
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