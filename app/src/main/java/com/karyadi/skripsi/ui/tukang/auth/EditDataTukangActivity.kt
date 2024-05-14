package com.karyadi.skripsi.ui.tukang.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
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
import com.karyadi.skripsi.R
import com.karyadi.skripsi.model.Tukang
import com.karyadi.skripsi.ui.main.HomeTukangActivity
import com.karyadi.skripsi.ui.tukang.auth.viewmodel.AuthTukangViewModel
import com.karyadi.skripsi.utils.PrefHelper
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_ID_TUKANG
import com.karyadi.skripsi.utils.PrefHelper.Companion.PREF_NAMA_TUKANG
import com.karyadi.skripsi.utils.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.karyadi.skripsi.databinding.ActivityEditDataTukangBinding
import java.io.IOException
import java.lang.IndexOutOfBoundsException
import java.util.*

class EditDataTukangActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraIdleListener   {

    private lateinit var binding: ActivityEditDataTukangBinding
    private lateinit var currentLocation : Location
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    val REQUEST_CODE = 102
    lateinit var prefHelper: PrefHelper

    companion object {
        const val EXTRA_DATA_TUKANG = "EXTRA_DATA_TUKANG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataTukangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        fetchLocation()

        prefHelper = PrefHelper(this)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[AuthTukangViewModel::class.java]

        val extraData: Tukang? = intent.extras?.getParcelable(EXTRA_DATA_TUKANG)

        viewModel.apply {

            dataTukangVM.observe(this@EditDataTukangActivity,{
                binding.tvNamaTukang.text = it!!.nama_tukang
                binding.etNama.setText(it.nama_tukang)
                binding.etNamaToko.setText(it.nama_jasa)
                binding.etTelepon.setText(it.telp_tukang)
                binding.tvAlamatToko.setText(it.alamat_tukang)
                binding.etAlamatToko.setText(it.alamat_tukang)
                binding.tvLatitude.setText(it.latitude_tukang)
                binding.tvLongitude.setText(it.longitude_tukang)
                binding.etKet.setText(it.keterangan_jasa)
//                binding.tvJamBuka.text = it.jam_buka
//                binding.tvJamTutup.text = it.jam_tutup

            })
            viewModel.getDataTukangById(extraData!!)


            dataTukang.observe(this@EditDataTukangActivity, {
                val move = Intent(this@EditDataTukangActivity, HomeTukangActivity::class.java)
                move.putExtra("EXTRA_LOGIN_TUKANG", extraData)
                startActivity(move)
//                finish()
            })

            messageSuccess.observe(this@EditDataTukangActivity, {
                Toast.makeText(this@EditDataTukangActivity, it, Toast.LENGTH_SHORT).show()
            })

            showProgress.observe(this@EditDataTukangActivity, {
                if (it) {
                    // show progress
                } else {
                    // hide progress
                }
            })

            messageFailed.observe(this@EditDataTukangActivity, {
                Toast.makeText(this@EditDataTukangActivity, it, Toast.LENGTH_SHORT).show()
            })
        }


//        binding.tvNamaTukang.text = extraData!!.nama_tukang
//        binding.etNama.setText(extraData.nama_tukang)
//        binding.etNamaToko.setText(extraData.nama_jasa)
//        binding.etTelepon.setText(extraData.telp_tukang)
//        binding.tvAlamatToko.setText(extraData.alamat_tukang)
//        binding.etAlamatToko.setText(extraData.alamat_tukang)
//        binding.tvLatitude.setText(extraData.latitude_tukang)
//        binding.tvLongitude.setText(extraData.longitude_tukang)
//        binding.etKet.setText(extraData.keterangan_jasa)
//        binding.tvJamBuka.text = extraData.jam_buka
//        binding.tvJamTutup.text = extraData.jam_tutup

        binding.tvPilihAlamat.visibility = View.GONE
        binding.googleMap.visibility = View.GONE

        binding.btnGetMaps.setOnClickListener {
            binding.tvPilihAlamat.visibility = View.VISIBLE
            binding.googleMap.visibility = View.VISIBLE
            binding.tvPerbaruiAlamat.visibility = View.GONE
            binding.btnGetMaps.visibility = View.GONE
        }

//        binding.btnJamBuka.setOnClickListener {
//            val cal = Calendar.getInstance()
//            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
//                cal.set(Calendar.HOUR_OF_DAY, hour)
//                cal.set(Calendar.MINUTE, minute)
//                binding.tvJamBuka.text = SimpleDateFormat("HH:mm").format(cal.time)
//            }
//            TimePickerDialog(
//                this,
//                timeSetListener,
//                cal.get(Calendar.HOUR_OF_DAY),
//                cal.get(Calendar.MINUTE),
//                true
//            ).show()
//        }

//        binding.btnJamTutup.setOnClickListener {
//            val cal = Calendar.getInstance()
//            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
//                cal.set(Calendar.HOUR_OF_DAY, hour)
//                cal.set(Calendar.MINUTE, minute)
//                binding.tvJamTutup.text = SimpleDateFormat("HH:mm").format(cal.time)
//            }
//            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
//        }


        binding.btnCancelDataTukang.setOnClickListener {
            finish()
        }

        binding.btnSimpanDataTukang.setOnClickListener {

            val namaTukang = binding.etNama.text.toString().trim()
            val teleponTukang = binding.etTelepon.text.toString().trim()
            val namaToko = binding.etNamaToko.text.toString().trim()
            val alamatTukang = binding.etAlamatToko.text.toString().trim()
            val lat = binding.tvLatitude.text.toString().trim()
            val long = binding.tvLongitude.text.toString().trim()
            val keterangan = binding.etKet.text.toString().trim()
//            val jamBuka = binding.tvJamBuka.text.toString()
//            val jamTutup = binding.tvJamTutup.text.toString()

            var jangkauan: String? = ""
            if (binding.cbJang1.isChecked) {
                jangkauan += "Anak-anak, "
            }
            if (binding.cbJang2.isChecked) {
                jangkauan += "Ramaja, Dewasa"
            }


            val dataTukang = Tukang(
                extraData!!.id_tukang,
                namaTukang,
                extraData.email_tukang,
                extraData.password_tukang,
                alamatTukang,
                "",
                namaToko,
                teleponTukang,
                keterangan,
//                getHariBuka(),
//                jamBuka,
//                jamTutup,
                jangkauan,
                lat,
                long,
                getSpesifikasi(),
            )


            Log.d("Data Tukang : ", dataTukang.toString())

            prefHelper.put(PREF_ID_TUKANG, extraData.id_tukang!!)
            prefHelper.put(PREF_NAMA_TUKANG, namaTukang)

            viewModel.updateTukang(dataTukang)
        }

    }

//    fun getHariBuka(): String {
//        var hariBuka = ""
//        if (binding.cbHari1.isChecked) {
//            hariBuka += "Senin, "
//        }
//        if (binding.cbHari2.isChecked) {
//            hariBuka += "Selasa, "
//        }
//        if (binding.cbHari3.isChecked) {
//            hariBuka += "Rabu, "
//        }
//        if (binding.cbHari4.isChecked) {
//            hariBuka += "Kamis, "
//        }
//        if (binding.cbHari5.isChecked) {
//            hariBuka += "Jum'at, "
//        }
//        if (binding.cbHari6.isChecked) {
//            hariBuka += "Sabtu, "
//        }
//        if (binding.cbHari7.isChecked) {
//            hariBuka += "Minggu"
//        }
//        return hariBuka
//    }

