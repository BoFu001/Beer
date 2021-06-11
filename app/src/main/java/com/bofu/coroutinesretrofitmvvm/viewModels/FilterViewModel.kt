package com.bofu.coroutinesretrofitmvvm.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bofu.coroutinesretrofitmvvm.R
import com.bofu.coroutinesretrofitmvvm.models.YearFilter


class FilterViewModel(application: Application) : AndroidViewModel (application) {

    private val TAG = javaClass.simpleName
    val yearFilter = MutableLiveData<YearFilter>()

    init {
        setData()
    }

    private fun setData() {
        val initialValues = getApplication<Application>().applicationContext.resources.getIntArray(R.array.initial_slider_values)
        yearFilter.value = YearFilter(initialValues[0], initialValues[1])
    }

    fun update(filter: YearFilter){
        Log.d(TAG, "valueFrom: ${filter.start}, valueTo ${filter.end}")
        yearFilter.value =  filter
    }

    fun getYearFilter(): YearFilter?{
        return yearFilter.value
    }

    fun cleanYearFilter(){
        setData()
    }
}





