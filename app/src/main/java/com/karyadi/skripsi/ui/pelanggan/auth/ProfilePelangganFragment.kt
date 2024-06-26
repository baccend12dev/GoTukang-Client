package com.karyadi.skripsi.ui.pelanggan.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.karyadi.skripsi.databinding.FragmentProfilePelangganBinding
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.ui.main.PilihUserActivity
import com.karyadi.skripsi.ui.pelanggan.auth.viewmodel.AuthPelangganViewModel
import com.karyadi.skripsi.utils.Constant
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.gson.Gson

class ProfilePelangganFragment : Fragment() {

    private val factory by lazy {
        ViewModelFactory.getInstance(requireContext())
    }

    private val authPelangganViewModel by lazy {
        ViewModelProvider(this, factory)[AuthPelangganViewModel::class.java]
    }

    private lateinit var binding: FragmentProfilePelangganBinding
    lateinit var prefHelper: PrefHelper

    val EXTRA_PELANGGAN = "EXTRA_PELANGGAN"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePelangganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefHelper = PrefHelper(context)

        val bundleData = arguments?.getString(EXTRA_PELANGGAN)
        val dataPelanggan = Gson().fromJson(bundleData, Pelanggan::class.java)

        authPelangganViewModel.apply {
            dataPelangganVM.observe(viewLifecycleOwner, {
                binding.apply {
                    tvNamaPelanggan.text = it.nama_pelanggan
                    tvNamaPelanggan2.text = it.nama_pelanggan
                    tvAlamatPelanggan.text = it.alamat_pelanggan
                    tvEmailPelanggan.text = it.email_pelanggan
                    tvEmailPelanggan2.text = it.email_pelanggan
                    tvJkPelanggan.text = it.jk_pelanggan
                    tvTelepon.text = it.telp_pelanggan
                    tvLatPelanggan.text = it.latitude_pelanggan.toString()
                    tvLongPelanggan.text = it.longitude_pelanggan.toString()

                    if(it.foto_pelanggan != null){
                        Glide.with(requireContext())
                            .load("${Constant.IMAGE_PELANGGAN}${it.foto_pelanggan}")
                            .into(imgPelanggan)
                    }
                }
            })
        }
        authPelangganViewModel.getDataPelangganById(dataPelanggan)

//        binding.apply {
//            tvNamaPelanggan.text = dataPelanggan.nama_pelanggan
//            tvNamaPelanggan2.text = dataPelanggan.nama_pelanggan
//            tvAlamatPelanggan.text = dataPelanggan.alamat_pelanggan
//            tvEmailPelanggan.text = dataPelanggan.email_pelanggan
//            tvEmailPelanggan2.text = dataPelanggan.email_pelanggan
//            tvJkPelanggan.text = dataPelanggan.jk_pelanggan
//            tvTelepon.text = dataPelanggan.telp_pelanggan
//            tvLatPelanggan.text = dataPelanggan.latitude_pelanggan.toString()
//            tvLongPelanggan.text = dataPelanggan.longitude_pelanggan.toString()
//
//            context?.let {
//                Glide.with(it)
//                    .load("${Constant.IMAGE_PELANGGAN}${dataPelanggan.foto_pelanggan}")
//                    .into(imgPelanggan)
//            }
//        }

        binding.btnEditProfil.setOnClickListener {
            Toast.makeText(context, "Edit Data " + dataPelanggan.nama_pelanggan, Toast.LENGTH_SHORT)
                .show()
            val moveIntent = Intent(context, EditDataPelangganActivity::class.java)
            moveIntent.putExtra(EditDataPelangganActivity.EXTRA_DATA_PELANGGAN, dataPelanggan)
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
            ProfilePelangganFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}