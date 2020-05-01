package com.nabarunsarkar.safe19india.ui.main

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nabarunsarkar.safe19india.databinding.ActivityDisplayEssentialsBinding
import com.nabarunsarkar.safe19india.model.EssentialsResponse
import com.nabarunsarkar.safe19india.utils.RvEssentialAdapter
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class DisplayEssentialsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayEssentialsBinding

    private val viewModel: EssentialViewModel by viewModel()

    var intent_string: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RvEssentialAdapter

    lateinit var selState: String
    lateinit var selCity: String
    lateinit var selCategory: String

    lateinit var essential_details: List<EssentialsResponse.Resource>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayEssentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
//        recyclerView.isNestedScrollingEnabled = false;
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.visibility = View.GONE

        intent_string = intent.getStringExtra(EXTRA_MESSAGE2)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.title = "Essential Services"

        initData()

//        binding.swipeRefreshLayout.setOnRefreshListener {
//            loadData()
//        }
        binding.swipeRefreshLayout.isEnabled = false

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
                    bind(state.data)
//                    binding.bindData.text = state.data.stateWiseDetails.toString()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        loadData()
    }

    private fun bind(details: EssentialsResponse) {
        essential_details = details.resources

//        println(essential_details.toString())


        initDynamicListSpinner()

        //state_details.forEach { (key, value) -> println("$key = $value") }

    }

    private fun initDynamicListSpinner() {

        // Custom choices
        val state_list: MutableList<CharSequence> = ArrayList()

        var numbersIterator = essential_details.iterator()
        while (numbersIterator.hasNext()) {
            val item = numbersIterator.next()
            state_list.add(item.state)
        }

        // Create an ArrayAdapter with custom choices
        val adapter_state =
            ArrayAdapter(this, R.layout.simple_spinner_item, state_list.distinct())

        // Specify the layout to use when the list of choices appears
        adapter_state.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Set the adapter to th spinner
        binding.stateSpinner.adapter = adapter_state
        binding.stateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                println(binding.stateSpinner.getSelectedItem().toString() + " selected")
                selState = binding.stateSpinner.getSelectedItem().toString()

                initCityListSpinner()
            }
        }
    }

    private fun initCityListSpinner() {

        // Custom choices
        val city_list: MutableList<CharSequence> = ArrayList()

        var numbersIterator = essential_details.iterator()
        while (numbersIterator.hasNext()) {
            val item = numbersIterator.next()
            if (item.state.equals(selState))
                city_list.add(item.city)
        }

        // Create an ArrayAdapter with custom choices
        val adapter_city =
            ArrayAdapter(this, R.layout.simple_spinner_item, city_list.distinct())

        // Specify the layout to use when the list of choices appears
        adapter_city.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Set the adapter to th spinner
        binding.citySpinner.adapter = adapter_city
        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                println(binding.stateSpinner.getSelectedItem().toString() + " selected")

                selCity = binding.citySpinner.getSelectedItem().toString()

                initServiceListSpinner()
            }
        }
    }

    private fun initServiceListSpinner() {

        // Custom choices
        val category_list: MutableList<CharSequence> = ArrayList()

        var numbersIterator = essential_details.iterator()
        while (numbersIterator.hasNext()) {
            val item = numbersIterator.next()
            if (item.state.equals(selState) && item.city.equals(selCity))
                category_list.add(item.category)
        }

        // Create an ArrayAdapter with custom choices
        val adapter_category =
            ArrayAdapter(this, R.layout.simple_spinner_item, category_list.distinct())

        // Specify the layout to use when the list of choices appears
        adapter_category.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        // Set the adapter to th spinner
        binding.categorySpinner.adapter = adapter_category
        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                println(binding.categorySpinner.getSelectedItem().toString() + " selected")

                selCategory = binding.categorySpinner.getSelectedItem().toString()

                binding.buttonSearch.isEnabled = true
            }
        }
    }

    /** Called when the user taps the Send button */
    fun showResult(view: View) {
//        println(selState+ " " + selCity + " " + selCategory)

        val filteredList: MutableList<EssentialsResponse.Resource> = ArrayList()

        var numbersIterator = essential_details.iterator()
        while (numbersIterator.hasNext()) {
            val item = numbersIterator.next()
            if (item.state.equals(selState) && item.city.equals(selCity) && item.category.equals(selCategory))
                filteredList.add(item)
        }
        viewAdapter = RvEssentialAdapter(this, filteredList)

        recyclerView.adapter = viewAdapter

        recyclerView.visibility = View.VISIBLE
    }

    private fun loadData() {
        recyclerView.visibility = View.GONE
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
