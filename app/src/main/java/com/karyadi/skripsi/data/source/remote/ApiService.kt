package com.karyadi.skripsi.data.source.remote

import com.karyadi.skripsi.model.*
import com.karyadi.skripsi.utils.Constant.URL_DETAIL_KATEGORI_DELETE
import com.karyadi.skripsi.utils.Constant.URL_DETAIL_KATEGORI_GET_BY_KATEGORI
import com.karyadi.skripsi.utils.Constant.URL_DETAIL_KATEGORI_GET_BY_TUKANG
import com.karyadi.skripsi.utils.Constant.URL_DETAIL_KATEGORI_INSERT
import com.karyadi.skripsi.utils.Constant.URL_DETAIL_KATEGORI_UPDATE
import com.karyadi.skripsi.utils.Constant.URL_KATEGORI_GET
import com.karyadi.skripsi.utils.Constant.URL_PELANGGAN_GET
import com.karyadi.skripsi.utils.Constant.URL_PELANGGAN_GET_BY_ID
import com.karyadi.skripsi.utils.Constant.URL_PELANGGAN_INSERT
import com.karyadi.skripsi.utils.Constant.URL_PELANGGAN_UPDATE
import com.karyadi.skripsi.utils.Constant.URL_TUKANG_GET
import com.karyadi.skripsi.utils.Constant.URL_TUKANG_GET_BY_ID
import com.karyadi.skripsi.utils.Constant.URL_TUKANG_GET_BY_NILAI
import com.karyadi.skripsi.utils.Constant.URL_TUKANG_INSERT
import com.karyadi.skripsi.utils.Constant.URL_TUKANG_UPDATE
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_DELETE
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_GET_BY_ID
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_GET_BY_PELANGGAN
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_GET_BY_TUKANG
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_INSERT
import com.karyadi.skripsi.utils.Constant.URL_PESANAN_UPDATE
import com.karyadi.skripsi.utils.Constant.URL_PILIH_JENIS
import com.karyadi.skripsi.utils.Constant.URL_RATING_INSERT
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_KATEGORI_DELETE
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_KATEGORI_GET_BY_DETAIL_KATEGORI
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_KATEGORI_INSERT
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_PESANAN_DELETE
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_PESANAN_GET_BY_PESANAN
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_PESANAN_INSERT
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_DETAIL_PESANAN_UPDATE
import com.karyadi.skripsi.utils.Constant.URL_UKURAN_GET
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET(URL_TUKANG_GET_BY_NILAI)
    fun getDataTukangNilai() : Observable<List<DetailKategoriNilai>>

    @GET(URL_TUKANG_GET)
    fun getDataTukang() : Observable<List<DetailKategoriNilai>>

    @GET(URL_KATEGORI_GET)
    fun getDataKategori() : Observable<List<DetailKategoriNilai>>

    @GET(URL_PILIH_JENIS)
    fun getDataPilihJenis() : Observable<List<PilihJenisKebutuhan>>

    @GET(URL_PELANGGAN_GET)
    fun getPelanggan(): Call<List<Pelanggan>>

    @GET(URL_TUKANG_GET)
    fun getTukang(): Call<List<Tukang>>

    @GET(URL_PELANGGAN_GET_BY_ID)
    fun getDataPelangganById(
        @Path("id_pelanggan") id_pelanggan_path: Int
    ): Call<List<Pelanggan>>

    @GET(URL_TUKANG_GET_BY_ID)
    fun getDataTukangById(
        @Path("id_tukang") id_tukang_path: Int
    ): Call<List<Tukang>>

    @FormUrlEncoded
    @POST(URL_PELANGGAN_INSERT)
    fun insertDataPelanggan(
        @Field("namaPelanggan") nama_pelanggan: String,
        @Field("emailPelanggan") email_pelanggan: String,
        @Field("passwordPelanggan") password_pelanggan: String,
        @Field("telpPelanggan") telp_pelanggan: String,
        @Field("latitudePelanggan") latitude_pelanggan: String,
        @Field("longitudePelanggan") longitude_pelanggan: String,
        @Field("alamatPelanggan") alamat_pelanggan: String,
        @Field("jkPelanggan") jk_pelanggan: String,
        @Field("foto_pelanggan") foto_pelanggan: String,

        ): Call<Success<Pelanggan>>


    @FormUrlEncoded
    @POST(URL_PELANGGAN_UPDATE)
    fun updateDataPelanggan(
        @Path("id_pelanggan") id_pelanggan_path: Int,
        @Field("namaPelanggan") nama_pelanggan: String,
        @Field("emailPelanggan") email_pelanggan: String,
        @Field("passwordPelanggan") password_pelanggan: String,
        @Field("telpPelanggan") telp_pelanggan: String,
        @Field("latitudePelanggan") latitude_pelanggan: String,
        @Field("longitudePelanggan") longitude_pelanggan: String,
        @Field("alamatPelanggan") alamat_pelanggan: String,
        @Field("jkPelanggan") jk_pelanggan: String,
        @Field("foto_pelanggan") foto_pelanggan: String,

        ): Call<Success<Pelanggan>>


    @FormUrlEncoded
    @POST(URL_TUKANG_INSERT)
    fun insertDataTukang(
        @Field("namaTukang") nama_tukang: String,
        @Field("emailTukang") email_tukang: String,
        @Field("passwordTukang") password_tukang: String,
        @Field("telpTukang") telp_tukang: String,
        @Field("namaJasa") nama_jasa: String,
        @Field("keteranganJasa") keterangan_jasa: String,
        @Field("latitudeTukang") latitude_tukang: String,
        @Field("longitudeTukang") longitude_tukang: String,
        @Field("alamatTukang") alamat_tukang: String,
        @Field("spesifikasiTukang") spesifikasi_tukang: String,
        @Field("jangkauanKategoriTukang") jangkauan_kategori_tukang: String,
//        @Field("hariBuka") hari_buka: String,
//        @Field("jamBuka") jam_buka: Any,
//        @Field("jamTutup") jam_tutup: String,
        @Field("foto_tukang") foto_tukang: String,

        ): Call<Success<Tukang>>


    @FormUrlEncoded
    @POST(URL_TUKANG_UPDATE)
    fun updateDataTukang(
        @Path("id_tukang") id_tukang_path: Int,
        @Field("namaTukang") nama_tukang: String,
        @Field("emailTukang") email_tukang: String,
        @Field("passwordTukang") password_tukang: String,
        @Field("telpTukang") telp_tukang: String,
        @Field("namaJasa") nama_jasa: String,
        @Field("keteranganJasa") keterangan_jasa: String,
        @Field("latitudeTukang") latitude_tukang: String,
        @Field("longitudeTukang") longitude_tukang: String,
        @Field("alamatTukang") alamat_tukang: String,
        @Field("spesifikasiTukang") spesifikasi_tukang: String,
        @Field("jangkauanKategoriTukang") jangkauan_kategori_tukang: String,
//        @Field("hariBuka") hari_buka: String,
//        @Field("jamBuka") jam_buka: Any,
//        @Field("jamTutup") jam_tutup: String,
        @Field("foto_tukang") foto_tukang: String,

        ): Call<Success<Tukang>>


    @GET(URL_DETAIL_KATEGORI_GET_BY_TUKANG)
    fun getDetailKategori(
        @Path("id_tukang") id_tukang_path: Int
    ): Observable<List<DetailKategoriTukang>>


    @GET(URL_DETAIL_KATEGORI_GET_BY_TUKANG)
    fun getDetailKategoriInPelanggan(
        @Path("id_tukang") id_tukang_path: Int
    ): Observable<List<DetailKategoriNilai>>


    @FormUrlEncoded
    @POST(URL_DETAIL_KATEGORI_INSERT)
    fun insertDataDetailKategori(
        @Field("idTukang") id_tukang: Int,
        @Field("idKategori") id_kategori: Int,
        @Field("keteranganKategori") keterangan_kategori: String,
        @Field("bahanTukang") bahan_tukang: String,
        @Field("hargaBahan") harga_bahan: String,
        @Field("ongkosTukang") ongkos_tukang: String,
        @Field("perkiraanLamaWaktuPengerjaan") perkiraan_lama_waktu_pengerjaan: String,

        ): Call<Success<DetailKategori>>


    @POST(URL_DETAIL_KATEGORI_DELETE)
    fun deleteDataDetailKategori(
        @Path("id_detail_kategori") id_detail_kategori_path: Int
    ): Call<Success<Int>>


    @FormUrlEncoded
    @POST(URL_DETAIL_KATEGORI_UPDATE)
    fun updateDataDetailKategori(
        @Path("id_detail_kategori") id_detail_kategori_path: Int,
        @Field("idTukang") id_tukang: Int,
        @Field("idKategori") id_kategori: Int,
        @Field("keteranganKategori") keterangan_kategori: String,
        @Field("bahanTukang") bahan_tukang: String,
        @Field("hargaBahan") harga_bahan: String?,
        @Field("ongkosTukang") ongkos_tukang: String?,
        @Field("perkiraanLamaWaktuPengerjaan") perkiraan_lama_waktu_pengerjaan: String,

        ): Call<Success<DetailKategori>>


    @GET(URL_DETAIL_KATEGORI_GET_BY_KATEGORI)
    fun getDataTukangByKategori(
        @Path("id_kategori") id_kategori_path: Int
    ): Observable<List<DetailKategoriNilai>>


    @GET(URL_UKURAN_GET)
    fun getDataUkuran() : Observable<List<UkuranDetailKategori>>


    @GET(URL_UKURAN_DETAIL_KATEGORI_GET_BY_DETAIL_KATEGORI)
    fun getUkuranByDetailKategori(
        @Path("id_detail_kategori") id_detail_kategori_path: Int
    ): Observable<List<UkuranDetailKategori>>


    @FormUrlEncoded
    @POST(URL_UKURAN_DETAIL_KATEGORI_INSERT)
    fun insertDataUkuranDetailKategori(
        @Field("idDetailKategori") id_detail_kategori: Int,
        @Field("idUkuran") id_ukuran: Int,

        ): Call<Success<UkuranDetailKategori>>


    @POST(URL_UKURAN_DETAIL_KATEGORI_DELETE)
    fun deleteDataUkuranDetailKategori(
        @Path("id_ukuran_detail_kategori") id_ukuran_detail_kategori_path: Int
    ): Call<ResponseDeleteSuccess>


    @FormUrlEncoded
    @POST(URL_RATING_INSERT)
    fun insertDataRating(
        @Field("idTukang") id_tukang: Int?,
        @Field("kriteria1") kriteria_1: Int?,
        @Field("kriteria2") kriteria_2: Int?,
        @Field("kriteria3") kriteria_3: Int?,
        @Field("kriteria4") kriteria_4: Int?,

        ): Call<Success<Rating>>


