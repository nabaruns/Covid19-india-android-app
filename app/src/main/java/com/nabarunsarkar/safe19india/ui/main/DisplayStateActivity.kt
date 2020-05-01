package com.nabarunsarkar.safe19india.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.gson.Gson
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.model.ApiResponse
import com.nabarunsarkar.safe19india.model.TotalDetails
import com.nabarunsarkar.safe19india.utils.RvAdapter
import kotlinx.android.synthetic.main.activity_display_state.*
import java.io.*
import kotlin.collections.ArrayList

class DisplayStateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_state)

        val actionBar = supportActionBar
        actionBar!!.title = "State wise"

        // Get the Intent that started this activity and extract the string
        val intent_string: String? = intent.getStringExtra(EXTRA_MESSAGE)

        if (intent_string != null) {
            val uri: Uri = Uri.parse(intent_string)

            val input: InputStream? = getContentResolver().openInputStream(uri)
            val inputAsString = input?.bufferedReader().use { it?.readText() }

            if (inputAsString != null) {

                val gson = Gson()

                var obj = gson.fromJson(inputAsString, ApiResponse::class.java)


                //Part1
                val totalConfirmedEntries = ArrayList<Entry>()
                val dailyConfirmedEntries = ArrayList<Entry>()
                val totalDeceasedEntries = ArrayList<Entry>()

                //Part2
                val numbersIterator = obj.casesTimeSeries.iterator()
                var xval = 0
                while (numbersIterator.hasNext()) {
                    val item = numbersIterator.next()

                    totalConfirmedEntries.add(Entry(xval.toFloat(), item.totalconfirmed.toFloat()))
                    dailyConfirmedEntries.add(Entry(xval.toFloat(), item.dailyconfirmed.toFloat()))
                    totalDeceasedEntries.add(Entry(xval.toFloat(), item.totaldeceased.toFloat()))
                    xval++
                }

                //Part3
                val vl = LineDataSet(totalConfirmedEntries, "Cumulative positive count")
                val vldaily = LineDataSet(dailyConfirmedEntries, "Daily positive count")
                val vlTotalDeath = LineDataSet(totalDeceasedEntries, "Cumulative death count")

                //Part4
                vl.setDrawValues(false)
                vl.setDrawFilled(true)
                vl.lineWidth = 3f
                vl.fillColor = R.color.gray
                vl.fillAlpha = R.color.dark_red

                //Part5
                lineChart.xAxis.labelRotationAngle = 0f

                //Part6
                lineChart.data = LineData(vl)

                //Part7
                lineChart.axisRight.isEnabled = false
                lineChart.xAxis.axisMaximum = xval + 0.1f

                //Part8
                lineChart.setTouchEnabled(true)
                lineChart.setPinchZoom(true)

                //Part9
                lineChart.description.text = "Days"
                lineChart.setNoDataText("No data yet!")

                //Part10
                lineChart.animateX(1800, Easing.EaseInExpo)

                //Part11
                val markerView = CustomMarker(this, R.layout.custom_marker_view)
                lineChart.marker = markerView


                //Part4
                vldaily.setDrawValues(false)
                vldaily.setDrawFilled(true)
                vldaily.lineWidth = 3f
                vldaily.fillColor = R.color.gray
                vldaily.fillAlpha = R.color.dark_red

                //Part5
                lineChart2.xAxis.labelRotationAngle = 0f

                //Part6
                lineChart2.data = LineData(vldaily)

                //Part7
                lineChart2.axisRight.isEnabled = false
                lineChart2.xAxis.axisMaximum = xval + 0.1f

                //Part8
                lineChart2.setTouchEnabled(true)
                lineChart2.setPinchZoom(true)

                //Part9
                lineChart2.description.text = "Days"
                lineChart2.setNoDataText("No forex yet!")

                //Part10
                lineChart2.animateX(1800, Easing.EaseInExpo)

                //Part11
                lineChart2.marker = markerView


                //Part4
                vlTotalDeath.setDrawValues(false)
                vlTotalDeath.setDrawFilled(true)
                vlTotalDeath.lineWidth = 3f
                vlTotalDeath.fillColor = R.color.gray
                vlTotalDeath.fillAlpha = R.color.dark_red

                //Part5
                lineChart3.xAxis.labelRotationAngle = 0f

                //Part6
                lineChart3.data = LineData(vlTotalDeath)

                //Part7
                lineChart3.axisRight.isEnabled = false
                lineChart3.xAxis.axisMaximum = xval + 0.1f

                //Part8
                lineChart3.setTouchEnabled(true)
                lineChart3.setPinchZoom(true)

                //Part9
                lineChart3.description.text = "Days"
                lineChart3.setNoDataText("No forex yet!")

                //Part10
                lineChart3.animateX(1800, Easing.EaseInExpo)

                //Part11
                lineChart3.marker = markerView




                val stateWiseList = obj.stateWiseDetails.drop(1)
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.isNestedScrollingEnabled = false;
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                val rvAdapter = RvAdapter(stateWiseList as ArrayList<TotalDetails>, intent_string)
                recyclerView.adapter = rvAdapter;

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        return
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // API 5+ solution
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
