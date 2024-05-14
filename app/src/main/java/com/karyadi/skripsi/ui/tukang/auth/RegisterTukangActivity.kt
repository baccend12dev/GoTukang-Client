package com.karyadi.skripsi.ui.tukang.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.databinding.ActivityRegisterTukangBinding
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.main.HomeTukangActivity
import com.karyadi.skripsi.ui.main.PilihUserActivity
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.ViewModelFactory

class RegisterTukangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterTukangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterTukangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]

        viewModel.apply {
            dataTukang.observe(this@RegisterTukangActivity, {
                val moveIntent = Intent(this@RegisterTukangActivity, HomeTukangActivity::class.java)
                startActivity(moveIntent)
            })

            messageSuccess.observe(this@RegisterTukangActivity, {
                Toast.makeText(this@RegisterTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

            showProgress.observe(this@RegisterTukangActivity, {
                if (it) {
                    // show progress
                } else {
                    // hide progress
                }
            })

            messageFailed.observe(this@RegisterTukangActivity, {
                Toast.makeText(this@RegisterTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

        }

        binding.btnRegisTukang.setOnClickListener {

            val namaTukang = binding.etNamaTukang.text.toString().trim()
            val emailTukang = binding.etEmailTukang.text.toString().trim()
            val passwordTukang = binding.etPasswordTukang.text.toString().trim()

            val dataTukang = Tukang(
                0,
                namaTukang,
                emailTukang,
                passwordTukang,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                )

            viewModel.registerTukang(dataTukang)

//            Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
//            val moveIntent = Intent(this, HomeTukangActivity::class.java)
//            startActivity(moveIntent)
        }

        binding.ivBack.setOnClickListener {
            val moveIntent = Intent(this, PilihUserActivity::class.java)
            startActivity(moveIntent)
        }

        binding.tvLogin.setOnClickListener {
            val moveIntent = Intent(this, LoginTukangActivity::class.java)
            startActivity(moveIntent)
        }
    }
}