package com.karyadi.skripsi.ui.tukang.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.utils.SingleLiveEvent

class AuthTukangViewModel(private val repository: Repository) : ViewModel() {

    var dataTukang = SingleLiveEvent<Tukang>()
    var dataTukangVM = SingleLiveEvent<Tukang>()
    var messageFailed = SingleLiveEvent<String>()
    var messageSuccess = SingleLiveEvent<String>()
    var showProgress = SingleLiveEvent<Boolean>()
    var onSuccessState = SingleLiveEvent<Boolean>()

    fun getDataTukangById(data: Tukang){

        repository.getDataTukangById(data, object : ResponseCallback<List<Tukang>>{
            override fun onSuccess(data: List<Tukang>) {
                dataTukangVM.postValue(data[0])
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                showProgress.postValue(true)
            }

            override fun onHideProgress() {
                showProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }

    fun registerTukang(data: Tukang) {

        repository.registerTukang(data, object : ResponseCallback<Tukang> {
            override fun onSuccess(data: Tukang) {
                dataTukang.postValue(data)
                messageSuccess.postValue("Register Berhasil")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                showProgress.postValue(true)
            }

            override fun onHideProgress() {
                showProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                TODO("Not yet implemented")
            }

        })

    }

    fun loginTukang(email: String, password: String) {

        repository.loginTukang(email, password, object : ResponseCallback<Tukang> {
            override fun onSuccess(data: Tukang) {
                dataTukang.postValue(data)
                messageSuccess.postValue("Login Berhasil")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                showProgress.postValue(true)
            }

            override fun onHideProgress() {
                showProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }

    fun updateTukang(data: Tukang) {

        repository.updateTukang(data, object : ResponseCallback<Tukang> {
            override fun onSuccess(data: Tukang) {
                dataTukang.postValue(data)
                messageSuccess.postValue("Data Berhasil Diperbarui")
                onSuccessState.postValue(true)
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                messageFailed.postValue(errorMessage)
                onSuccessState.postValue(false)
            }

            override fun onShowProgress() {
                showProgress.postValue(true)
            }

            override fun onHideProgress() {
                showProgress.postValue(false)
            }

            override fun isEmptyData(check: Boolean) {
                TODO("Not yet implemented")
            }

        })
    }

}