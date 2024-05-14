package com.karyadi.skripsi.utils

import android.content.Context
import android.content.SharedPreferences

class PrefHelper(context: Context?) {

    companion object {
        val PREF_IS_LOGIN_PELANGGAN = "PREF_IS_LOGIN_PELANGGAN"
        val PREF_ID_PELANGGAN = "PREF_ID_PELANGGAN"
        val PREF_EMAIL_PELANGGAN = "PREF_EMAIL_PELANGGAN"
        val PREF_PASSWORD_PELANGGAN = "PREF_PASSWORD_PELANGGAN"
        val PREF_NAMA_PELANGGAN = "PREF_NAMA_PELANGGAN"
        val PREF_TELP_PELANGGAN = "PREF_TELP_PELANGGAN"
        val PREF_LATITUDE_PELANGGAN = "PREF_LATITUDE_PELANGGAN"
        val PREF_LONGITUDE_PELANGGAN = "PREF_LONGITUDE_PELANGGAN"
        val PREF_ALAMAT_PELANGGAN = "PREF_ALAMAT_PELANGGAN"
        val PREF_JK_PELANGGAN = "PREF_JK_PELANGGAN"
        val PREF_FOTO_PELANGGAN = "PREF_FOTO_PELANGGAN"

        val PREF_IS_LOGIN_TUKANG = "PREF_IS_LOGIN_TUKANG"
        val PREF_ID_TUKANG = "PREF_ID_TUKANG"
        val PREF_EMAIL_TUKANG = "PREF_EMAIL_TUKANG"
        val PREF_PASSWORD_TUKANG = "PREF_PASSWORD_TUKANG"
        val PREF_NAMA_TUKANG = "PREF_NAMA_TUKANG"
        val PREF_NAMA_TOKO_TUKANG = "PREF_NAMA_TOKO_TUKANG"
        val PREF_KET_TUKANG = "PREF_KET_TUKANG"
        val PREF_TELP_TUKANG = "PREF_TELP_TUKANG"
        val PREF_LATITUDE_TUKANG = "PREF_LATITUDE_TUKANG"
        val PREF_LONGITUDE_TUKANG = "PREF_LONGITUDE_TUKANG"
        val PREF_ALAMAT_TUKANG = "PREF_ALAMAT_TUKANG"
        val PREF_HARI_BUKA_TUKANG = "PREF_HARI_BUKA_TUKANG"
        val PREF_JAM_BUKA_TUKANG = "PREF_JAM_BUKA_TUKANG"
        val PREF_JAM_TUTUP_TUKANG = "PREF_JAM_TUTUP_TUKANG"
        val PREF_JANGKAUAN_TUKANG = "PREF_JANGKAUAN_TUKANG"
        val PREF_SPESIFIKASI_TUKANG = "PREF_SPESIFIKASI_TUKANG"
        val PREF_FOTO_TUKANG = "PREF_FOTO_TUKANG"
    }

    private val PREFS_NAME = "PrefHelpSkripsi"
    private var sharedPref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPref = context!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }

    fun put(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun put(key: String, value: Int) {
        editor.putInt(key, value)
            .apply()
    }

    fun put(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPref.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}