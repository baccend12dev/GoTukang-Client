package com.karyadi.skripsi.ui.tukang.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.main.PilihUserActivity
import com.karyadi.skripsi.ui.tukang.auth.EditDataTukangActivity.Companion.EXTRA_DATA_TUKANG
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.Constant
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.FragmentProfileTukangBinding
class ProfileTukangFragment : Fragment() {

    private lateinit var binding: FragmentProfileTukangBinding
    lateinit var prefHelper: PrefHelper

    val EXTRA_TUKANG = "EXTRA_TUKANG"

    private val factory by lazy {
        ViewModelFactory.getInstance(requireContext())
    }

    private val authTukangViewModel by lazy {
        ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileTukangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        supportActionBar?.title = "Data Tukang"
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        prefHelper = PrefHelper(context)

        val bundleData = arguments?.getString(EXTRA_TUKANG)
        val dataTukang = Gson().fromJson(bundleData, Tukang::class.java)

        authTukangViewModel.apply {
            dataTukangVM.observe(viewLifecycleOwner, {
                binding.apply {

                    tvNamaToko.text = it.nama_jasa
                    tvNamaToko2.text = it.nama_jasa
                    tvNamaTukang.text = it.nama_tukang
                    tvNamaTukang2.text = it.nama_tukang
                    tvEmailTukang.text = it.email_tukang
                    tvTelepon.text = it.telp_tukang
                    tvAlamatTukang.text = it.alamat_tukang
                    tvKetToko.text = it.keterangan_jasa
                    tvSpesifikasi.text = it.spesifikasi_tukang
                    tvJangkauan.text = it.jangkauan_kategori_tukang
                    tvLatitude.text = it.latitude_tukang
                    tvLongitude.text = it.longitude_tukang
//                    tvHariBuka.text = it.hari_buka
//                    tvJamBuka.text = it.jam_buka
//                    tvJamTutup.text = it.jam_tutup

                    if(it.foto_tukang != null){
                        Glide.with(requireContext())
                            .load("${Constant.IMAGE_TUKANG}${it.foto_tukang}")
                            .into(imgTukang)
                    }

                }
            })
        }
        authTukangViewModel.getDataTukangById(dataTukang)

//        binding.apply {
//            tvNamaToko.text = dataTukang.nama_jasa
//            tvNamaToko2.text = dataTukang.nama_jasa
//            tvNamaTukang.text = dataTukang.nama_tukang
//            tvNamaTukang2.text = dataTukang.nama_tukang
//            tvEmailTukang.text = dataTukang.email_tukang
//            tvTelepon.text = dataTukang.telp_tukang
//            tvAlamatTukang.text = dataTukang.alamat_tukang
//            tvKetToko.text = dataTukang.keterangan_jasa
//            tvSpesifikasi.text = dataTukang.spesifikasi_tukang
//            tvJangkauan.text = dataTukang.jangkauan_kategori_tukang
//            tvLatitude.text = dataTukang.latitude_tukang
//            tvLongitude.text = dataTukang.longitude_tukang
//            tvHariBuka.text = dataTukang.hari_buka
//            tvJamBuka.text = dataTukang.jam_buka
//            tvJamTutup.text = dataTukang.jam_tutup
//
//            context?.let {
//                Glide.with(it)
//                    .load("${Constant.IMAGE_TUKANG}${dataTukang.foto_tukang}")
//                    .into(imgTukang)
//            }
//
////            if(dataTukang!!.nilai_akhir != null){
////                val df = DecimalFormat("#.#")
////                val extraRating = extraDataNilai!!.nilai_akhir
////                val rating = df.format(extraRating)
////                contentBinding.tvRating.text = rating.toString()
////            }
////            else{
////                contentBinding.tvRating.text = "Belum ada penilaian"
////            }
//        }

        binding.btnEditProfil.setOnClickListener {
            val moveIntent = Intent(context, EditDataTukangActivity::class.java)
            moveIntent.putExtra(EXTRA_DATA_TUKANG, dataTukang)
            startActivity(moveIntent)
        }

        binding.btnLogout.setOnClickListener {
            prefHelper.clear()
            Toast.makeText(context, "Keluar", Toast.LENGTH_SHORT).show()
            val moveIntent = Intent(context, PilihUserActivity::class.java)
            startActivity(moveIntent)
            activity?.finish()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileTukangFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}