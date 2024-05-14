package com.karyadi.skripsi.ui.pelanggan.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.databinding.FragmentDashboardPelangganBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.ui.detailkategoriInGridViewKategori.DetailKategoriFragment
import com.karyadi.skripsi.ui.pelanggan.dashboard.adapter.KategoriTukangAdapter
import com.karyadi.skripsi.ui.pelanggan.dashboard.adapter.RekomendasiTukangAdapter
import com.karyadi.skripsi.ui.pelanggan.dashboard.viewmodel.DashboardPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.detail.DetailTukangPelangganActivity
import com.google.gson.Gson

class DashboardPelangganFragment : BaseFragment<FragmentDashboardPelangganBinding>() {

    private lateinit var viewModel: DashboardPelangganViewModel
    private var imageList = intArrayOf(R.drawable.img_slider1, R.drawable.img_slider2)
    private val dataPelanggan by lazy {
        baseGetInstance<Pelanggan>("EXTRA_PELANGGAN_DASHBOARD")
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDashboardPelangganBinding {
        return FragmentDashboardPelangganBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        viewModel = obtainViewModel<DashboardPelangganViewModel>().apply {

            listKategori.observe(viewLifecycleOwner, {
                setupRvKategori(it)
                binding.tvKategori.visibility = View.VISIBLE
            })

            listNilai.observe(viewLifecycleOwner, {
                setupRvTukang(it, dataPelanggan)
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

        Log.d("Data Pelanggan : ", dataPelanggan.toString())

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

//            tvNamaPelanggan.text = dataPelanggan.nama_pelanggan
        }

//        binding.tvText.text = extraArgument.nama_pelanggan

//        binding.btnDetail.setOnClickListener {
//            val moveIntent = Intent(context, DetailTukangPelangganActivity::class.java)
//            startActivity(moveIntent)
//        }
    }

    private fun setupRvKategori(data: List<DetailKategoriNilai>?) {
        val kategoriTukangAdapter = KategoriTukangAdapter()
        data?.let { kategoriTukangAdapter.setKategori(it) }

        binding.rvKategori.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = kategoriTukangAdapter
        }

        kategoriTukangAdapter.setOnItemClickCallback(object : KategoriTukangAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DetailKategoriNilai) {

                val detailKategoriFragment = DetailKategoriFragment()

                val bundle = Bundle()
                val bundleData = Gson().toJson(data)
                val bundleDataPelanggan = Gson().toJson(dataPelanggan)
                bundle.putString("EXTRA_TUKANG_BY_KATEGORI", bundleData)
                bundle.putString("EXTRA_TUKANG_BY_KATEGORI_EXTRA_PELANGGAN", bundleDataPelanggan)
                bundle.putString("EXTRA_ACCOUNT_STATUS", "status_pelanggan")
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

    private fun setupRvTukang(data: List<DetailKategoriNilai>, dataPelanggan: Pelanggan) {
        val rekomendasiTukangAdapter = RekomendasiTukangAdapter()
        rekomendasiTukangAdapter.setupDataPelanggan(dataPelanggan)
        rekomendasiTukangAdapter.setTukang(data)

        binding.rvTukang.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekomendasiTukangAdapter
        }

        rekomendasiTukangAdapter.setOnItemClickCallback(object :
            RekomendasiTukangAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailKategoriNilai) {
                selectedTukang(data)
            }
        })
    }

    private fun selectedTukang(data: DetailKategoriNilai) {
        Toast.makeText(context, "Kamu memilih " + data.nama_tukang, Toast.LENGTH_SHORT).show()
        Log.d("Test", "CLICK FROM ADAPTER")
        val intent = Intent(binding.root.context, DetailTukangPelangganActivity::class.java)
        intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_TUKANG, data)
        intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_PELANGGAN, dataPelanggan)
        startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DashboardPelangganFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}