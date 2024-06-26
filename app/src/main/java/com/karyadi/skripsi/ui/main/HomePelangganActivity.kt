package com.karyadi.skripsi.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseActivity
import com.karyadi.skripsi.databinding.ActivityHomePelangganBinding
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.ui.pelanggan.dashboard.DashboardPelangganFragment
import com.karyadi.skripsi.ui.pelanggan.auth.ProfilePelangganFragment
import com.karyadi.skripsi.ui.pelanggan.auth.viewmodel.AuthPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.transaksi.TransaksiPelangganFragment
import com.karyadi.skripsi.utils.Constant
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.gson.Gson
import com.karyadi.skripsi.ui.pelanggan.dashboard.DashboardPilihJenis

class HomePelangganActivity : BaseActivity<ActivityHomePelangganBinding>() {

    lateinit var prefHelper: PrefHelper

    companion object {
        const val EXTRA_LOGIN_PELANGGAN = "EXTRA_LOGIN_PELANGGAN"
    }

    private val factory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val authPelangganViewModel by lazy {
        ViewModelProvider(this, factory)[AuthPelangganViewModel::class.java]
    }

    private val extraData: Pelanggan? by lazy {
        intent.getParcelableExtra(EXTRA_LOGIN_PELANGGAN)
    }

    override fun setupViewBinding(): ActivityHomePelangganBinding {
        return ActivityHomePelangganBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        authPelangganViewModel.apply {
            dataPelangganVM.observe(this@HomePelangganActivity, {
                binding.apply {

                    tvUsername.text = it.nama_pelanggan

                    if(it.foto_pelanggan != null){
                        Glide.with(this@HomePelangganActivity)
                            .load("${Constant.IMAGE_PELANGGAN}${it!!.foto_pelanggan}")
                            .into(imgPelanggan)
                    }
                }
            })
        }

        authPelangganViewModel.getDataPelangganById(extraData!!)

    }

    override fun setupUI(savedInstanceState: Bundle?) {

//        val dashboardPelangganFragment = DashboardPelangganFragment()
        val dashboardPilihJenis         = DashboardPilihJenis.newInstance()
        val transaksiPelangganFragment = TransaksiPelangganFragment.newInstance()
        val profilePelangganFragment = ProfilePelangganFragment.newInstance()

        prefHelper = PrefHelper(this)
//        binding.tvUsername2.text = prefHelper.getString(PREF_NAMA_PELANGGAN)
//        binding.tvHi2.text = prefHelper.getString(PREF_ID_PELANGGAN)

//        val extraData: Pelanggan? = intent.extras?.getParcelable(EXTRA_LOGIN_PELANGGAN)

//        binding.tvUsername.text = extraData!!.nama_pelanggan
//        Glide.with(this)
//            .load("${Constant.IMAGE_PELANGGAN}${extraData!!.foto_pelanggan}")
//            .into(binding.imgPelanggan)

        val bundle = Bundle()
        val bundleData = Gson().toJson(extraData)
        bundle.putString("EXTRA_PELANGGAN", bundleData)
        transaksiPelangganFragment.arguments = bundle
        profilePelangganFragment.arguments = bundle

        dashboardPilihJenis.baseNewInstance("EXTRA_PELANGGAN_DASHBOARD",extraData)
        extraData!!.nama_pelanggan?.let { Log.d("EXTRA PELANGGAN DASH", it) }

        addFragment(dashboardPilihJenis)
        binding.apply {
            bottomNavigation.show(0)
            bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
            bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_shopping_cart))
            bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_profile))

            bottomNavigation.setOnClickMenuListener {
                when (it.id) {
                    0 -> {
                        replaceFragment(dashboardPilihJenis)
                    }
                    1 -> {
                        replaceFragment(transaksiPelangganFragment)
                    }
                    2 -> {
                        replaceFragment(profilePelangganFragment)
                    }
                    else -> {
                        replaceFragment(dashboardPilihJenis)
                    }
                }
            }
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, fragment)
            .addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_container, fragment)
            .addToBackStack(Fragment::class.java.simpleName).commit()
    }
}