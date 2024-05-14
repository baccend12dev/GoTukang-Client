package com.karyadi.skripsi.model

data class Success<T> (
    val status: String,
    val message: String,
    val data: T
)
