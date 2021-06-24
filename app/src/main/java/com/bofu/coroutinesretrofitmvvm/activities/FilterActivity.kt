package com.bofu.coroutinesretrofitmvvm.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bofu.coroutinesretrofitmvvm.R
import com.bofu.coroutinesretrofitmvvm.databinding.ActivityFilterBinding
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.viewModels.FilterViewModel
import com.google.gson.Gson

class FilterActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName
    private val filterViewModel: FilterViewModel by viewModels()
    private val binding: ActivityFilterBinding by lazy {
        ActivityFilterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getFilter()
        UISetup()
        filterViewModelSetup()
    }

    private fun getFilter(){
        val jsonStr = intent.getStringExtra("jsonFilterData")
        val jsonYearFilter = Gson().fromJson(jsonStr, YearFilter::class.java)
        jsonYearFilter?.let {
            filterViewModel.update(it)
            binding.filterYearRange.setValues(it.start.toFloat(), it.end.toFloat())
        }
    }

    private fun UISetup(){
        binding.filterButton.setOnClickListener {
            reloadDataInMainActivity()
        }

        binding.cleanButton.setOnClickListener {
            filterViewModel.cleanYearFilter()
            reloadDataInMainActivity()
        }

        binding.filterYearRange.addOnChangeListener { slider, _, _ ->
            val yearFilter = YearFilter(slider.values[0].toInt(), slider.values[1].toInt())
            filterViewModel.update(yearFilter)
        }
    }

    private fun filterViewModelSetup(){
        filterViewModel.yearFilter.observe(this) {
            updateText()
        }
    }

    private fun updateText(){
        val yearFilter = filterViewModel.getYearFilter()
        val str = String.format(getString(R.string.filter_page_text), yearFilter?.start, yearFilter?.end)
        binding.filterTextView.text = str
    }

    private fun reloadDataInMainActivity(){
        val jsonYearFilter = Gson().toJson(filterViewModel.getYearFilter())
        val intent = Intent()
        intent.putExtra("jsonFilterData", jsonYearFilter)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}