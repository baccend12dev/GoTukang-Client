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
import com.karyadi.skripsi.databinding.FragmentEditDataKategoriBinding
import com.karyadi.skripsi.model.DetailKategori
import com.karyadi.skripsi.model.DetailKategoriTukang
import com.karyadi.skripsi.ui.tukang.kategori.viewmodel.KategoriTukangViewModel
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson


class EditDataKategoriFragment : DialogFragment() {

    private lateinit var binding: FragmentEditDataKategoriBinding
    val EXTRA_DETAIL_KATEGORI = "EXTRA_DETAIL_KATEGORI"
    val FIELD_REQUIRED = "Field tidak boleh kosong"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditDataKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundleData = arguments?.getString(EXTRA_DETAIL_KATEGORI)
        val extraData = Gson().fromJson(bundleData, DetailKategoriTukang::class.java)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[KategoriTukangViewModel::class.java]

        viewModel.apply {
            dataDetailKategori.observe(this@EditDataKategoriFragment, {
                dialog?.dismiss()
                (parentFragment as KategoriTukangFragment).refreshGetDataViewModel()
            })

            messageSuccess.observe(this@EditDataKategoriFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })

            messageFailed.observe(this@EditDataKategoriFragment, {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }

        binding.tvNamaTukang.text = extraData.nama_tukang
        binding.etKetKategori.setText(extraData.keterangan_kategori)
        binding.etBahanTukang.setText(extraData.bahan_tukang)
        binding.etHargaBahan.setText(extraData.harga_bahan)
        binding.etOngkosTukang.setText(extraData.ongkos_tukang)
        binding.etLamaWaktu.setText(extraData.perkiraan_lama_waktu_pengerjaan)

        binding.btnSimpan.setOnClickListener {
            popupSimpanData(context, extraData)
        }

        binding.btnCancel.setOnClickListener {
            dialog?.cancel()
        }

    }

    private fun popupSimpanData(context: Context?, data: DetailKategoriTukang?) {
        val box: Context = ContextThemeWrapper(context, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(box)
        materialAlertDialogBuilder.setTitle("Perbarui Data")
            .setMessage("Apa Anda yakin ingin mengubah data ini?")
            .setNegativeButton("Batalkan", null)
            .setPositiveButton("Simpan") { dialogInterface, i -> simpanData(data) }
            .show()
    }

    private fun simpanData(data: DetailKategoriTukang?) {

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[KategoriTukangViewModel::class.java]

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
            data?.id_detail_kategori,
            data?.id_tukang,
            data?.id_kategori,
            ketKategori,
            bahanTukang,
            hargaBahan,
            ongkosTukang,
            lamaWaktu
        )

        viewModel.updateDataDetailKategori(dataDetailKategori)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            EditDataKategoriFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}