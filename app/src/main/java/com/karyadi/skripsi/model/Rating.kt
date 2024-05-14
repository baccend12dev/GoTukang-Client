package com.karyadi.skripsi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rating(
    val id_rating: Int?,
    val id_tukang: Int?,
    val kriteria_1: Int?,
    val kriteria_2: Int?,
    val kriteria_3: Int?,
    val kriteria_4: Int?,
): Parcelable