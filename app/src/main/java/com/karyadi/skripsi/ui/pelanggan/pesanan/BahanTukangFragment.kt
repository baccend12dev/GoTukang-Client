package com.karyadi.skripsi.ui.pelanggan.pesanan

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.R
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.ui.pelanggan.pesanan.viewmodel.PesananViewModel
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.FragmentBahanTukangBinding
import java.text.SimpleDateFormat
import java.util.*

class BahanTukangFragment : Fragment() {

    private lateinit var binding: FragmentBahanTukangBinding
    val EXTRA_DATA_BAHAN_TUKANG = "EXTRA_DATA_BAHAN_TUKANG"
    val FIELD_REQUIRED = "Field tidak boleh kosong"
    lateinit var prefHelper: PrefHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBahanTukangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefHelper = PrefHelper(context)
        val idPelanggan = prefHelper.getString(PrefHelper.PREF_ID_PELANGGAN)
        val idPelangganInt : Int = idPelanggan!!.toInt()
        val namaPelanggan = prefHelper.getString(PrefHelper.PREF_NAMA_PELANGGAN)

        val dataPelanggan = Pelanggan(
            idPelangganInt, "", "","","","","","","",""
        )

        val bundleData = arguments?.getString(EXTRA_DATA_BAHAN_TUKANG)
        val data = Gson().fromJson(bundleData, DetailKategoriNilai::class.java)
        Log.d("Data hasil intent : ", data.toString())

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[PesananViewModel::class.java]

        viewModel.apply {
            dataPesanan.observe(this@BahanTukangFragment, {
//                val move = Intent(getActivity(), HomePelangganActivity::class.java)
//                move.putExtra("EXTRA_LOGIN_PELANGGAN", dataPelanggan)
//                getActivity()?.startActivity(move)
                getActivity()?.finish()
            })

            messageSuccess.observe(this@BahanTukangFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            messageFailed.observe(this@BahanTukangFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }

        val sdf = SimpleDateFormat("yyyy-M-dd")
        val currentDate = sdf.format(Date())
        binding.tvTanggalSelesai.text = currentDate

        binding.tvIdTukang.text = "id Tukang : " + data.id_tukang.toString()
        binding.tvNamaTukang.text = "Nama Tukang : " + data!!.nama_tukang
        binding.tvIdKategori.text = "id kategori : " + data.id_kategori.toString()
        binding.tvNamaKategori.text = "nama kategori : " + data.nama_kategori
        binding.tvIdDetailKategori.text = "id detail kategori : " + data.id_detail_kategori.toString()
        binding.tvIdPelanggan.text = "id pelanggan : " + idPelanggan
        binding.tvNamaPelanggan.text = "nama pelanggan : " + namaPelanggan
        binding.tvOngkosTukang.text = "ongkos tukang : " + data.ongkos_tukang
        binding.tvTotalBiaya.text = "total biaya = ongkos x jumlah = "
        binding.tvStatusBahan.text = "Belum di verifikasi"

        binding.btnPilihTanggal.setOnClickListener {

            val cal = Calendar.getInstance()
            val year = cal.get(Calendar.YEAR)
            val month = cal.get(Calendar.MONTH)
            val day = cal.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                val stringDate = "$year-${month+1}-$dayOfMonth"
                binding.tvTanggalSelesai.text = stringDate
            }, year, month, day)
            dpd.show()
        }

        binding.btnKirimData.setOnClickListener {
            popupKirimData(context, data)
        }

    }

    private fun popupKirimData(context: Context?, data: DetailKategoriNilai) {
        val box: Context = ContextThemeWrapper(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(box)
        materialAlertDialogBuilder.setTitle("Kirim Data")
            .setMessage("Apa Anda sudah yakin ingin melakukan pemesanan ini?")
            .setNegativeButton("Batalkan", null)
            .setPositiveButton("Kirim") { dialogInterface, i -> kirimData(data) }
            .show()

    }

    private fun kirimData(data: DetailKategoriNilai) {
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[PesananViewModel::class.java]

        prefHelper = PrefHelper(context)
        val idPelangganPH = prefHelper.getString(PrefHelper.PREF_ID_PELANGGAN)

        val idPelanggan : Int = idPelangganPH!!.toInt()

        val asalBahan = "Tukang"
        val statusBahan = "Bahan dari tukang, tolong dicarikan sesuai keinginan"
        val tanggalSelesai = binding.tvTanggalSelesai.text.toString().trim()
        val catatanPesanan = binding.etCatatanPesanan.text.toString().trim()
        val bahanTukang = binding.etBahanTukang.text.toString().trim()
        val panjangBahan = binding.etPanjangBahan.text.toString().trim()
        val lebarBahan = binding.etLebarBahan.text.toString().trim()
        val jumlahTukangET = binding.etJumlahTukang.text.toString().trim()
        val statusPesanan = "Belum diverifikasi"

        if (jumlahTukangET.isEmpty()){
            binding.etJumlahTukang.error = FIELD_REQUIRED
            return
        }

        val ongkosTukang = data.ongkos_tukang
        val jumlahTukang : Int = jumlahTukangET.toInt()

        val totalBiaya = jumlahTukang * ongkosTukang!!

        val dataPesanan = Pesanan(
            0,
            idPelanggan,
            data.id_tukang,
            data.id_detail_kategori,
            "",
            tanggalSelesai,
            "",
            catatanPesanan,
//            bahanTukang,
//            asalBahan,
//            panjangBahan,
//            lebarBahan,
//            statusBahan,
//            data.harga_bahan,
            ongkosTukang,
            jumlahTukang,
            totalBiaya,
            statusPesanan,
        )

        Log.d("Data Pesanan : ", dataPesanan.toString())
        viewModel.insertDataPesanan(dataPesanan)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BahanTukangFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}