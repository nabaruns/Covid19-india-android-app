package com.nabarunsarkar.safe19india.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.gson.Gson
import com.nabarunsarkar.safe19india.databinding.ActivityMainBinding
import com.nabarunsarkar.safe19india.model.ApiResponse
import com.nabarunsarkar.safe19india.model.TotalDetails
import com.nabarunsarkar.safe19india.utils.State
import com.nabarunsarkar.safe19india.utils.getPeriod
import com.nabarunsarkar.safe19india.worker.NotificationWorker
import com.nabarunsarkar.safe19india.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import kotlin.text.Charsets.UTF_8


const val EXTRA_MESSAGE = "com.nabarunsarkar.covid19notify.MESSAGE"
const val EXTRA_MESSAGE2 = "com.nabarunsarkar.covid19notify.MESSAGE2"

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initWorker()

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
    }

    private fun initData() {
        viewModel.covidLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> binding.swipeRefreshLayout.isRefreshing = true
                is State.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    bind(state.data.stateWiseDetails[0])
//                    binding.bindData.text = state.data.stateWiseDetails.toString()
                    saveData(state.data)
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.button.isEnabled = true
                }
            }
        })

        loadData()
    }

    private fun bind(details: TotalDetails) {
        binding.textConfirmed.text = details.confirmed
        binding.textActive.text = details.active
        binding.textRecovered.text = details.recovered
        binding.textDeceased.text = details.deaths

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        supportActionBar?.subtitle =
            "Last updated: ${getPeriod(simpleDateFormat.parse(details.lastUpdatedTime))}"
    }

    private fun loadData() {
        viewModel.getData()
    }

    private fun saveData(input: ApiResponse) {
        val gson = Gson()
        val json = gson.toJson(input)

        val outputFile = File.createTempFile("api_response", null, this.cacheDir);

        if (outputFile.exists()) {
            outputFile.delete()
        }
        else {
            outputFile.parentFile?.mkdirs()
        }

        val inputStream = ByteArrayInputStream(json.toByteArray(UTF_8))

        val outputStream = FileOutputStream(outputFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        val uri: Uri = Uri.fromFile(outputFile)

        binding.bindData.text = uri.toString()
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notificationWorkRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            JOB_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            notificationWorkRequest
        )
    }

    companion object {
        const val JOB_TAG = "notificationWorkTag"
    }

    /** Called when the user taps the Send button */
    fun sendMessage(view: View) {
        val editText = binding.bindData
        val message = editText.text.toString()
        val intent = Intent(this, DisplayStateActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the Send button */
    fun openEssentialActivity(view: View) {
        val message = "none"
        val intent = Intent(this, DisplayEssentialsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the Send button */
    fun openAppResourceActivity(view: View) {
        val message = "none"
        val intent = Intent(this, DisplayAppsActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the Send button */
    fun openFAQActivity(view: View) {
        val message = "none"
        val intent = Intent(this, DisplayFaqActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, message)
        }
        startActivity(intent)
    }

    /** Called when the user taps the About button in menu */
    fun openAbout() {
        val intent = Intent(this, AboutActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "About")
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.about ->         //add the function to perform here
                openAbout()
        }
        return super.onOptionsItemSelected(item)
    }
}
