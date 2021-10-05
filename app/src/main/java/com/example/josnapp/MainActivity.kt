package com.example.josnapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var result: TextView
    private var curencyDet: Datum? = null
    var currency: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputValue = findViewById<View>(R.id.inputValue) as EditText
        val btnConv = findViewById<View>(R.id.btnConv) as Button
        val spinner = findViewById<View>(R.id.spr) as Spinner
        result = findViewById<View>(R.id.tvResult) as TextView


        val cur = arrayListOf("inr", "usd", "aud", "sar", "cny", "jpy")
        val curVal = arrayListOf<Double>()

        var selected: Int = 0

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, cur
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    selected = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }




        btnConv.setOnClickListener {


            var inputval = inputValue.text.toString()
            currency = inputval.toDouble()

            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val call: Call<Datum>? = apiInterface!!.doGetListResources()
            call?.enqueue(object : Callback<Datum?> {
                override fun onResponse(
                    call: Call<Datum?>?,
                    response: Response<Datum?>
                ) {
                    val resource: Datum? = response.body()
                    when (selected) {
                        0 -> {
                            var res = conver(resource?.eur?.inr?.toDouble())
                            result.text = res.toString()
                        }
                        1 -> {
                            var res = conver(resource?.eur?.usd?.toDouble())
                            result.text = res.toString()
                        }
                        2 -> {
                            var res = conver(resource?.eur?.aud?.toDouble())
                            result.text = res.toString()
                        }
                        3 -> {
                            var res = conver(resource?.eur?.sar?.toDouble())
                            result.text = res.toString()
                        }
                        4 -> {
                            var res = conver(resource?.eur?.cny?.toDouble())
                            result.text = res.toString()
                        }
                        5 -> {
                            var res = conver(resource?.eur?.jpy?.toDouble())
                            result.text = res.toString()
                        }
                    }
                }


                override fun onFailure(call: Call<Datum?>, t: Throwable?) {
                    call.cancel()
                }
            })
        }
    }


    private fun conver(i: Double?): String? {
        var s = 0.0
        if (i != null) {
            s = (i * currency)
        }
        return s.toString()
    }
}
