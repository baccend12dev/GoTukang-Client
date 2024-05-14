package com.karyadi.skripsi.ui.tukang.kategori

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.R
import com.karyadi.skripsi.databinding.FragmentTambahDataKategoriBinding
import com.karyadi.skripsi.model.DetailKategori
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.tukang.kategori.viewmodel.KategoriTukangViewModel
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson

class TambahDataKategoriFragment : DialogFragment() {

    private lateinit var binding: FragmentTambahDataKategoriBinding
    val EXTRA_TUKANG = "EXTRA_TUKANG"
    val FIELD_REQUIRED = "Field tidak boleh kosong"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTambahDataKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundleData = arguments?.getString(EXTRA_TUKANG)
        val dataTukang = Gson().fromJson(bundleData, Tukang::class.java)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[KategoriTukangViewModel::class.java]

        viewModel.apply {
            dataDetailKategori.observe(this@TambahDataKategoriFragment, {
                dialog?.dismiss()
                (parentFragment as KategoriTukangFragment).refreshGetDataViewModel()
            })

            messageSuccess.observe(this@TambahDataKategoriFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            messageFailed.observe(this@TambahDataKategoriFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }

        binding.tvNamaTukang.text = dataTukang.nama_tukang

        binding.btnSimpan.setOnClickListener {
            popupSimpanData(context, dataTukang)
        }

        binding.btnCancel.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun popupSimpanData(context: Context?, dataTukang: Tukang?) {
        val box: Context = ContextThemeWrapper(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(box)
        materialAlertDialogBuilder.setTitle("Tambah Data")
            .setMessage("Apa anda yakin ingin menambah data ini?")
            .setNegativeButton("Batalkan", null)
            .setPositiveButton("Tambah") { dialogInterface, i -> simpanData(dataTukang) }
            .show()
    }

    private fun simpanData(dataTukang: Tukang?) {

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[KategoriTukangViewModel::class.java]

        var value = 1
        when (binding.rgKategori.checkedRadioButtonId) {
            R.id.rb_kategori_2 -> value = 1
            R.id.rb_kategori_3 -> value = 2
            R.id.rb_kategori_4 -> value = 3
            R.id.rb_kategori_5 -> value = 4
            R.id.rb_kategori_6 -> value = 5
            R.id.rb_kategori_7 -> value = 6
            R.id.rb_kategori_8 -> value = 7
            R.id.rb_kategori_9 -> value = 8
        }
        val idKategori = value

        val ketKategori = binding.etKetKategori.text.toString().trim()
        val bahanTukang = binding.etBahanTukang.text.toString().trim()
        val hargaBahan = binding.etHargaBahan.text.toString().trim()
        val ongkosTukang = binding.etOngkosTukang.text.toString().trim()
        val lamaWaktu = binding.etLamaWaktu.text.toString().trim()

        if (ketKategori.isEmpty()){
            binding.etKetKategori.error = FIELD_REQUIRED
            return
        }

        if (ongkosTukang.isEmpty()){
            binding.etOngkosTukang.error = FIELD_REQUIRED
            return
        }

        val dataDetailKategori = DetailKategori(
            0,
            dataTukang?.id_tukang,
            idKategori,
            ketKategori,
            bahanTukang,
            hargaBahan,
            ongkosTukang,
            lamaWaktu,
        )

        viewModel.insertDataDetailKategori(dataDetailKategori)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TambahDataKategoriFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}