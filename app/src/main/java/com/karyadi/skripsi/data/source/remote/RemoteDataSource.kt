package com.karyadi.skripsi.data.source.remote

import android.content.Context
import android.util.Log
import com.karyadi.skripsi.data.DataSource
import com.karyadi.skripsi.data.source.ResponseCallback
import com.karyadi.skripsi.model.*
import com.karyadi.skripsi.utils.EspressoIdlingResource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(context: Context) : DataSource {

    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(context: Context): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(context).apply { instance = this }
            }
    }

    private val apiService = ApiConfig.getApiService(context)

    override fun getDataTukangNilai(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        EspressoIdlingResource.increment()
        apiService.getDataTukangNilai().subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriNilai>>() {
                override fun onSuccess(data: List<DetailKategoriNilai>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun getDataTukang(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        EspressoIdlingResource.increment()
        apiService.getDataTukang().subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriNilai>>() {
                override fun onSuccess(data: List<DetailKategoriNilai>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun getDataKategori(callback: ResponseCallback<List<DetailKategoriNilai>>) {
        EspressoIdlingResource.increment()
        apiService.getDataKategori().subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriNilai>>() {
                override fun onSuccess(data: List<DetailKategoriNilai>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    fun getDataPilihJenis(callback: ResponseCallback<List<PilihJenisKebutuhan>>) {
        EspressoIdlingResource.increment()
        apiService.getDataPilihJenis().subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<PilihJenisKebutuhan>>() {
                override fun onSuccess(data: List<PilihJenisKebutuhan>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun getDataPelangganById(
        data: Pelanggan,
        responseCallback: ResponseCallback<List<Pelanggan>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataPelangganById(data.id_pelanggan!!).enqueue(object : Callback<List<Pelanggan>>{
            override fun onResponse(call: Call<List<Pelanggan>>, response: Response<List<Pelanggan>>) {
                responseCallback.onHideProgress()
                responseCallback.onSuccess(response.body()!!)
                Log.d("Repsonse : ", data.toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<List<Pelanggan>>, t: Throwable) {
                responseCallback.onHideProgress()
                responseCallback.onFailed(500)
                EspressoIdlingResource.decrement()
            }

        })

    }

    override fun getDataTukangById(
        data: Tukang,
        responseCallback: ResponseCallback<List<Tukang>>) {
        EspressoIdlingResource.increment()
        apiService.getDataTukangById(data.id_tukang!!).enqueue(object : Callback<List<Tukang>>{
            override fun onResponse(call: Call<List<Tukang>>, response: Response<List<Tukang>>) {
                responseCallback.onHideProgress()
                responseCallback.onSuccess(response.body()!!)
                Log.d("Repsonse : ", data.toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<List<Tukang>>, t: Throwable) {
                responseCallback.onHideProgress()
                responseCallback.onFailed(500)
                EspressoIdlingResource.decrement()
            }

        })

    }

    override fun registerPelanggan(data: Pelanggan, responseCallback: ResponseCallback<Pelanggan>) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        var checkEmail = 0

        apiService.getPelanggan().enqueue(object : Callback<List<Pelanggan>> {
            override fun onResponse(
                call: Call<List<Pelanggan>>,
                response: Response<List<Pelanggan>>
            ) {
                for (i in response.body()?.indices!!) {
                    if (data.email_pelanggan.equals(response.body()!!.get(i).email_pelanggan)) {
                        checkEmail++
                    }
                }

                if (checkEmail != 0) {
                    responseCallback.onFailed(500, "Email Sudah Terdaftar")
                } else {
                    apiService.insertDataPelanggan(
                        nama_pelanggan = data.nama_pelanggan!!,
                        email_pelanggan = data.email_pelanggan!!,
                        password_pelanggan = data.password_pelanggan!!,
                        telp_pelanggan = data.telp_pelanggan!!,
                        latitude_pelanggan = data.latitude_pelanggan!!,
                        longitude_pelanggan = data.longitude_pelanggan!!,
                        alamat_pelanggan = data.alamat_pelanggan!!,
                        jk_pelanggan = data.jk_pelanggan!!,
                        foto_pelanggan = data.foto_pelanggan!!,
                    ).enqueue(object : Callback<Success<Pelanggan>> {
                        override fun onResponse(
                            call: Call<Success<Pelanggan>>,
                            response: Response<Success<Pelanggan>>
                        ) {
                            responseCallback.onHideProgress()
                            response.body()?.data?.let { responseCallback.onSuccess(it) }
                            Log.d("Repsonse Body", response.body().toString())
                            EspressoIdlingResource.decrement()
                        }

                        override fun onFailure(call: Call<Success<Pelanggan>>, t: Throwable) {
                            responseCallback.onHideProgress()
                            EspressoIdlingResource.decrement()
                            responseCallback.onFailed(500, t.localizedMessage)
                        }

                    })
                }

            }

            override fun onFailure(call: Call<List<Pelanggan>>, t: Throwable) {
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, "Gagal Mengecek Data Server")
            }

        })

    }

    override fun loginPelanggan(
        email: String,
        password: String,
        responseCallback: ResponseCallback<Pelanggan>
    ) {

        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.getPelanggan().enqueue(object : Callback<List<Pelanggan>> {
            override fun onResponse(
                call: Call<List<Pelanggan>>,
                response: Response<List<Pelanggan>>
            ) {

                val data = mutableListOf<Pelanggan>()
                response.body()?.let { data.addAll(it) }

                var valid = true

                for (i in data.indices) {
                    if (email.equals(data[i].email_pelanggan) && password.equals(data[i].password_pelanggan)) {
                        responseCallback.onSuccess(data[i])
                        valid = true
                        break

                    } else {
                        valid = false
                    }
                }

                if (!valid) {
                    responseCallback.onFailed(500, "Data yang anda masukkan tidak valid")
                }
            }

            override fun onFailure(call: Call<List<Pelanggan>>, t: Throwable) {
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, "Gagal Mengecek Data Server")
            }

        })
    }

    override fun registerTukang(data: Tukang, responseCallback: ResponseCallback<Tukang>) {

        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        var checkEmail = 0

        apiService.getTukang().enqueue(object : Callback<List<Tukang>> {
            override fun onResponse(
                call: Call<List<Tukang>>,
                response: Response<List<Tukang>>
            ) {
                for (i in response.body()?.indices!!) {
                    if (data.email_tukang.equals(response.body()!!.get(i).email_tukang)) {
                        checkEmail++
                    }
                }

                if (checkEmail != 0) {
                    responseCallback.onFailed(500, "Email Sudah Terdaftar")
                } else {
                    apiService.insertDataTukang(
                        nama_tukang = data.nama_tukang!!,
                        email_tukang = data.email_tukang!!,
                        password_tukang = data.password_tukang!!,
                        telp_tukang = data.telp_tukang!!,
                        latitude_tukang = data.latitude_tukang!!,
                        longitude_tukang = data.longitude_tukang!!,
                        alamat_tukang = data.alamat_tukang!!,
                        jangkauan_kategori_tukang = data.jangkauan_kategori_tukang!!,
                        foto_tukang = data.foto_tukang!!,
//                        hari_buka = data.hari_buka.toString(),
//                        jam_buka = data.jam_buka.toString(),
//                        jam_tutup = data.jam_tutup.toString(),
//                        keterangan_jasa = data.keterangan_jasa.toString(),
                        keterangan_jasa = data.keterangan_jasa!!,
                        nama_jasa = data.nama_jasa!!,
                        spesifikasi_tukang = data.spesifikasi_tukang!!,

                        ).enqueue(object : Callback<Success<Tukang>> {

                        override fun onResponse(
                            call: Call<Success<Tukang>>,
                            response: Response<Success<Tukang>>
                        ) {
                            responseCallback.onHideProgress()
                            response.body()?.data?.let { responseCallback.onSuccess(it) }
                            Log.d("Repsonse Body", response.body().toString())
                            EspressoIdlingResource.decrement()
                        }

                        override fun onFailure(call: Call<Success<Tukang>>, t: Throwable) {
                            responseCallback.onHideProgress()
                            EspressoIdlingResource.decrement()
                            responseCallback.onFailed(500, t.localizedMessage)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<List<Tukang>>, t: Throwable) {
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, "Gagal Mengecek Data Server")
            }

        })
    }

    override fun loginTukang(
        email: String,
        password: String,
        responseCallback: ResponseCallback<Tukang>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.getTukang().enqueue(object : Callback<List<Tukang>> {
            override fun onResponse(
                call: Call<List<Tukang>>,
                response: Response<List<Tukang>>
            ) {
                val data = mutableListOf<Tukang>()
                response.body()?.let { data.addAll(it) }

                var valid = true

                for (i in data.indices) {
                    if (email.equals(data[i].email_tukang) && password.equals(data[i].password_tukang)) {
                        responseCallback.onSuccess(data[i])
                        valid = true
                        break

                    } else {
                        valid = false
                    }
                }

                if (!valid) {
                    responseCallback.onFailed(500, "Data yang anda masukkan tidak valid")
                }
            }

            override fun onFailure(call: Call<List<Tukang>>, t: Throwable) {
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, "Gagal Mengecek Data Server")
            }

        })
    }

    override fun updatePelanggan(data: Pelanggan, responseCallback: ResponseCallback<Pelanggan>) {

        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.updateDataPelanggan(
            data.id_pelanggan!!,
            nama_pelanggan = data.nama_pelanggan!!,
            email_pelanggan = data.email_pelanggan!!,
            password_pelanggan = data.password_pelanggan!!,
            telp_pelanggan = data.telp_pelanggan!!,
            latitude_pelanggan = data.latitude_pelanggan!!,
            longitude_pelanggan = data.longitude_pelanggan!!,
            alamat_pelanggan = data.alamat_pelanggan!!,
            jk_pelanggan = data.jk_pelanggan!!,
            foto_pelanggan = data.foto_pelanggan!!,
        ).enqueue(object : Callback<Success<Pelanggan>> {
            override fun onResponse(
                call: Call<Success<Pelanggan>>,
                response: Response<Success<Pelanggan>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<Pelanggan>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun updateTukang(data: Tukang, responseCallback: ResponseCallback<Tukang>) {

        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.updateDataTukang(
            data.id_tukang!!,
            nama_tukang = data.nama_tukang!!,
            email_tukang = data.email_tukang!!,
            password_tukang = data.password_tukang!!,
            telp_tukang = data.telp_tukang!!,
            latitude_tukang = data.latitude_tukang!!,
            longitude_tukang = data.longitude_tukang!!,
            alamat_tukang = data.alamat_tukang!!,
            jangkauan_kategori_tukang = data.jangkauan_kategori_tukang!!,
            foto_tukang = data.foto_tukang!!,
//            hari_buka = data.hari_buka.toString(),
//            jam_buka = data.jam_buka.toString(),
//            jam_tutup = data.jam_tutup.toString(),
//            keterangan_jasa = data.keterangan_jasa.toString(),
            keterangan_jasa = data.keterangan_jasa!!,
            nama_jasa = data.nama_jasa!!,
            spesifikasi_tukang = data.spesifikasi_tukang!!,
        ).enqueue(object : Callback<Success<Tukang>> {
            override fun onResponse(
                call: Call<Success<Tukang>>,
                response: Response<Success<Tukang>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<Tukang>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun getListDetailKategori(
        data: Tukang,
        callback: ResponseCallback<List<DetailKategoriTukang>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDetailKategori(data.id_tukang!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriTukang>>() {
                override fun onSuccess(data: List<DetailKategoriTukang>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun getListDetailKategoriInPelanggan(
        data: DetailKategoriNilai,
        callback: ResponseCallback<List<DetailKategoriNilai>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDetailKategoriInPelanggan(data.id_tukang!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriNilai>>() {
                override fun onSuccess(data: List<DetailKategoriNilai>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun insertDataDetailKategoriTukang(
        data: DetailKategori,
        responseCallback: ResponseCallback<DetailKategori>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.insertDataDetailKategori(
            id_tukang = data.id_tukang!!,
            id_kategori = data.id_kategori!!,
            keterangan_kategori = data.keterangan_kategori!!,
            bahan_tukang = data.bahan_tukang!!,
            harga_bahan = data.harga_bahan,
            ongkos_tukang = data.ongkos_tukang,
            perkiraan_lama_waktu_pengerjaan = data.perkiraan_lama_waktu_pengerjaan!!,
        ).enqueue(object : Callback<Success<DetailKategori>> {
            override fun onResponse(
                call: Call<Success<DetailKategori>>,
                response: Response<Success<DetailKategori>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<DetailKategori>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })

    }

    override fun deleteDataDetailKategori(
        data: DetailKategoriTukang,
        responseCallback: ResponseCallback<Int>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.deleteDataDetailKategori(data.id_detail_kategori!!)
            .enqueue(object : Callback<Success<Int>> {
                override fun onResponse(
                    call: Call<Success<Int>>,
                    response: Response<Success<Int>>
                ) {
                    responseCallback.onHideProgress()
                    response.body()?.data?.let { responseCallback.onSuccess(it) }
                    Log.d("Repsonse Body", response.body().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<Success<Int>>, t: Throwable) {
                    responseCallback.onHideProgress()
                    EspressoIdlingResource.decrement()
                    responseCallback.onFailed(500, t.localizedMessage)
                }

            })
    }

    override fun updateDataDetailKategori(
        data: DetailKategori,
        responseCallback: ResponseCallback<DetailKategori>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.updateDataDetailKategori(
            data.id_detail_kategori!!,
            id_tukang = data.id_tukang!!,
            id_kategori = data.id_kategori!!,
            keterangan_kategori = data.keterangan_kategori!!,
            bahan_tukang = data.bahan_tukang!!,
            harga_bahan = data.harga_bahan,
            ongkos_tukang = data.ongkos_tukang,
            perkiraan_lama_waktu_pengerjaan = data.perkiraan_lama_waktu_pengerjaan!!,
        ).enqueue(object : Callback<Success<DetailKategori>> {
            override fun onResponse(
                call: Call<Success<DetailKategori>>,
                response: Response<Success<DetailKategori>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<DetailKategori>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })

    }

    override fun getDataTukangByKategori(
        data: DetailKategoriNilai,
        callback: ResponseCallback<List<DetailKategoriNilai>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataTukangByKategori(data.id_kategori!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<DetailKategoriNilai>>() {
                override fun onSuccess(data: List<DetailKategoriNilai>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }

            })
    }

    override fun getDataUkuran(callback: ResponseCallback<List<UkuranDetailKategori>>) {
        EspressoIdlingResource.increment()
        apiService.getDataUkuran()
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<UkuranDetailKategori>>() {
                override fun onSuccess(data: List<UkuranDetailKategori>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }

            })
    }

    override fun getUkuranByDetailKategori(
        data: UkuranDetailKategori,
        callback: ResponseCallback<List<UkuranDetailKategori>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getUkuranByDetailKategori(data.id_detail_kategori!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<UkuranDetailKategori>>() {
                override fun onSuccess(data: List<UkuranDetailKategori>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun insertDataUkuranDetailKategori(
        data: UkuranDetailKategori,
        responseCallback: ResponseCallback<UkuranDetailKategori>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.insertDataUkuranDetailKategori(
            id_detail_kategori = data.id_detail_kategori!!,
            id_ukuran = data.id_ukuran!!
        ).enqueue(object : Callback<Success<UkuranDetailKategori>> {
            override fun onResponse(
                call: Call<Success<UkuranDetailKategori>>,
                response: Response<Success<UkuranDetailKategori>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<UkuranDetailKategori>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun deleteDataUkuranDetailKategori(
        data: UkuranDetailKategori,
        responseCallback: ResponseCallback<ResponseDeleteSuccess>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.deleteDataUkuranDetailKategori(data.id_ukuran_detail_kategori!!)
            .enqueue(object : Callback<ResponseDeleteSuccess> {
                override fun onResponse(
                    call: Call<ResponseDeleteSuccess>,
                    response: Response<ResponseDeleteSuccess>
                ) {
                    responseCallback.onHideProgress()
                    response.body()?.let { responseCallback.onSuccess(it) }
                    EspressoIdlingResource.decrement()
                    Log.d("Repsonse Body", response.body().toString())
                }

                override fun onFailure(call: Call<ResponseDeleteSuccess>, t: Throwable) {
                    responseCallback.onHideProgress()
                    EspressoIdlingResource.decrement()
                    responseCallback.onFailed(500, t.localizedMessage)
                }
            })
    }

    override fun insertDataRating(data: Rating, responseCallback: ResponseCallback<Rating>) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.insertDataRating(
            id_tukang = data.id_tukang,
            kriteria_1 = data.kriteria_1,
            kriteria_2 = data.kriteria_2,
            kriteria_3 = data.kriteria_3,
            kriteria_4 = data.kriteria_4
        ).enqueue(object : Callback<Success<Rating>> {
            override fun onResponse(
                call: Call<Success<Rating>>,
                response: Response<Success<Rating>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<Rating>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })

    }

//    override fun getDataPesananById(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
//        EspressoIdlingResource.increment()
//        apiService.getDataPesananById(data.id_pesanan!!)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : ApiCallback<Pesanan>(){
//                override fun onSuccess(data: Pesanan) {
//                    responseCallback.onSuccess(data)
//                    EspressoIdlingResource.decrement()
//                }
//                override fun onFailure(code: Int, errorMessage: String) {
//                    responseCallback.onFailed(code, errorMessage)
//                    EspressoIdlingResource.decrement()
//                }
//
//            })
//
//    }

    override fun getDataPesananById(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        EspressoIdlingResource.increment()
        apiService.getDataPesananById(data.id_pesanan!!).enqueue(object : Callback<Pesanan> {
            override fun onFailure(call: Call<Pesanan>, t: Throwable) {
                responseCallback.onHideProgress()
                responseCallback.onSuccess(data)
                Log.d("Repsonse : ", data.toString())
                EspressoIdlingResource.decrement()
            }

            override fun onResponse(call: Call<Pesanan>, response: Response<Pesanan>) {
                responseCallback.onHideProgress()
                responseCallback.onFailed(500)
                EspressoIdlingResource.decrement()
            }
        })
    }

    override fun getDataDetailPesananById(
        data: Pesanan,
        responseCallback: ResponseCallback<List<DetailPesanan>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataDetailPesananById(data.id_pesanan!!).enqueue(object : Callback<List<DetailPesanan>>{
            override fun onResponse(call: Call<List<DetailPesanan>>, response: Response<List<DetailPesanan>>) {
                responseCallback.onHideProgress()
                responseCallback.onSuccess(response.body()!!)
                Log.d("Repsonse : ", data.toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<List<DetailPesanan>>, t: Throwable) {
                responseCallback.onHideProgress()
                responseCallback.onFailed(500)
                EspressoIdlingResource.decrement()
            }

        })

    }

    override fun getDataPesananByPelanggan(
        data: Pelanggan,
        callback: ResponseCallback<List<Pesanan>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataPesananByPelanggan(data.id_pelanggan!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<Pesanan>>() {
                override fun onSuccess(data: List<Pesanan>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }

            })
    }

    override fun getDataPesananByTukang(
        data: Tukang,
        callback: ResponseCallback<List<Pesanan>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataPesananByTukang(data.id_tukang!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<Pesanan>>() {
                override fun onSuccess(data: List<Pesanan>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }

            })
    }

    override fun insertDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.insertDataPesanan(
            id_pelanggan = data.id_pelanggan,
            id_tukang = data.id_tukang,
            id_detail_kategori = data.id_detail_kategori,
            tanggal_pesanan = data.tanggal_pesanan,
            tanggal_pesanan_selesai = data.tanggal_pesanan_selesai,
            lama_waktu_pengerjaan = data.lama_waktu_pengerjaan,
            catatan_pesanan = data.catatan_pesanan,
//            desain_tukang = data.desain_tukang,
//            bahan_tukang = data.bahan_tukang,
//            asal_bahan = data.asal_bahan,
//            panjang_bahan = data.panjang_bahan,
//            lebar_bahan = data.lebar_bahan,
//            status_bahan = data.status_bahan,
//            harga_bahan = data.harga_bahan,
            ongkos_tukang = data.ongkos_tukang,
            jumlah_tukang = data.jumlah_tukang,
//            biaya_tukang = data.biaya_tukang,
            total_biaya = data.total_biaya,
            status_pesanan = data.status_pesanan,
        ).enqueue(object : Callback<Success<Pesanan>> {
            override fun onResponse(
                call: Call<Success<Pesanan>>,
                response: Response<Success<Pesanan>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<Pesanan>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun updateDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.updateDataPesanan(
            data.id_pesanan,
            id_pelanggan = data.id_pelanggan,
            id_tukang = data.id_tukang,
            id_detail_kategori = data.id_detail_kategori,
            tanggal_pesanan = data.tanggal_pesanan,
            tanggal_pesanan_selesai = data.tanggal_pesanan_selesai,
            lama_waktu_pengerjaan = data.lama_waktu_pengerjaan,
            catatan_pesanan = data.catatan_pesanan,
//            desain_tukang = data.desain_tukang,
//            bahan_tukang = data.bahan_tukang,
//            asal_bahan = data.asal_bahan,
//            panjang_bahan = data.panjang_bahan,
//            lebar_bahan = data.lebar_bahan,
//            status_bahan = data.status_bahan,
//            harga_bahan = data.harga_bahan,
            ongkos_tukang = data.ongkos_tukang,
            jumlah_tukang = data.jumlah_tukang,
//            biaya_tukang = data.biaya_tukang,
            total_biaya = data.total_biaya,
            status_pesanan = data.status_pesanan,
        ).enqueue(object : Callback<Success<Pesanan>> {
            override fun onResponse(
                call: Call<Success<Pesanan>>,
                response: Response<Success<Pesanan>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<Pesanan>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun deleteDataPesanan(data: Pesanan, responseCallback: ResponseCallback<Pesanan>) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.deleteDataPesanan(data.id_pesanan!!)
            .enqueue(object : Callback<Success<Pesanan>> {
                override fun onResponse(
                    call: Call<Success<Pesanan>>,
                    response: Response<Success<Pesanan>>
                ) {
                    responseCallback.onHideProgress()
                    response.body()?.data?.let { responseCallback.onSuccess(it) }
                    Log.d("Repsonse Body", response.body().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<Success<Pesanan>>, t: Throwable) {
                    responseCallback.onHideProgress()
                    EspressoIdlingResource.decrement()
                    responseCallback.onFailed(500, t.localizedMessage)
                }

            })
    }

    override fun getDataUkuranByPesanan(
        data: Pesanan,
        callback: ResponseCallback<List<UkuranDetailPesanan>>
    ) {
        EspressoIdlingResource.increment()

        apiService.getDataUkuranByPesanan(data.id_pesanan!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<UkuranDetailPesanan>>() {
                override fun onSuccess(data: List<UkuranDetailPesanan>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }

            })

    }

    override fun getDataUkuranPesananByDetailKategori(
        data: Pesanan,
        callback: ResponseCallback<List<UkuranDetailPesanan>>
    ) {
        EspressoIdlingResource.increment()
        apiService.getDataUkuranPesananByDetailKategori(data.id_detail_kategori!!)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { callback.onShowProgress() }
            .doOnTerminate { callback.onHideProgress() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<List<UkuranDetailPesanan>>() {
                override fun onSuccess(data: List<UkuranDetailPesanan>) {
                    if (data.isNotEmpty()) {
                        callback.onSuccess(data)
                        callback.isEmptyData(false)
                    } else {
                        callback.isEmptyData(true)
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                    EspressoIdlingResource.decrement()
                }
            })
    }

    override fun insertDataUkuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.insertDataUkuranDetailPesanan(
            id_pesanan = data.id_pesanan,
            id_ukuran = data.id_ukuran,
            ukuran_pesanan = data.ukuran_pesanan,
        ).enqueue(object : Callback<Success<UkuranDetailPesanan>> {
            override fun onResponse(
                call: Call<Success<UkuranDetailPesanan>>,
                response: Response<Success<UkuranDetailPesanan>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<UkuranDetailPesanan>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })
    }

    override fun updateDatakuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.updateDataUkuranDetailPesanan(
            data.id_ukuran_detail_pesanan,
            id_pesanan = data.id_pesanan,
            id_ukuran = data.id_ukuran,
            ukuran_pesanan = data.ukuran_pesanan
        ).enqueue(object : Callback<Success<UkuranDetailPesanan>> {
            override fun onResponse(
                call: Call<Success<UkuranDetailPesanan>>,
                response: Response<Success<UkuranDetailPesanan>>
            ) {
                responseCallback.onHideProgress()
                response.body()?.data?.let { responseCallback.onSuccess(it) }
                Log.d("Repsonse Body", response.body().toString())
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<Success<UkuranDetailPesanan>>, t: Throwable) {
                responseCallback.onHideProgress()
                EspressoIdlingResource.decrement()
                responseCallback.onFailed(500, t.localizedMessage)
            }

        })

    }

    override fun deleteDatakuranDetailPesanan(
        data: UkuranDetailPesanan,
        responseCallback: ResponseCallback<UkuranDetailPesanan>
    ) {
        EspressoIdlingResource.increment()

        responseCallback.onShowProgress()

        apiService.deleteDataUkuranDetailPesanan(data.id_ukuran_detail_pesanan!!)
            .enqueue(object : Callback<Success<UkuranDetailPesanan>> {
                override fun onResponse(
                    call: Call<Success<UkuranDetailPesanan>>,
                    response: Response<Success<UkuranDetailPesanan>>
                ) {
                    responseCallback.onHideProgress()
                    response.body()?.data?.let { responseCallback.onSuccess(it) }
                    Log.d("Repsonse Body", response.body().toString())
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<Success<UkuranDetailPesanan>>, t: Throwable) {
                    responseCallback.onHideProgress()
                    EspressoIdlingResource.decrement()
                    responseCallback.onFailed(500, t.localizedMessage)
                }

            })
    }

}