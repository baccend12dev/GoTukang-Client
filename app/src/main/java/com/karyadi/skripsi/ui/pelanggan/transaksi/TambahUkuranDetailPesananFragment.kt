package com.karyadi.skripsi.ui.pelanggan.transaksi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.databinding.FragmentTambahUkuranDetailPesananBinding
import com.karyadi.skripsi.model.Pelanggan
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.model.UkuranDetailPesanan
import com.karyadi.skripsi.ui.main.HomePelangganActivity
import com.karyadi.skripsi.ui.pelanggan.transaksi.adapter.TambahUkuranDetailPesananAdapter
import com.karyadi.skripsi.ui.pelanggan.transaksi.viewmodel.UkuranDetailPesananViewModel
import com.karyadi.skripsi.utils.PrefHelper

class TambahUkuranDetailPesananFragment : BaseFragment<FragmentTambahUkuranDetailPesananBinding>() {

    private lateinit var ukuranDetailPesananViewModel: UkuranDetailPesananViewModel
    private val dataPesanan by lazy {
        baseGetInstance<Pesanan>("DETAIL_UKURAN_PESANAN_TUKANG")
    }
    lateinit var prefHelper: PrefHelper

    private val tempDataUkuranPesanan = mutableListOf<UkuranDetailPesanan>()

    private fun getItemLayout(position: Int): TambahUkuranDetailPesananAdapter.TambahUkuranDetailPesananViewHolder {
        return binding.rvTambahUkuran.findViewHolderForLayoutPosition(position) as TambahUkuranDetailPesananAdapter.TambahUkuranDetailPesananViewHolder
    }

    private fun isiUkuranPesanan(): MutableList<UkuranDetailPesanan> {
        val data = mutableListOf<UkuranDetailPesanan>()
        data.clear()
        for (i in tempDataUkuranPesanan.indices) {
            data.add(
                UkuranDetailPesanan(
                    tempDataUkuranPesanan[i].id_ukuran_detail_pesanan,
                    dataPesanan.id_pesanan,
                    tempDataUkuranPesanan[i].id_ukuran,
                    tempDataUkuranPesanan[i].nama_ukuran,
                    tempDataUkuranPesanan[i].gambar_ukuran,
                    getItemLayout(i).getEtUkuran()
                )
            )
        }
        return data
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTambahUkuranDetailPesananBinding {
        return FragmentTambahUkuranDetailPesananBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        ukuranDetailPesananViewModel = obtainViewModel<UkuranDetailPesananViewModel>().apply {
            listUkuranDetailPesanan.observe(viewLifecycleOwner, {
                setupRvUkuran(it)
                tempDataUkuranPesanan.clear()
                tempDataUkuranPesanan.addAll(it)
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

            messageFailed.observe(viewLifecycleOwner, {
                showToast(it)
            })

            messageSuccess.observe(viewLifecycleOwner, {
                showToast(it)
            })

            onSuccessState.observe(viewLifecycleOwner, {
            })

        }
        ukuranDetailPesananViewModel.getDataUkuranPesananByDetailKategory(dataPesanan)
        val listUkuran =
            ukuranDetailPesananViewModel.getDataUkuranPesananByDetailKategory(dataPesanan)

        Log.d("data ukuran : ", listUkuran.toString())
    }

    private fun setupRvUkuran(data: List<UkuranDetailPesanan>?) {
        val tambahUkuranDetailPesananAdapter = TambahUkuranDetailPesananAdapter()
        tambahUkuranDetailPesananAdapter.setUkuranPesanan(data!!)

        binding.rvTambahUkuran.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = tambahUkuranDetailPesananAdapter
        }

    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {
        prefHelper = PrefHelper(context)
        val idPelanggan = prefHelper.getString(PrefHelper.PREF_ID_PELANGGAN)
        val idPelangganInt : Int = idPelanggan!!.toInt()

        val dataPelanggan = Pelanggan(
            idPelangganInt, "","","","","","","","",""
        )

        Log.d("Data pesanan : ", dataPesanan.toString())

        binding.tvIdDetailKategori.text = "Detail Kategori : " + dataPesanan.id_detail_kategori.toString()

        binding.btnKirimData.setOnClickListener {
            for (i in isiUkuranPesanan().indices) {
                ukuranDetailPesananViewModel.insertDataUkuranDetailPesanan(isiUkuranPesanan()[i])
            }

            Toast.makeText(context, "Data Ukuran Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

            val intent = Intent(getActivity(), HomePelangganActivity::class.java)
            intent.putExtra("EXTRA_LOGIN_PELANGGAN", dataPelanggan)
            getActivity()?.startActivity(intent)
//            getActivity()?.finish()

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TambahUkuranDetailPesananFragment().apply {
                arguments = Bundle().apply {}
            }
    }


}