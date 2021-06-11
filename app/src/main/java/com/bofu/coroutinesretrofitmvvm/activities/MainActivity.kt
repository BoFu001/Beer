package com.bofu.coroutinesretrofitmvvm.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bofu.coroutinesretrofitmvvm.R
import com.bofu.coroutinesretrofitmvvm.adapters.MainAdapter
import com.bofu.coroutinesretrofitmvvm.databinding.ActivityMainBinding
import com.bofu.coroutinesretrofitmvvm.models.Beer
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.viewModels.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val resultContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val jsonYearFilter = it.data?.getStringExtra("jsonFilterData")
            val yearFilter = Gson().fromJson(jsonYearFilter, YearFilter::class.java)
            mainViewModel.filterData(yearFilter)
        }
    }

    private val TAG = javaClass.simpleName
    private val mainViewModel: MainViewModel by viewModels()
    private val mainAdapter = MainAdapter(ArrayList(), this::selectBeer)

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mainRecyclerViewSetup()
        mainViewModelSetup()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.filter -> {
            val intent = Intent(this, FilterActivity::class.java)
            mainViewModel.yearFilter?.let {
                val jsonFilterData = Gson().toJson(it)
                intent.putExtra("jsonFilterData", jsonFilterData)
            }
            resultContract.launch(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun mainRecyclerViewSetup(){
        binding.mainRecyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun mainViewModelSetup(){
        println("zzz 1")

        mainViewModel.liveData.observe(this) {
            println("zzz 2:" + it.size)
            mainAdapter.update(it)
            noResultView(it.size)
        }
        mainViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
    }

    private fun showProgressBar(bool:Boolean){
        when(bool){
            true -> binding.mainProgressbar.visibility = View.VISIBLE
            false -> binding.mainProgressbar.visibility = View.GONE
        }
    }

    private fun selectBeer(beer: Beer, position:Int){
        Log.d(TAG, "beer: ${beer.name}, position: $position")
    }

    private fun noResultView(size:Int){
        when(size){
            0 -> binding.mainNoResultView.noResultViewLayout.visibility = View.VISIBLE
            else -> binding.mainNoResultView.noResultViewLayout.visibility = View.GONE
        }
    }

}