package com.karyadi.skripsi.ui.tukang.transaksi

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.ui.pelanggan.pesanan.viewmodel.PesananViewModel
import com.karyadi.skripsi.ui.pelanggan.transaksi.adapter.TransaksiPelangganAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.FragmentTransaksiTukangBinding

class TransaksiTukangFragment : BaseFragment<FragmentTransaksiTukangBinding>() {

    private lateinit var pesananViewModel: PesananViewModel
    private val dataTukang by lazy{
        baseGetInstance<Tukang>("EXTRA_TUKANG")
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTransaksiTukangBinding {
        return FragmentTransaksiTukangBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        pesananViewModel = obtainViewModel<PesananViewModel>().apply {
            listPesanan.observe(viewLifecycleOwner, {
                setupRvPesanan(it)
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
        pesananViewModel.getDataPesananByTukang(dataTukang)
    }

    private fun setupRvPesanan(data: List<Pesanan>?) {
        val transaksiPelangganAdapter = TransaksiPelangganAdapter()
        transaksiPelangganAdapter.setListPesanan(data!!)

        binding.rvPesanan.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transaksiPelangganAdapter
        }

        transaksiPelangganAdapter.setOnDeleteClickCallback(object : TransaksiPelangganAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(data: Pesanan) {
                popupDelete(context, data)
            }

        })

        transaksiPelangganAdapter.setOnUpdateClickCallback(object : TransaksiPelangganAdapter.OnUpdateClickCallback{
            override fun onUpdateClikced(data: Pesanan) {
                Toast.makeText(context, "Kamu mengupdate " + data.id_pesanan, Toast.LENGTH_SHORT).show()

            }

        })

        transaksiPelangganAdapter.setOnItemClickCallback(object : TransaksiPelangganAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Pesanan) {
                selectedPesanan(data)
            }

        })

    }

    private fun popupDelete(context: Context?, data: Pesanan) {
        val box: Context = ContextThemeWrapper(context, R.style.AppTheme)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(box)
        materialAlertDialogBuilder.setTitle("Hapus Data")
            .setMessage("Apa anda yakin ingin membatalkan data ini?")
            .setNegativeButton("Batalkan", null)
            .setPositiveButton(
                "Hapus"
            ) { dialogInterface, i ->
                // panggil disini
                deleteDataPesanan(data)
            }
            .show()
    }

    private fun deleteDataPesanan(data: Pesanan) {
        Toast.makeText(context, "Kamu menghapus " + data.id_pesanan, Toast.LENGTH_SHORT).show()
    }

    private fun selectedPesanan(data: Pesanan) {
        Toast.makeText(context, "Kamu memilih " + data.id_pesanan, Toast.LENGTH_SHORT).show()

        val detailTransaksiTukangFragment = DetailTransaksiTukangFragment()

        val bundle = Bundle()
        val bundleData = Gson().toJson(data)
        bundle.putString("DETAIL_PESANAN_TUKANG", bundleData)

        detailTransaksiTukangFragment.arguments = bundle

        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, detailTransaksiTukangFragment, DetailTransaksiTukangFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TransaksiTukangFragment().apply {
                arguments = Bundle().apply {}
            }
    }


}