package com.karyadi.skripsi.core

import androidx.lifecycle.ViewModel
import com.karyadi.skripsi.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {
    var eventShowProgress = SingleLiveEvent<Boolean>()
    var eventIsEmpty = SingleLiveEvent<Boolean>()
    var eventErrorMessage = SingleLiveEvent<String>()
}