//    @GET(URL_PESANAN_GET_BY_ID)
//    fun getDataPesananById(
//        @Path("id_pesanan") id_pesanan_path: Int
//    ): Observable<Pesanan>

    @GET(URL_PESANAN_GET_BY_ID)
    fun getDataPesananById(
        @Path("id_pesanan") id_pesanan_path: Int
    ): Call<Pesanan>


    @GET(URL_PESANAN_GET_BY_ID)
    fun getDataDetailPesananById(
        @Path("id_pesanan") id_pesanan_path: Int
    ): Call<List<DetailPesanan>>


    @GET(URL_PESANAN_GET_BY_PELANGGAN)
    fun getDataPesananByPelanggan(
        @Path("id_pelanggan") id_pelanggan_path: Int
    ): Observable<List<Pesanan>>


    @GET(URL_PESANAN_GET_BY_TUKANG)
    fun getDataPesananByTukang(
        @Path("id_tukang") id_tukang_path: Int
    ): Observable<List<Pesanan>>


    @FormUrlEncoded
    @POST(URL_PESANAN_INSERT)
    fun insertDataPesanan(
        @Field("idPelanggan") id_pelanggan: Int?,
        @Field("idTukang") id_tukang: Int?,
        @Field("idDetailKategori") id_detail_kategori: Int?,
        @Field("tanggalPesanan") tanggal_pesanan: String?,
        @Field("tanggalPesananSelesai") tanggal_pesanan_selesai: String?,
        @Field("lamaWaktuPengerjaan") lama_waktu_pengerjaan: String?,
        @Field("catatanPesanan") catatan_pesanan: String?,
//        @Field("desainTukang") desain_tukang: String?,
//        @Field("bahanTukang") bahan_tukang: String?,
//        @Field("asalBahan") asal_bahan: String?,
//        @Field("panjangBahan") panjang_bahan: String?,
//        @Field("lebarBahan") lebar_bahan: String?,
//        @Field("statusBahan") status_bahan: String?,
//        @Field("hargaBahan") harga_bahan: Int?,
        @Field("ongkosTukang") ongkos_tukang: Int?,
        @Field("jumlahTukang") jumlah_tukang: Int?,
//        @Field("biayaTukang") biaya_tukang: Int?,
        @Field("totalBiaya") total_biaya: Int?,
        @Field("statusPesanan") status_pesanan: String?,

        ): Call<Success<Pesanan>>


    @FormUrlEncoded
    @POST(URL_PESANAN_UPDATE)
    fun updateDataPesanan(
        @Path("id_pesanan") id_pesanan_path: Int?,
        @Field("idPelanggan") id_pelanggan: Int?,
        @Field("idTukang") id_tukang: Int?,
        @Field("idDetailKategori") id_detail_kategori: Int?,
        @Field("tanggalPesanan") tanggal_pesanan: String?,
        @Field("tanggalPesananSelesai") tanggal_pesanan_selesai: String?,
        @Field("lamaWaktuPengerjaan") lama_waktu_pengerjaan: String?,
        @Field("catatanPesanan") catatan_pesanan: String?,
//        @Field("desainTukang") desain_tukang: String?,
//        @Field("bahanTukang") bahan_tukang: String?,
//        @Field("asalBahan") asal_bahan: String?,
//        @Field("panjangBahan") panjang_bahan: String?,
//        @Field("lebarBahan") lebar_bahan: String?,
//        @Field("statusBahan") status_bahan: String?,
//        @Field("hargaBahan") harga_bahan: Int?,
        @Field("ongkosTukang") ongkos_tukang: Int?,
        @Field("jumlahTukang") jumlah_tukang: Int?,
//        @Field("biayaTukang") biaya_tukang: Int?,
        @Field("totalBiaya") total_biaya: Int?,
        @Field("statusPesanan") status_pesanan: String?,

        ): Call<Success<Pesanan>>


    @POST(URL_PESANAN_DELETE)
    fun deleteDataPesanan(
        @Path("id_pesanan") id_pesanan_path: Int
    ): Call<Success<Pesanan>>


    @GET(URL_UKURAN_DETAIL_PESANAN_GET_BY_PESANAN)
    fun getDataUkuranByPesanan(
        @Path("id_pesanan") id_pesanan_path: Int
    ): Observable<List<UkuranDetailPesanan>>


    @GET(URL_UKURAN_DETAIL_KATEGORI_GET_BY_DETAIL_KATEGORI)
    fun getDataUkuranPesananByDetailKategori(
        @Path("id_detail_kategori") id_detail_kategori_path: Int
    ): Observable<List<UkuranDetailPesanan>>


    @FormUrlEncoded
    @POST(URL_UKURAN_DETAIL_PESANAN_INSERT)
    fun insertDataUkuranDetailPesanan(
        @Field("idPesanan") id_pesanan: Int?,
        @Field("idUkuran") id_ukuran: Int?,
        @Field("ukuranPesanan") ukuran_pesanan: Int?,

        ): Call<Success<UkuranDetailPesanan>>


    @FormUrlEncoded
    @POST(URL_UKURAN_DETAIL_PESANAN_UPDATE)
    fun updateDataUkuranDetailPesanan(
        @Path("id_ukuran_detail_pesanan") id_ukuran_detail_pesanan_path: Int?,
        @Field("idPesanan") id_pesanan: Int?,
        @Field("idUkuran") id_ukuran: Int?,
        @Field("ukuranPesanan") ukuran_pesanan: Int?,

        ): Call<Success<UkuranDetailPesanan>>


    @POST(URL_UKURAN_DETAIL_PESANAN_DELETE)
    fun deleteDataUkuranDetailPesanan(
        @Path("id_ukuran_detail_pesanan") id_ukuran_detail_pesanan_path: Int
    ): Call<Success<UkuranDetailPesanan>>
}