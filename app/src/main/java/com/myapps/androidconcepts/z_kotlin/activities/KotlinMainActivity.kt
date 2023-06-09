package com.myapps.androidconcepts.z_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.myapps.androidconcepts.Activities.MainActivity
import com.myapps.androidconcepts.Helpers.Utilities
import com.myapps.androidconcepts.R
import kotlinx.android.synthetic.main.activity_kotlin_main.*

class KotlinMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tv_textOne.text = "Welcome to Kotlin Concepts"

        btn_openActivity.setOnClickListener {
            if (et_name.text.toString().trim().isNullOrEmpty()) {
                et_name.error = "Name Required"
            } else if (et_description.text.toString().trim().isNullOrEmpty()) {
                et_description.error = "Description Required"
            } else {
                val intent = Intent(this, SecondActivity::class.java)
                intent.putExtra("NAME", et_name.text.toString().trim())
                intent.putExtra("BODY", et_description.text.toString().trim())
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        et_name.text.clear()
        et_description.text.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.kotlin_menu_options, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {

            R.id.retrofit -> {
                startActivity(Intent(this, KotlinsRetrofitActivity::class.java))
            }

            R.id.viewPager -> {

            }

            R.id.listView -> {

            }

            R.id.adMob -> {

            }

            R.id.googleMaps -> {
                startActivity(Intent(this, KotlinsMapsActivity::class.java))
            }

            R.id.openCamera -> {
                startActivity(Intent(this, KotlinCameraActivity::class.java))
            }

            R.id.sharedPreference -> {

            }

            else -> {
                intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }

        return true
    }


    override fun onResume() {
        super.onResume()
        Utilities.onResumeToRegister(this)
    }

    override fun onPause() {
        super.onPause()
        Utilities.onPauseToUnRegister(this)
    }
}