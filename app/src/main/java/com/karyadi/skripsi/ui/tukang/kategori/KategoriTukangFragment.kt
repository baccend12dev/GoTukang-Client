package com.karyadi.skripsi.ui.tukang.kategori

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.karyadi.skripsi.R
import com.karyadi.skripsi.core.BaseFragment
import com.karyadi.skripsi.model.DetailKategoriTukang
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.tukang.kategori.adapter.ListKategoriAdapter
import com.karyadi.skripsi.ui.tukang.kategori.viewmodel.KategoriTukangViewModel
import com.karyadi.skripsi.ui.tukang.ukuran.UkuranDetailKategoriActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.karyadi.skripsi.databinding.FragmentKategoriTukangBinding

class KategoriTukangFragment : BaseFragment<FragmentKategoriTukangBinding>() {

    private val kategoriTukangViewModel: KategoriTukangViewModel by lazy {
        obtainViewModel()
    }

    private val dataTukang by lazy {
        baseGetInstance<Tukang>("EXTRA_TUKANG_KATEGORI")
    }

    private val kategoriAdapter by lazy {
        ListKategoriAdapter()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKategoriTukangBinding {
       return FragmentKategoriTukangBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
        kategoriTukangViewModel.apply {

            listDetailKategori.observe(viewLifecycleOwner, {
                setupRvDetailKategori(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, {
                setupEventProgressView(binding.progressBar, it)
            })

            eventErrorMessage.observe(viewLifecycleOwner, {
                showToast(it)

            })

            dataListDetailKategori.observe(viewLifecycleOwner, {

            })

            messageSuccess.observe(viewLifecycleOwner, {
                showToast(it)
            })

            eventShowProgress.observe(viewLifecycleOwner, {
                setupEventProgressView(binding.progressBar, it)
            })

            onSuccessDeleteState.observe(viewLifecycleOwner, {
                if (it) {

                    deleteItemPosition.observe(viewLifecycleOwner, {
                        kategoriAdapter.deleteItem(it)
                    })

                }
            })

        }

        kategoriTukangViewModel.getListDetailKategori(dataTukang)
    }

    override fun setupUI(view: View, savedInstanceState: Bundle?) {

        binding.tvDetailKategori.text = "Kategori Tukang " + dataTukang.nama_tukang

        binding.btnAddCategory.setOnClickListener {
            val tambahDataKategoriFragment = TambahDataKategoriFragment()

            val bundle = Bundle()
            val bundleData = Gson().toJson(dataTukang)
            bundle.putString("EXTRA_TUKANG", bundleData)
            tambahDataKategoriFragment.arguments = bundle

            val fragmentManager = childFragmentManager //cara memunculkan dialog box(1)
            tambahDataKategoriFragment.show(fragmentManager, TambahDataKategoriFragment::class.java.simpleName) //cara memunculkan dialog box(2)
        }

    }

    private fun setupRvDetailKategori(data: List<DetailKategoriTukang>?) {
        kategoriAdapter.setDetailKategori(data!!)

        binding.rvDetailKategori.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = kategoriAdapter
        }

        kategoriAdapter.setOnDeleteClickCallback(object : ListKategoriAdapter.OnDeleteClickCallback{
            override fun onDeleteClicked(data: DetailKategoriTukang, position: Int) {
                popupDelete(context, data, position)
            }
        })

        kategoriAdapter.setOnUpdateClickCallback(object : ListKategoriAdapter.OnUpdateClickCallback{
            override fun onUpdateClikced(data: DetailKategoriTukang) {

                val editDataKategoriFragment = EditDataKategoriFragment()

                val bundle = Bundle()
                val bundleData = Gson().toJson(data)
                bundle.putString("EXTRA_DETAIL_KATEGORI", bundleData)
                editDataKategoriFragment.arguments = bundle

                val fragmentManager = childFragmentManager //cara memunculkan dialog box(1)
                editDataKategoriFragment.show(fragmentManager, EditDataKategoriFragment::class.java.simpleName) //cara memunculkan dialog box(2)
            }
        })

        kategoriAdapter.setOnItemClickCallback(object : ListKategoriAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DetailKategoriTukang) {
                selectedKategori(data)
            }
        })
    }

    private fun selectedKategori(data: DetailKategoriTukang) {
        Toast.makeText(context, "Kamu memilih " + data.nama_kategori, Toast.LENGTH_SHORT).show()
        Log.d("Test", "CLICK FROM ADAPTER")
        val intent = Intent(binding.root.context, UkuranDetailKategoriActivity::class.java)
        intent.putExtra(UkuranDetailKategoriActivity.EXTRA_DATA_KATEGORI, data)
        startActivity(intent)
    }

    private fun popupDelete(context: Context?, data: DetailKategoriTukang, position: Int) {
        val box: Context = ContextThemeWrapper(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(box)
        materialAlertDialogBuilder.setTitle("Hapus Data")
            .setMessage("Apa anda yakin ingin menghapus data ini?")
            .setNegativeButton("Tidak", null)
            .setPositiveButton(
                "Hapus"
            ) { dialogInterface, i ->
                // panggil disini
                deleteDataKategori(data, position)
             }
            .show()
    }

    private fun deleteDataKategori(data: DetailKategoriTukang, position: Int){
        kategoriTukangViewModel.deleteDataDetailKategori(data, position)
    }

    fun refreshGetDataViewModel() {
        kategoriTukangViewModel.getListDetailKategori(dataTukang)
    }


    private fun showMessage(message: String, context: Context?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            KategoriTukangFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}