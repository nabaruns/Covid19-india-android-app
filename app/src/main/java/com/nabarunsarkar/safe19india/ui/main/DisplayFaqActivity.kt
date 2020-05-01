package com.nabarunsarkar.safe19india.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.R
import com.nabarunsarkar.safe19india.databinding.ActivityDisplayFaqBinding
import com.nabarunsarkar.safe19india.model.FaqResponse
import com.nabarunsarkar.safe19india.utils.FaqRvAdapter
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DisplayFaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayFaqBinding

    private val viewModel: FaqViewModel by viewModel()

    var intent_string: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent_string = intent.getStringExtra(EXTRA_MESSAGE2)

        val actionBar = supportActionBar
        actionBar!!.title = "Important FAQs"

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
//                    val state_data = state.data.filter { it.key == intent_string }[intent_string]
                    val state_data = state.data
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

    private fun bind(details: FaqResponse) {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val rvAdapter = FaqRvAdapter(details.faqs)
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

