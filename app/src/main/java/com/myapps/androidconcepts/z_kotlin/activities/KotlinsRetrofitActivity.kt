package com.myapps.androidconcepts.z_kotlin.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myapps.androidconcepts.Helpers.Utilities
import com.myapps.androidconcepts.HttpCalls.MainApplication
import com.myapps.androidconcepts.R
import com.myapps.androidconcepts.z_kotlin.adapters.KotlinAdapter
import com.myapps.androidconcepts.z_kotlin.models.KotlinModel
import kotlinx.android.synthetic.main.activity_kotlins_retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class KotlinsRetrofitActivity : AppCompatActivity() {
    //var modelList: List<KotlinModel> = ArrayList<KotlinModel>()     //either we can use this or below line.
    lateinit var modelList: List<KotlinModel>
    lateinit var adapter: KotlinAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlins_retrofit)

        setSupportActionBar(kt_retrofit_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        kt_RecyclerView.layoutManager = LinearLayoutManager(this)
        kt_RecyclerView.hasFixedSize()


        getAllData()


        kt_swipeRefreshLay.setOnRefreshListener {
            kt_swipeRefreshLay.isRefreshing = false
            Collections.shuffle(modelList, Random(System.currentTimeMillis()))
            adapter = KotlinAdapter(modelList, this@KotlinsRetrofitActivity)
            kt_RecyclerView.adapter = adapter
        }
    }

    private fun getAllData() {

        val call: Call<List<KotlinModel>> =
            MainApplication.getKotlinRestClient().getMyApi().getData()

        call.enqueue(object : Callback<List<KotlinModel>> {
            override fun onResponse(
                call: Call<List<KotlinModel>>,
                response: Response<List<KotlinModel>>
            ) {
                if (response.isSuccessful) {
                    //modelList = response.body() as ArrayList<KotlinModel>       //either we can use this or below line.
                    modelList = response.body()!!
                    adapter = KotlinAdapter(modelList, this@KotlinsRetrofitActivity)
                    kt_RecyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<KotlinModel>>, t: Throwable) {
                Toast.makeText(
                    this@KotlinsRetrofitActivity,
                    "Something Went Wrong..!" + t.localizedMessage, Toast.LENGTH_SHORT
                ).show()
            }
        })

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