    fun getSpesifikasi(): String {
        var spesifikasi = ""
        if (binding.cbSpek1.isChecked) {
            spesifikasi += "Renovasi, "
        }
        if (binding.cbSpek2.isChecked) {
            spesifikasi += "bangun pryek"
        }
        return spesifikasi
    }

    private fun fetchLocation() {

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
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
        mMap!!.setOnCameraMoveListener(this)
        mMap!!.setOnCameraMoveStartedListener(this)
        mMap!!.setOnCameraIdleListener(this)

        mMap!!.setOnMyLocationChangeListener { location ->
            currentLocation = location
        }

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

    override fun onLocationChanged(location: Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses : List<Address>? = null
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }catch (e: IOException){
            e.printStackTrace()
        }
        setAddress(addresses!![0])
    }

    private fun setAddress(addresses: Address) {
        if (addresses != null){
            if (addresses.getAddressLine(0) != null){
                binding.tvAlamatToko.setText(addresses.getAddressLine(0))
                binding.etAlamatToko.visibility = View.VISIBLE
                binding.etAlamatToko.setText(addresses.getAddressLine(0))
            }
            if (addresses.getAddressLine(1) != null){
                binding.tvAlamatToko.setText(binding.tvAlamatToko.text.toString() + addresses.getAddressLine(1))
                binding.etAlamatToko.visibility = View.VISIBLE
                binding.etAlamatToko.setText(binding.etAlamatToko.text.toString() + addresses.getAddressLine(1))
            }
        }
    }

    override fun onCameraMove() {
    }

    override fun onCameraMoveStarted(p0: Int) {
    }

    override fun onCameraIdle() {
        var addresses: List<Address>? = null
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            addresses = geocoder.getFromLocation( mMap!!.getCameraPosition().target.latitude, mMap!!.getCameraPosition().target.longitude, 1)

            val lat = mMap!!.cameraPosition.target.latitude
            val lng = mMap!!.cameraPosition.target.longitude

            binding.tvLatitude.text = lat.toString()
            binding.tvLongitude.text = lng.toString()

            setAddress(addresses!![0])
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
        }catch (e: IOException){
            e.printStackTrace()
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