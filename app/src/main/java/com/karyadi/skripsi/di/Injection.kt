package com.karyadi.skripsi.di

import android.content.Context
import com.karyadi.skripsi.data.Repository
import com.karyadi.skripsi.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): Repository {
        val remoteDataSource = RemoteDataSource.getInstance(context)
        return Repository.getInstance(remoteDataSource)
    }

}