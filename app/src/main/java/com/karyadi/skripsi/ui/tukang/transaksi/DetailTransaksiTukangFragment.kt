package com.karyadi.skripsi.ui.tukang.transaksi

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.databinding.FragmentDetailTransaksiTukangBinding
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.model.UkuranDetailPesanan
import com.karyadi.skripsi.ui.pelanggan.pesanan.viewmodel.PesananViewModel
import com.karyadi.skripsi.ui.pelanggan.transaksi.viewmodel.UkuranDetailPesananViewModel
import com.karyadi.skripsi.ui.tukang.transaksi.adapter.UkuranDetailPesananTukangAdapter
import java.net.URLEncoder

class DetailTransaksiTukangFragment : BaseFragment<FragmentDetailTransaksiTukangBinding>() {

    private lateinit var pesananViewModel: PesananViewModel
    private var wa_id : String? = null
    private var totalbiaya :String? =null
    private var namatukang : String? = null
    private var namapelanggan : String? = null
    private var kategori : String? = null
    private var keterangan : String? = null
    private var nomortelp : String? = null
    private var alamatpel : String? = null
    private var hargatukang : String? = null
    private var tanggalkerja: String? = null
    private var jumlahtukang: String? = null

    private lateinit var ukuranDetailPesananViewModel: UkuranDetailPesananViewModel
    private val extraDataPesanan by lazy{
        baseGetInstance<Pesanan>("DETAIL_PESANAN_TUKANG")
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailTransaksiTukangBinding {
        return FragmentDetailTransaksiTukangBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        pesananViewModel = obtainViewModel<PesananViewModel>().apply {

            dataDetailPesanan.observe(viewLifecycleOwner) {
                binding.apply {
                    tvIdPesanan.text = "Kode Pesanan : " + it.id_pesanan.toString()
                    tvIdTukang.text = "ID Tukang : " + it.id_tukang.toString()
                    tvIdPelanggan.text = "ID Pelanggan : " + it.id_pelanggan.toString()
                    tvIdDetailKategori.text =
                        "ID Detail Kategori : " + it.id_detail_kategori.toString()
                    tvNamaPelanggan3.text = "Nama Pelanggan : " + it.nama_pelanggan
                    tvTelpPelanggan.text = "Telp Pelanggan : " + it.telp_pelanggan
                    tvAlamatPelanggan.text = "Alamat Pelanggan : " + it.alamat_pelanggan
                    tvKategori.text = "Kategori : " + it.nama_kategori
                    tvTanggalPesanan.text = "Tanggal Pesanan : " + it.tanggal_pesanan
                    tvTanggalPesananSelesai.text =
                        "Tanggal Pesanan Selesai : " + it.tanggal_pesanan_selesai
                    tvCatatanPesanan.text = "Catatan Pesanam : " + it.catatan_pesanan
                    tvAsalBahan.text = "Asal Bahan : " + it.asal_bahan
                    tvStatusBahan.text = "Status Bahan : " + it.status_bahan

                    tvOngkosTukang.text = "Ongkos Tukang : " + it.ongkos_tukang
                    tvJumlahTukang.text = "Jumlah Tukang : " + it.jumlah_tukang
                    tvTotalBiaya.text = "Total Biaya : " + it.total_biaya
//                    tvKetPesanan.text = "Keterangan : " + it.lama_waktu_pengerjaan
//                    tvStatusPesanan.text = "Status Pesanan : " + it.status_pesanan
                    nomortelp = it.telp_pelanggan
                    wa_id = it.id_pesanan.toString()
                    totalbiaya = it.total_biaya.toString()
                    namapelanggan = it.nama_pelanggan
                    alamatpel = it.alamat_pelanggan
                    hargatukang = it.ongkos_tukang.toString()
                    namatukang = it.nama_tukang
                    kategori =it.nama_kategori
                    tanggalkerja=it.tanggal_pesanan_selesai
                    jumlahtukang =it.jumlah_tukang.toString()



                    val asalPelanggan = "Pelanggan"
                    if (it.asal_bahan.equals(asalPelanggan)) {
                        tvHargaBahan.visibility = View.GONE
                    } else {
                        tvHargaBahan.text = "Harga Bahan : " + it.harga_bahan
                    }
                }
            }
            getDataDetailPesananById(extraDataPesanan)


            dataPesanan.observe(viewLifecycleOwner, {
                binding.apply {
//                    tvIdPesanan.text = "Kode Pesanan : " + it.id_pesanan.toString()
//                    tvIdTukang.text = "ID Tukang : " + it.id_tukang.toString()
//                    tvIdPelanggan.text = "ID Pelanggan : " + it.id_pelanggan.toString()
//                    tvIdDetailKategori.text = "ID Detail Kategori : " + it.id_detail_kategori.toString()
//                    tvTanggalPesanan.text = "Tanggal Pesanan : " + it.tanggal_pesanan
//                    tvTanggalPesananSelesai.text = "Tanggal Pesanan Selesai : " + it.tanggal_pesanan_selesai
                    tvKetPesanan.text = "Keterangan : " + it.lama_waktu_pengerjaan
                    tvStatusPesanan.text = "Status Pesanan : " + it.status_pesanan
                }

                val statusBelumDiverifikasi = "Belum diverifikasi"
                val statusDiverifikasi = "Verifikasi"
                val statusTidakDiterima = "Tidak Diterima"
                val statusProses = "Sedang dikerjakan"
                val statusSelesai = "Selesai"

                val ketStatusDiverifikasi = "Selamat Pesanan Anda telah diverifikasi oleh Tukang, Silahkan masukkan ukuran"
                val ketStatusDiProses = "Pesanan Anda sedang diproses oleh Tukang"
                val ketStatusTidakDiterima = "Opss Maaf, Pesanan Anda tidak Diterima oleh Tukang, Tukang Tukang saat ini sedang penuh, cari yang lain"
                val ketStatusUkuranTelahDimasukkan = "Data Ukuran telah dimasukkan"


                val pesananDiTerima = Pesanan(
                    it.id_pesanan, it.id_pelanggan, it.id_tukang, it.id_detail_kategori, it.tanggal_pesanan, it.tanggal_pesanan_selesai,
                    ketStatusDiverifikasi,
                    it.catatan_pesanan,  it.ongkos_tukang, it.jumlah_tukang,  it.total_biaya,
                    statusDiverifikasi,
                )

                val pesananTidakDiterima = Pesanan(
                    it.id_pesanan, it.id_pelanggan, it.id_tukang, it.id_detail_kategori, it.tanggal_pesanan, it.tanggal_pesanan_selesai,
                    ketStatusTidakDiterima,
                    it.catatan_pesanan,  it.ongkos_tukang, it.jumlah_tukang,  it.total_biaya,
                    statusTidakDiterima,
                )

                val pesananDiProses = Pesanan(
                    it.id_pesanan, it.id_pelanggan, it.id_tukang, it.id_detail_kategori, it.tanggal_pesanan, it.tanggal_pesanan_selesai,
                    ketStatusDiProses,
                    it.catatan_pesanan,  it.ongkos_tukang, it.jumlah_tukang,  it.total_biaya,
                    statusProses,
                )

                val pesananSelesai = Pesanan(
                    it.id_pesanan, it.id_pelanggan, it.id_tukang, it.id_detail_kategori, it.tanggal_pesanan, it.tanggal_pesanan_selesai,
                    statusSelesai,
                    it.catatan_pesanan,  it.ongkos_tukang, it.jumlah_tukang,  it.total_biaya,
                    statusSelesai,
                )

                binding.btnTerima.setOnClickListener {
                    // Pemeriksaan nomor telepon null
                    if (nomortelp != null) {
                        Log.d("Pesanan Diterima : ", pesananDiTerima.toString())
                        pesananViewModel.updateDataPesanan(pesananDiTerima)
                    } else {
                        // Penanganan jika nomor telepon null
                        Log.e("Error", "Nomor telepon kosong atau tidak tersedia.")
                        // Tambahkan penanganan kesalahan jika nomor telepon kosong atau tidak tersedia
                        return@setOnClickListener
                    }

                    // Kode berikutnya hanya dijalankan jika nomor telepon tidak null
                    val phoneNumber = "+62$nomortelp"
                    val whatsappIntent = Intent(Intent.ACTION_VIEW)
                    val message = "Halo ini dari GoTukang, pesanan Anda telah diterima! Berikut detail pesanannya:\n"+
                            "ID Pesanan    : $wa_id\n" +
                            "Nama Pelanggan: $namapelanggan\n" +
                            "Nama Tukang   : $namatukang\n" +
                            "Kategori      : $kategori\n"+
                            "Alamat anda   : $alamatpel\n"+
                            "harga tukang  : $hargatukang\n"+
                            "Jumlah Tukang : $jumlahtukang\n"+
                            "Total Biaya   : $totalbiaya\n"+
                            "Tanggal untuk kerja : $tanggalkerja\n"+
                            "Tolong Konfirmasi Pesanan Anda Apakah Sudah Benar Atau Tidak"

                    // Detail pesanan lainnya...
                    whatsappIntent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${URLEncoder.encode(message, "UTF-8")}")
                    startActivity(whatsappIntent)
                }

                binding.btnTolak.setOnClickListener {
                    Log.d("Pesanan Ditolak : ", pesananTidakDiterima.toString())
                    pesananViewModel.updateDataPesanan(pesananTidakDiterima)
                }

                binding.btnSelesai.setOnClickListener {
                    Log.d("Pesanan Selesai : ", pesananSelesai.toString())
                    pesananViewModel.updateDataPesanan(pesananSelesai)
                }

                binding.btnProses.setOnClickListener {
                    Log.d("Pesanan Di Proses : ", pesananDiProses.toString())
                    pesananViewModel.updateDataPesanan(pesananDiProses)
                }


                if (it.status_pesanan.equals(statusBelumDiverifikasi)){
                    binding.layoutTerimaTolak.visibility = View.VISIBLE
                    binding.btnProses.visibility = View.INVISIBLE
                    binding.btnSelesai.visibility = View.INVISIBLE
                    binding.tvListUkuran.visibility = View.INVISIBLE
                }

                if (it.status_pesanan.equals(statusDiverifikasi)){
                    binding.layoutTerimaTolak.visibility = View.GONE
                    binding.btnProses.visibility = View.INVISIBLE
                    binding.btnSelesai.visibility = View.INVISIBLE
                    binding.tvListUkuran.visibility = View.INVISIBLE
                }

                if (it.lama_waktu_pengerjaan.equals(ketStatusUkuranTelahDimasukkan)){
                    binding.layoutTerimaTolak.visibility = View.GONE
                    binding.btnProses.visibility = View.VISIBLE
                    binding.btnSelesai.visibility = View.GONE
                    binding.tvListUkuran.visibility = View.VISIBLE
                }

                if (it.status_pesanan.equals(statusProses)){
                    binding.layoutTerimaTolak.visibility = View.GONE
                    binding.btnProses.visibility = View.GONE
                    binding.btnSelesai.visibility = View.VISIBLE
                    binding.tvListUkuran.visibility = View.VISIBLE
                }

                if (it.status_pesanan.equals(statusTidakDiterima)){
                    binding.layoutTerimaTolak.visibility = View.GONE
                    binding.btnProses.visibility = View.GONE
                    binding.btnSelesai.visibility = View.GONE
                    binding.tvListUkuran.visibility = View.GONE
                }

                if (it.status_pesanan.equals(statusSelesai)){
                    binding.layoutTerimaTolak.visibility = View.GONE
                    binding.btnProses.visibility = View.GONE
                    binding.btnSelesai.visibility = View.GONE
                    binding.tvListUkuran.visibility = View.VISIBLE
                }

                if (it.status_pesanan.equals(statusDiverifikasi)) {
                    binding.tvStatusPesanan.setTextColor(Color.GREEN)
                }

                if (it.status_pesanan.equals(statusProses)) {
                    binding.tvStatusPesanan.setTextColor(Color.GREEN)
                }

                if (it.status_pesanan.equals(statusTidakDiterima)) {
                    binding.tvStatusPesanan.setTextColor(Color.RED)
                }

                if (it.status_pesanan.equals(statusSelesai)) {
                    binding.tvStatusPesanan.setTextColor(Color.BLUE)
                }

            })

            eventShowProgress.observe(viewLifecycleOwner, {
                setupEventProgressView(binding.progressBar, it)
            })

            eventErrorMessage.observe(viewLifecycleOwner, {
                showToast(it)

            })

            eventIsEmpty.observe(viewLifecycleOwner, {
                // setupEventEmptyView(binding?.{EMPTY_VIEW MU}!! ,it)
            })
        }
        pesananViewModel.getDataPesananById(extraDataPesanan)

        ukuranDetailPesananViewModel = obtainViewModel<UkuranDetailPesananViewModel>().apply {
            listUkuranDetailPesanan.observe(viewLifecycleOwner, {
                setupRvUkuran(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, {
                setupEventProgressView(binding.progressBar, it)
            })

            eventErrorMessage.observe(viewLifecycleOwner, {
                showToast(it)

            })

            eventIsEmpty.observe(viewLifecycleOwner, {
                // setupEventEmptyView(binding?.{EMPTY_VIEW MU}!! ,it)
            })
        }
        ukuranDetailPesananViewModel.getDataUkuranByPesanan(extraDataPesanan)
        val listUkuran = ukuranDetailPesananViewModel.getDataUkuranByPesanan(extraDataPesanan)

        Log.d("data ukuran : ", listUkuran.toString())
    }

    private fun setupRvUkuran(data: List<UkuranDetailPesanan>?) {
        val ukuranDetailPesananTukangAdapter = UkuranDetailPesananTukangAdapter()
        ukuranDetailPesananTukangAdapter.setUkuranPesanan(data!!)

        binding.rvUkuran.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ukuranDetailPesananTukangAdapter
        }
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailTransaksiTukangFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}