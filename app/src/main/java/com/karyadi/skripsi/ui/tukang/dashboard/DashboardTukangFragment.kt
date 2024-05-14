package com.karyadi.skripsi.ui.tukang.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.detailkategoriInGridViewKategori.DetailKategoriFragment
import com.karyadi.skripsi.ui.tukang.dashboard.adapter.KategoriAdapter
import com.karyadi.skripsi.ui.tukang.dashboard.adapter.TukangAdapter
import com.karyadi.skripsi.ui.tukang.dashboard.viewmodel.DashboardTukangViewModel
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.FragmentDashboardTukangBinding

class DashboardTukangFragment : BaseFragment<FragmentDashboardTukangBinding>() {

    private lateinit var viewModel: DashboardTukangViewModel
    private var imageList = intArrayOf(R.drawable.img_slider1, R.drawable.img_slider2)
    private val dataTukang by lazy {
        baseGetInstance<Tukang>("EXTRA_TUKANG_DASHBOARD")
    }

    override fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentDashboardTukangBinding {
        return FragmentDashboardTukangBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        viewModel = obtainViewModel<DashboardTukangViewModel>().apply {

            listKategori.observe(viewLifecycleOwner, {
                setupRvKategori(it)
                binding.tvKategori.visibility = View.VISIBLE
            })

            listNilai.observe(viewLifecycleOwner, {
                //dataTukang adalah extraData Tukang dari login Tukang
                setupRvTukang(it, dataTukang)
                binding.tvRekomendasi.visibility = View.VISIBLE
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

        binding.tvKategori.visibility = View.INVISIBLE
        viewModel.getDataKategori()

        binding.tvRekomendasi.visibility = View.INVISIBLE
        viewModel.getDataTukang()
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewFlipper.setInAnimation(context, android.R.anim.slide_in_left)
            viewFlipper.setOutAnimation(context, android.R.anim.slide_out_right)
            for (image in imageList) {
                val imageView = ImageView(context)
                imageView.setImageResource(image)
                viewFlipper.addView(imageView)
                viewFlipper.flipInterval = 5000
                viewFlipper.isAutoStart = true
            }
        }
    }

    private fun setupRvKategori(data: List<DetailKategoriNilai>?) {
        val kategoriAdapter = KategoriAdapter()
        data?.let { kategoriAdapter.setKategori(it) }

        binding.rvKategori.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = kategoriAdapter
        }

        kategoriAdapter.setOnItemClickCallback(object : KategoriAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DetailKategoriNilai) {

                val detailKategoriFragment = DetailKategoriFragment()

                val bundle = Bundle()
                val bundleData = Gson().toJson(data)
                val bundleDataTukang = Gson().toJson(dataTukang)
                bundle.putString("EXTRA_TUKANG_BY_KATEGORI", bundleData)
                bundle.putString("EXTRA_TUKANG_BY_KATEGORI_EXTRA_TUKANG", bundleDataTukang)
                bundle.putString("EXTRA_ACCOUNT_STATUS", "status_tukang")
                detailKategoriFragment.arguments = bundle

                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container, detailKategoriFragment, DetailKategoriFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        })

    }

    private fun setupRvTukang(data: List<DetailKategoriNilai>, dataTukang: Tukang) {
        val tukangAdapter = TukangAdapter()
        tukangAdapter.setupDataTukang(dataTukang)
        tukangAdapter.setTukang(data)

        binding.rvTukang.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tukangAdapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardTukangFragment().apply {
                arguments = Bundle().apply {}
            }

    }
}