package com.karyadi.skripsi.ui.pelanggan.rating

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.karyadi.skripsi.databinding.ActivityRatingTukangBinding
import com.karyadi.skripsi.model.Pesanan
import com.karyadi.skripsi.model.Rating
import com.karyadi.skripsi.ui.pelanggan.rating.viewmodel.RatingTukangViewModel
import com.karyadi.skripsi.utils.ViewModelFactory

class RatingTukangActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingTukangBinding

    companion object {
        const val EXTRA_DATA_RATING_TUKANG = "EXTRA_DATA_RATING_TUKANG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatingTukangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Rating Tukang"

        val dataPesanan: Pesanan? = intent.extras?.getParcelable(EXTRA_DATA_RATING_TUKANG)
        val idTukang = dataPesanan?.id_tukang


        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[RatingTukangViewModel::class.java]

        viewModel.apply {
            dataRating.observe(this@RatingTukangActivity, {
//                val intent = Intent(this@RatingTukangActivity, HomePelangganActivity::class.java)
//                startActivity(intent)
                finish()
            })

            messageSuccess.observe(this@RatingTukangActivity, {
                Toast.makeText(this@RatingTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

            showProgress.observe(this@RatingTukangActivity, {
                if (it) {
                    // show progress
                } else {
                    // hide progress
                }
            })

            messageFailed.observe(this@RatingTukangActivity, {
                Toast.makeText(this@RatingTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

        }

        binding.btnRating.setOnClickListener {

            val kriteria1 : Int = binding.rbKriteria1.rating.toInt()
            val kriteria2 : Int = binding.rbKriteria2.rating.toInt()
            val kriteria3 : Int = binding.rbKriteria3.rating.toInt()
            val kriteria4 : Int = binding.rbKriteria4.rating.toInt()

            binding.rbKriteria1.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener{
                override fun onRatingChanged(
                    ratingBar: RatingBar?,
                    rating: Float,
                    fromUser: Boolean
                ) {
                    Toast.makeText(applicationContext, "Rating : $rating", Toast.LENGTH_SHORT).show()
                }

            })

            binding.rbKriteria2.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener{
                override fun onRatingChanged(
                    ratingBar: RatingBar?,
                    rating: Float,
                    fromUser: Boolean
                ) {
                    Toast.makeText(applicationContext, "Rating : $rating", Toast.LENGTH_SHORT).show()
                }

            })

            binding.rbKriteria3.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener{
                override fun onRatingChanged(
                    ratingBar: RatingBar?,
                    rating: Float,
                    fromUser: Boolean
                ) {
                    Toast.makeText(applicationContext, "Rating : $rating", Toast.LENGTH_SHORT).show()
                }

            })

            binding.rbKriteria4.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener{
                override fun onRatingChanged(
                    ratingBar: RatingBar?,
                    rating: Float,
                    fromUser: Boolean
                ) {
                    Toast.makeText(applicationContext, "Rating : $rating", Toast.LENGTH_SHORT).show()
                }

            })


            val dataRating = Rating(
                0,
                idTukang,
                kriteria1,
                kriteria2,
                kriteria3,
                kriteria4,
            )


            viewModel.insertDataRating(dataRating)
        }

    }
}