package com.karyadi.skripsi.ui.detailkategoriInGridViewKategori

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.databinding.FragmentDetailKategoriBinding
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.detailkategoriInGridViewKategori.adapter.DetailKetegoriAdapter
import com.karyadi.skripsi.ui.pelanggan.dashboard.viewmodel.DashboardPelangganViewModel
import com.karyadi.skripsi.ui.pelanggan.detail.DetailTukangPelangganActivity
import com.karyadi.skripsi.ui.tukang.dashboard.DetailTukangActivity


class DetailKategoriFragment : BaseFragment<FragmentDetailKategoriBinding>() {

    private lateinit var dashboardPelangganViewModel: DashboardPelangganViewModel
    private val data by lazy {
        baseGetInstance<DetailKategoriNilai>("EXTRA_TUKANG_BY_KATEGORI")
    }

    private val extraDataPelanggan by lazy {
        baseGetInstance<Pelanggan>("EXTRA_TUKANG_BY_KATEGORI_EXTRA_PELANGGAN")
    }

    private val extraDataTukang by lazy {
        baseGetInstance<Tukang>("EXTRA_TUKANG_BY_KATEGORI_EXTRA_TUKANG")
    }

    private val statusAccount by lazy {
        baseGetInstance<String>("EXTRA_ACCOUNT_STATUS")
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailKategoriBinding {
        return FragmentDetailKategoriBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        dashboardPelangganViewModel = obtainViewModel<DashboardPelangganViewModel>().apply {

            listDataTukangByKategori.observe(viewLifecycleOwner, {

                if (statusAccount == "status_tukang") {
                    setupRvTukangByKategoriInTukang(it, extraDataTukang)

                } else if (statusAccount == "status_pelanggan") {
                    setupRvTukangByKategoriInPelanggan(it, extraDataPelanggan)

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
        dashboardPelangganViewModel.getDataTukangByKategori(data)
    }

    private fun setupRvTukangByKategoriInTukang(
        data: List<DetailKategoriNilai>?,
        dataTukang: Tukang
    ) {
        val detailKetegoriAdapter = DetailKetegoriAdapter()
        detailKetegoriAdapter.setupDataTukang(dataTukang)
        detailKetegoriAdapter.setTukangByKategori(data!!)

        binding.rvTukang.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailKetegoriAdapter
        }

        detailKetegoriAdapter.setOnItemClickCallback(object :
            DetailKetegoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailKategoriNilai) {

                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Kamu memilih " + data.nama_tukang, Toast.LENGTH_SHORT).show()
                Log.d("Test", "CLICK FROM ADAPTER")
                val intent = Intent(binding.root.context, DetailTukangActivity::class.java)
                intent.putExtra(DetailTukangActivity.EXTRA_DATA_NILAI, data)
                intent.putExtra(DetailTukangActivity.EXTRA_DATA_TUKANG, dataTukang)
                startActivity(intent)

            }

        })
    }

    private fun setupRvTukangByKategoriInPelanggan(
        data: List<DetailKategoriNilai>?,
        dataPelanggan: Pelanggan
    ) {
        val detailKetegoriAdapter = DetailKetegoriAdapter()
        detailKetegoriAdapter.setupDataPelanggan(dataPelanggan)
        detailKetegoriAdapter.setTukangByKategori(data!!)

        binding.rvTukang.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailKetegoriAdapter
        }

        detailKetegoriAdapter.setOnItemClickCallback(object :
            DetailKetegoriAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DetailKategoriNilai) {

                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, "Kamu memilih " + data.nama_tukang, Toast.LENGTH_SHORT).show()
                Log.d("Test", "CLICK FROM ADAPTER")

                val intent = Intent(binding.root.context, DetailTukangPelangganActivity::class.java)
                intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_TUKANG, data)
                intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_PELANGGAN, dataPelanggan)
                startActivity(intent)

            }

        })
    }

//    private fun setupRvTukangByKategori(data: List<DetailKategoriNilai>?, dataPelanggan: Pelanggan) {
//        val detailKetegoriAdapter = DetailKetegoriAdapter()
//        detailKetegoriAdapter.setupDataPelanggan(dataPelanggan)
//        detailKetegoriAdapter.setTukangByKategori(data!!)
//
//        binding.rvTukang.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = detailKetegoriAdapter
//        }
//
//        detailKetegoriAdapter.setOnItemClickCallback(object :
//            DetailKetegoriAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: DetailKategoriNilai) {
//
//
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
//                Toast.makeText(context, "Kamu memilih " + data.nama_tukang, Toast.LENGTH_SHORT)
//                    .show()
//                Log.d("Test", "CLICK FROM ADAPTER")
//
//                if (statusAccount == "status_tukang") {
//                    val intent = Intent(binding.root.context, DetailTukangActivity::class.java)
//                    intent.putExtra(DetailTukangActivity.EXTRA_TUKANG, data)
//                    startActivity(intent)
//                } else if (statusAccount == "status_pelanggan") {
//                    val intent =
//                        Intent(binding.root.context, DetailTukangPelangganActivity::class.java)
//                    intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_TUKANG, data)
//                    intent.putExtra(DetailTukangPelangganActivity.EXTRA_DATA_PELANGGAN, dataPelanggan)
//                    startActivity(intent)
//                }
//            }
//
//        })
//    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        binding.tvTukangByKategori.text =
            "Data Tukang yang memiliki kategori " + data.nama_kategori
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DetailKategoriFragment().apply {
                arguments = Bundle().apply {}
            }

    }

//    private lateinit var binding: FragmentDetailKategoriBinding
//    val EXTRA_TUKANG_BY_KATEGORI = "EXTRA_TUKANG_BY_KATEGORI"

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentDetailKategoriBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val bundleData = arguments?.getString(EXTRA_TUKANG_BY_KATEGORI)
//        val data = Gson().fromJson(bundleData, DetailKategoriNilai::class.java)
//    }


}