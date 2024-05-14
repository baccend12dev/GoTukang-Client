package com.karyadi.skripsi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseActivity
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.tukang.transaksi.TransaksiTukangFragment
import com.karyadi.skripsi.ui.tukang.dashboard.DashboardTukangFragment
import com.karyadi.skripsi.ui.tukang.kategori.KategoriTukangFragment
import com.karyadi.skripsi.ui.tukang.auth.ProfileTukangFragment
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.Constant
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.ActivityHomeTukangBinding

class HomeTukangActivity : BaseActivity<ActivityHomeTukangBinding>() {

    lateinit var prefHelper: PrefHelper

    companion object {
        const val EXTRA_LOGIN_TUKANG = "EXTRA_LOGIN_TUKANG"
    }

    private val factory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val authTukangViewModel by lazy {
        ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]
    }

    private val extraData: Tukang? by lazy {
        intent.getParcelableExtra(EXTRA_LOGIN_TUKANG)
    }

    override fun setupViewBinding(): ActivityHomeTukangBinding {
        return ActivityHomeTukangBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
        authTukangViewModel.apply {
            dataTukangVM.observe(this@HomeTukangActivity, {
                binding.apply {

                    tvUsername.text = it.nama_tukang

                    if(it.foto_tukang != null){
                        Glide.with(this@HomeTukangActivity)
                            .load("${Constant.IMAGE_TUKANG}${it!!.foto_tukang}")
                            .into(imgTukang)
                    }
                }
            })
        }

        authTukangViewModel.getDataTukangById(extraData!!)
    }

    override fun setupUI(savedInstanceState: Bundle?) {

        val dashboardTukangFragment = DashboardTukangFragment.newInstance()
        val transaksiTukangFragment = TransaksiTukangFragment.newInstance()
        val kategoriTukangFragment = KategoriTukangFragment.newInstance()
        val profileTukangFragment = ProfileTukangFragment.newInstance()

        prefHelper = PrefHelper(this)
//        val extraData: Tukang? = intent.extras?.getParcelable(EXTRA_LOGIN_TUKANG)

//        binding.tvUsername.text = extraData!!.nama_tukang
//        Glide.with(this)
//            .load("${Constant.IMAGE_TUKANG}${extraData.foto_tukang}")
//            .into(binding.imgTukang)

        val bundle = Bundle()
        val bundleData = Gson().toJson(extraData)
        bundle.putString("EXTRA_TUKANG", bundleData)
        profileTukangFragment.arguments = bundle
        transaksiTukangFragment.arguments = bundle

        dashboardTukangFragment.baseNewInstance("EXTRA_TUKANG_DASHBOARD", extraData)
        kategoriTukangFragment.baseNewInstance("EXTRA_TUKANG_KATEGORI", extraData)

        addFragment(dashboardTukangFragment)
        binding.apply {
            bottomNavigation.show(0)
            bottomNavigation.add(MeowBottomNavigation.Model(0, R.drawable.ic_home))
            bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_shopping_cart))
            bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_category))
            bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_profile))

            bottomNavigation.setOnClickMenuListener {
                when (it.id) {
                    0 -> {
                        replaceFragment(dashboardTukangFragment)
                    }
                    1 -> {
                        replaceFragment(transaksiTukangFragment)
                    }
                    2 -> {
                        replaceFragment(kategoriTukangFragment)
                    }
                    3 -> {
                        replaceFragment(profileTukangFragment)
                    }
                    else -> {
                        replaceFragment(dashboardTukangFragment)
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