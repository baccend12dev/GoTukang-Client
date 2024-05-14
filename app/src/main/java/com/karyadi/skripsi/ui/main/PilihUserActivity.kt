package com.karyadi.skripsi.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karyadi.skripsi.databinding.ActivityPilihUserBinding
import com.karyadi.skripsi.ui.pelanggan.auth.LoginPelangganActivity
import com.karyadi.skripsi.ui.tukang.auth.LoginTukangActivity

class PilihUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPilihUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPilihUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Pilih Salah Satu"

        binding.layoutUser1.setOnClickListener {
            val moveIntent = Intent(this, LoginPelangganActivity::class.java)
            startActivity(moveIntent)
        }

        binding.layoutUser2.setOnClickListener {
            val moveIntent = Intent(this, LoginTukangActivity::class.java)
            startActivity(moveIntent)
        }



    }
}