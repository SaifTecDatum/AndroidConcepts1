package com.myapps.androidconcepts.z_kotlin.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.myapps.androidconcepts.R

class KotlinsMapsActivity : AppCompatActivity() {
    private val TAG = "KotlinsMapsActivity"
    lateinit var supportMapFragment: SupportMapFragment
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val rqstCode: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlins_maps)

        supportMapFragment =
            supportFragmentManager.findFragmentById(R.id.supportMapFragment) as SupportMapFragment

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermissions()

    }

    private fun checkPermissions() {
        val accessFineLocation: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val readExtStr: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)


        /*For people just migrating from java, In Kotlin List is by default immutable and mutable version of Lists is called MutableList.

        Hence if you have something like :

        val list: List<String> = ArrayList()
        In this case you will not get an add() method as list is immutable. Hence you will have to declare a MutableList as shown below :

        val list: MutableList<String> = ArrayList()
        Now you will see an add() method and you can add elements to any list.


        Ex:
        val permissionList: MutableList<String> = ArrayList()

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getMyLocation()
        }*/

        val permissionList: MutableList<String> = ArrayList()

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            getMapsLocation()
        }

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        if (readExtStr != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), rqstCode)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == rqstCode) {

            if (grantResults.isNotEmpty()) {
                for (item in permissions.indices) {

                    if (permissions[item] == Manifest.permission.ACCESS_FINE_LOCATION) {
                        if (grantResults[item] == PackageManager.PERMISSION_GRANTED) {
                            getMapsLocation()
                        }
                    } else {
                        //finish()      //triggering all ifElse conditions at a time-someProblem in kotlin's forLoop
                        //Toast.makeText(this, "Permission Denied..!", Toast.LENGTH_SHORT).show()
                    }

                    if (permissions[item] == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        if (grantResults[item] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
                        }
                    }

                    if (permissions[item] == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        if (grantResults[item] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "NA", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        }
    }

    private fun getMapsLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        val task: Task<Location> = fusedLocationProviderClient.lastLocation

        task.addOnSuccessListener {
            var latLng: LatLng = LatLng(it.latitude, it.longitude)

            supportMapFragment.getMapAsync {
                var markerOptions: MarkerOptions = MarkerOptions().position(latLng).title("You are Here..!")
                it.addMarker(markerOptions)
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
            }
        }
    }

}