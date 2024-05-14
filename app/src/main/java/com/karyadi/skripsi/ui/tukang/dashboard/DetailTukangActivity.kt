package com.karyadi.skripsi.ui.tukang.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.karyadi.skripsi.R
import com.karyadi.skripsi.model.DetailKategoriNilai
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.pelanggan.detail.adapter.ListKategoriInDetailAdapter
import com.karyadi.skripsi.ui.pelanggan.detail.viewmodel.KategoriTukangInPelangganViewModel
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.Constant
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.karyadi.skripsi.databinding.ActivityDetailTukangBinding
import java.text.DecimalFormat
import java.util.*

class DetailTukangActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityDetailTukangBinding
    private lateinit var currentLocation : Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var mMap: GoogleMap? = null
    val REQUEST_CODE = 104

    lateinit var prefHelper: PrefHelper

    companion object {
        const val EXTRA_DATA_NILAI = "EXTRA_DATA_NILAI"
        const val EXTRA_DATA_TUKANG = "EXTRA_DATA_TUKANG"
    }

    private val factory by lazy {
        ViewModelFactory.getInstance(this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, factory)[KategoriTukangInPelangganViewModel::class.java]
    }

    private val authTukangViewModel by lazy {
        ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]
    }

    private val extraDataNilai: DetailKategoriNilai? by lazy {
        intent.getParcelableExtra(EXTRA_DATA_NILAI)
    }

    private val extraDataTukang: Tukang? by lazy {
        intent.getParcelableExtra(EXTRA_DATA_TUKANG)
    }

    private fun setupViewModel() {
        viewModel.apply {
            listDetailKategori.observe(this@DetailTukangActivity, {
                setupRvDetailKategori(it)
            })

            eventShowProgress.observe(this@DetailTukangActivity, {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            })

            eventErrorMessage.observe(this@DetailTukangActivity, {
                Toast.makeText(this@DetailTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

            eventIsEmpty.observe(this@DetailTukangActivity, {
            })
            getListDetailKategori(extraDataNilai!!)
        }

        authTukangViewModel.apply {
            dataTukang.observe(this@DetailTukangActivity,{

            })
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTukangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Data Tukang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupViewModel()

        // Using Gson
//        val extraData : String = intent.extras?.getString(EXTRA_TUKANG)!!
//        val dataNilai = Gson().fromJson(extraData, Nilai::class.java)
//        val tukangId = dataNilai.id_tukang

        // Using Parcelable
//        val extraDataNilai: DetailKategoriNilai? = intent.extras?.getParcelable(EXTRA_DATA_NILAI)

        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.INVISIBLE

        binding.apply {

            binding.progressBar.visibility = View.GONE
            binding.content.visibility = View.VISIBLE

            if (extraDataNilai != null) {
                tvNamaTukang.text = extraDataNilai!!.nama_tukang
                tvNamaToko.text = extraDataNilai!!.nama_toko
                tvNamaTukang2.text = extraDataNilai!!.nama_tukang
                tvNamaToko2.text = extraDataNilai!!.nama_toko
                tvKetToko.text = extraDataNilai!!.keterangan_toko
                tvEmailTukang.text = extraDataNilai!!.email_tukang
                tvTeleponTukang.text = extraDataNilai!!.telp_tukang
                tvAlamatTukang.text = extraDataNilai!!.alamat_tukang
                tvSpesifikasi.text = extraDataNilai!!.spesifikasi_tukang
                tvJangkauan.text = extraDataNilai!!.jangkauan_kategori_tukang
//                tvHariBuka.text = extraDataNilai!!.hari_buka
//                tvJamBuka.text = extraDataNilai!!.jam_buka
//                tvJamTutup.text = extraDataNilai!!.jam_tutup
                tvJarak.text = " " + getHasilOlahDataLongLat(extraDataTukang!!, extraDataNilai!!) + " km"

                Glide.with(this@DetailTukangActivity)
                    .load("${Constant.IMAGE_TUKANG}${extraDataNilai!!.foto_tukang}")
                    .into(imgTukang)
            }

            if(extraDataNilai!!.nilai_akhir != null){
                val df = DecimalFormat("#.#")
                val extraRating = extraDataNilai!!.nilai_akhir
                val rating = df.format(extraRating)
//                tvRating.text = rating.toString()
                tvRating.text = extraRating.toString()
            }
            else{
                tvRating.text = "Belum ada penilaian"
            }

            btnOpenMaps.setOnClickListener {
                btnOpenMaps.visibility = View.GONE
                googleMap.visibility = View.VISIBLE
            }

        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()
    }

    fun getHasilOlahDataLongLat(dataTukang: Tukang, dataNilai: DetailKategoriNilai): String {

        val lat1 = dataTukang.latitude_tukang
        val long1 = dataTukang.longitude_tukang
        val lat2 = dataNilai.latitude_tukang
        val long2 = dataNilai.longitude_tukang

        Log.d("Lat1 : ", lat1.toString())
        Log.d("Long1 : ", long1.toString())
        Log.d("Lat2 : ", lat2.toString())
        Log.d("Long2 : ", long2.toString())

        if (lat1 != null && long2 != null){
            val lati1 : Double = lat1!!.toDouble()
            val longi1 : Double = long1!!.toDouble()
            val lati2 : Double = lat2!!.toDouble()
            val longi2 : Double = long2!!.toDouble()

            Log.d("Lati1 : ", lati1.toString())
            Log.d("Longi1 : ", longi1.toString())
            Log.d("Lati2 : ", lati2.toString())
            Log.d("Longi2 : ", longi2.toString())

            val longDiff : Double = longi1 - longi2
            Log.d("Different Longitude", longDiff.toString())

            var distance: Double = (Math.sin(deg2rad(lati1)) * Math.sin(deg2rad(lati2))) + (Math.cos(deg2rad(lati1)) * Math.cos(deg2rad(lati2)) * Math.cos(deg2rad(longDiff)))

            distance = Math.acos(distance)
            distance = rad2deg(distance)
            distance = distance * 60 * 1.1515
            distance = distance * 1.609344
            Log.d("in km : ", distance.toString())

            val df = DecimalFormat("#.#")
            Log.d("with decimal format: ", (df.format(distance)).toString())

            return (df.format(distance)).toString()
        } else{

            var hasil = 0

            return hasil.toString()
        }

    }

    private fun deg2rad(lat: Double): Double {
        return (lat * Math.PI / 180.0)
    }

    private fun rad2deg(distance: Double): Double {
        return (distance * 180.0 / Math.PI)
    }

    private fun setupRvDetailKategori(data: List<DetailKategoriNilai>?) {
        val listKategoriInDetailAdapter = ListKategoriInDetailAdapter()
        listKategoriInDetailAdapter.setDetailKategori(data!!)

        binding.rvDetailKategori.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listKategoriInDetailAdapter
        }

        listKategoriInDetailAdapter.setOnItemClickCallback(object : ListKategoriInDetailAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DetailKategoriNilai) {

                Toast.makeText(this@DetailTukangActivity, "Kategori : " + data.nama_kategori , Toast.LENGTH_SHORT).show()

//                val detailKategoriInPelangganFragment = DetailKategoriInPelangganFragment()
//
//                val bundle = Bundle()
//                val bundleData = Gson().toJson(data)
//                bundle.putString("EXTRA_DETAIL_KATEGORI", bundleData)
//                detailKategoriInPelangganFragment.arguments = bundle
//
//                detailKategoriInPelangganFragment.show(supportFragmentManager,
//                    DetailTukangPelangganActivity.SHOW_DETAIL_KETEGORI
//                )

            }
        })
    }

    private fun fetchLocation() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE)
            return
        }

        val task = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null){
                currentLocation = location
                val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)
                supportMapFragment.getMapAsync(this)
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getMyLocation()
    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mMap!!.isMyLocationEnabled = true

        mMap!!.uiSettings.isZoomControlsEnabled = true

        mMap!!.setOnMyLocationChangeListener { location ->
            currentLocation = location
            val latlng = LatLng(location.latitude, location.longitude)

            //tukang nilai
            val tukangLat = extraDataNilai?.latitude_tukang
            val tukangLng = extraDataNilai?.longitude_tukang

            Log.d("tukangLat : ", tukangLat.toString())
            Log.d("tukangLng : ", tukangLng.toString())

            val tukangLatDouble : Double = tukangLat!!.toDouble()
            val tukangLngDouble : Double = tukangLng!!.toDouble()

            Log.d("tukangLatDouble : ", tukangLatDouble.toString())
            Log.d("tukangLngDouble : ", tukangLngDouble.toString())

            val tukangLatLng = LatLng(tukangLatDouble, tukangLngDouble)
            Log.d("tukangLatLng : ", tukangLatLng.toString())

            val iconTukang = BitmapDescriptorFactory.fromResource(R.drawable.ic_home_taylor_3d)

            val markerTukang = MarkerOptions().position(tukangLatLng).title("Lokasi Tukang")
                .snippet(getAddress(tukangLatDouble, tukangLngDouble))
                .icon(iconTukang)
            mMap!!.addMarker(markerTukang)

            mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(tukangLatLng, 13f))

            //tukang user

            val tukangUserLat = extraDataTukang?.latitude_tukang
            val tukangUserLng = extraDataTukang?.longitude_tukang

            if (tukangUserLat != null && tukangUserLng != null){

                Log.d("tukangUserLat : ", tukangUserLat.toString())
                Log.d("tukangUserLng : ", tukangUserLng.toString())

                val tukangUserLatDouble = tukangUserLat!!.toDouble()
                val tukangUserLngDouble = tukangUserLng!!.toDouble()

                Log.d("tukangUserLatDouble", tukangUserLatDouble.toString())
                Log.d("tukangUserLngDouble", tukangUserLngDouble.toString())

                val tukangUserLatLng = LatLng(tukangUserLatDouble, tukangUserLngDouble)
                Log.d("tukangUserLatLng", tukangUserLatLng.toString())

                val iconPengguna = BitmapDescriptorFactory.fromResource(R.drawable.ic_position_3d_2)

                val markerOptions = MarkerOptions().position(tukangUserLatLng)
                    .title("Lokasi Saya")
                    .snippet(getAddress(tukangUserLatDouble, tukangUserLngDouble))
                    .icon(iconPengguna)
                mMap!!.addMarker(markerOptions)

                mMap!!.addPolyline(
                    PolylineOptions().add(tukangLatLng, tukangUserLatLng)
                        .width // below line is use to specify the width of poly line.
                            (7f) // below line is use to add color to our poly line.
                        .color(Color.RED) // below line is to make our poly line geodesic.
                        .geodesic(true)
                )

            } else{

                val iconPengguna = BitmapDescriptorFactory.fromResource(R.drawable.ic_position_3d_2)

                val markerOptions = MarkerOptions().position(latlng)
                    .title("Lokasi Saya")
                    .snippet(getAddress(latlng.latitude, latlng.longitude))
                    .icon(iconPengguna)
                mMap!!.addMarker(markerOptions)

                mMap!!.addPolyline(
                    PolylineOptions().add(tukangLatLng, latlng)
                        .width // below line is use to specify the width of poly line.
                            (7f) // below line is use to add color to our poly line.
                        .color(Color.RED) // below line is to make our poly line geodesic.
                        .geodesic(true)
                )
            }

        }

    }

    private fun getAddress(latitude: Double, longitude: Double): String? {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].getAddressLine(0).toString()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLocation()
                }
            }
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}