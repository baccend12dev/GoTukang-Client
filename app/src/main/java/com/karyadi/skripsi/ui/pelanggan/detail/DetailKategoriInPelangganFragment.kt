package com.karyadi.skripsi.ui.pelanggan.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.karyadi.skripsi.databinding.FragmentDetailKategoriInPelangganBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.ui.pelanggan.pesanan.PesananActivity
import com.karyadi.skripsi.ui.pelanggan.pesanan.PesananActivity.Companion.EXTRA_DATA_DETAIL_KATEGORI_NILAI
import com.google.gson.Gson

class DetailKategoriInPelangganFragment : DialogFragment() {

    private lateinit var binding: FragmentDetailKategoriInPelangganBinding
    val EXTRA_DETAIL_KATEGORI = "EXTRA_DETAIL_KATEGORI"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailKategoriInPelangganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundleData = arguments?.getString(EXTRA_DETAIL_KATEGORI)
        val data = Gson().fromJson(bundleData, DetailKategoriNilai::class.java)

        binding.apply {

            tvNamaKategori.text = data.nama_kategori
            tvNamaTukang.text = data.nama_tukang
            tvIdTukang.text = data.id_tukang.toString()
//            tvBahanTukang.text = data.bahan_tukang
//            tvHargaBahan.text = data.harga_bahan.toString()
            tvOngkosTukang.text = data.ongkos_tukang.toString()
            tvLamaWaktu.text = data.perkiraan_lama_waktu_pengerjaan
            tvKet.text = data.keterangan_kategori

//            Glide.with(this@DetailKategoriInPelangganFragment)
//                .load("${Constant.IMAGE_KATEGORI}${data.gambar_kategori}")
//                .into(imgKategori)
        }

        binding.btnLakukanTukang.setOnClickListener {
            Toast.makeText(context, "Melakukan Pesanan" , Toast.LENGTH_SHORT).show()
            val moveIntent = Intent(context, PesananActivity::class.java)
            moveIntent.putExtra(EXTRA_DATA_DETAIL_KATEGORI_NILAI, data)
            startActivity(moveIntent)
        }

        binding.btnClose.setOnClickListener {
            dialog?.cancel()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailKategoriInPelangganFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}