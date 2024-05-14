package com.karyadi.skripsi.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.di.Injection
import com.karyadi.skripsi.ui.pelanggan.auth.viewmodel.AuthPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.dashboard.viewmodel.DashboardPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.detail.viewmodel.KategoriTukangInPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.pesanan.viewmodel.PesananViewModel
import com.karyadi.skripsi.ui.pelanggan.transaksi.viewmodel.UkuranDetailPesananViewModel
import com.karyadi.skripsi.ui.pelanggan.rating.viewmodel.RatingTukangViewModel
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.ui.tukang.dashboard.viewmodel.DashboardTukangViewModel
import com.karyadi.skripsi.ui.tukang.kategori.viewmodel.KategoriTukangViewModel
import com.karyadi.skripsi.ui.tukang.ukuran.viewmodel.UkuranViewModel

class ViewModelFactory private constructor(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) : T{
        when{
            modelClass.isAssignableFrom(DashboardTukangViewModel::class.java) -> {
                return DashboardTukangViewModel(repository) as T
            }

            modelClass.isAssignableFrom(DashboardPelangganViewModel::class.java) -> {
                return DashboardPelangganViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AuthPelangganViewModel::class.java) -> {
                return AuthPelangganViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AuthTukangViewModel::class.java) -> {
                return AuthTukangViewModel(repository) as T
            }

            modelClass.isAssignableFrom(KategoriTukangViewModel::class.java) -> {
                return KategoriTukangViewModel(repository) as T
            }

            modelClass.isAssignableFrom(UkuranViewModel::class.java) -> {
                return UkuranViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RatingTukangViewModel::class.java) -> {
                return RatingTukangViewModel(repository) as T
            }

            modelClass.isAssignableFrom(KategoriTukangInPelangganViewModel::class.java) -> {
                return KategoriTukangInPelangganViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PesananViewModel::class.java) -> {
                return PesananViewModel(repository) as T
            }

            modelClass.isAssignableFrom(UkuranDetailPesananViewModel::class.java) -> {
                return UkuranDetailPesananViewModel(repository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}