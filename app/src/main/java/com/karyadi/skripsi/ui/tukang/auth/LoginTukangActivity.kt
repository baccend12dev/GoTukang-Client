package com.karyadi.skripsi.ui.tukang.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.databinding.ActivityLoginTukangBinding
import com.karyadi.skripsi.ui.main.HomeTukangActivity
import com.karyadi.skripsi.ui.main.PilihUserActivity
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_EMAIL_TUKANG
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_ID_TUKANG
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_IS_LOGIN_TUKANG
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_NAMA_TUKANG
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_PASSWORD_TUKANG
import com.karyadi.skripsi.utils.ViewModelFactory

class LoginTukangActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginTukangBinding
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTukangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefHelper = PrefHelper(this)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]

        viewModel.apply{

            dataTukang.observe(this@LoginTukangActivity, {

                if(prefHelper.getBoolean(PREF_IS_LOGIN_TUKANG)){

                    val idTukang = it.id_tukang.toString()
                    val namaTukang = it.nama_tukang

                    prefHelper.put(PREF_ID_TUKANG, idTukang)
                    prefHelper.put(PREF_NAMA_TUKANG, namaTukang!!)

                    val moveIntent = Intent(this@LoginTukangActivity, HomeTukangActivity::class.java)
                    moveIntent.putExtra("EXTRA_LOGIN_TUKANG", it)
                    startActivity(moveIntent)

                }
            })

            messageSuccess.observe(this@LoginTukangActivity, {
                Log.d("PREF_IS_LOGIN_TUKANG", "Value is : ${prefHelper.getBoolean(PREF_IS_LOGIN_TUKANG)}")
                Toast.makeText(this@LoginTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

            showProgress.observe(this@LoginTukangActivity, {
                if (it) {
                    // show progress
                } else {
                    // hide progress
                }
            })

            messageFailed.observe(this@LoginTukangActivity, {
                Toast.makeText(this@LoginTukangActivity, it, Toast.LENGTH_SHORT).show()
            })
        }

        binding.btnLoginTukang.setOnClickListener {

            val emailTukang = binding.etEmailTukang.text.toString().trim()
            val passwordTukang = binding.etPasswordTukang.text.toString().trim()

            saveSession(emailTukang, passwordTukang)
            viewModel.loginTukang(emailTukang, passwordTukang)
        }

        binding.ivBack.setOnClickListener {
            val moveIntent = Intent(this, PilihUserActivity::class.java)
            startActivity(moveIntent)
        }

        binding.tvRegister.setOnClickListener {
            val moveIntent = Intent(this, RegisterTukangActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun saveSession(emailTukang: String, passwordTukang: String) {
        prefHelper.put(PREF_EMAIL_TUKANG, emailTukang)
        prefHelper.put(PREF_PASSWORD_TUKANG, passwordTukang)
        prefHelper.put(PREF_IS_LOGIN_TUKANG, true)
    }
}