package com.myapps.androidconcepts.z_kotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.myapps.androidconcepts.Helpers.Utilities
import com.myapps.androidconcepts.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setSupportActionBar(scndToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tv_receiverName.append("Hi! " + intent.getStringExtra("NAME"))
        //aboveLine&belowLineGivesSameOutput, butAppend&setTextAreDifferent.
        tv_receiverDescrptn.text = "About: " + intent.getStringExtra("BODY")

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