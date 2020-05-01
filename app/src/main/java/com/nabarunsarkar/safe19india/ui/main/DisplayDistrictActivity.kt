package com.nabarunsarkar.safe19india.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.databinding.ActivityDisplayDistrictBinding
import com.nabarunsarkar.safe19india.model.DistrictDataDetails
import com.nabarunsarkar.safe19india.utils.MapRvAdapter
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DisplayDistrictActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayDistrictBinding

    private val viewModel: DistrictViewModel by viewModel()

    var intent_string: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayDistrictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent_string = intent.getStringExtra(EXTRA_MESSAGE2)

        val actionBar = supportActionBar
        actionBar!!.title = "State: $intent_string"

        initData()

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
                    val state_data = state.data.filter { it.key == intent_string }[intent_string]
                    if (state_data != null) {
                        bind(state_data)
                    }
//                    binding.bindData.text = state.data.stateWiseDetails.toString()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        loadData()
    }

    private fun bind(details: DistrictDataDetails) {
        var state_details = details.districtData


        //state_details.forEach { (key, value) -> println("$key = $value") }

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val rvAdapter = MapRvAdapter(state_details, intent_string)
        recyclerView.adapter = rvAdapter;

    }

    private fun loadData() {
        viewModel.getData()
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
            R.id.home -> {
                // API 5+ solution
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
