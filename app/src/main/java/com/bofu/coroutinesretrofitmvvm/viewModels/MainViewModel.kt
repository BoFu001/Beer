package com.bofu.coroutinesretrofitmvvm.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.bofu.coroutinesretrofitmvvm.extensions.isConnectedToNetwork
import com.bofu.coroutinesretrofitmvvm.models.Beer
import com.bofu.coroutinesretrofitmvvm.models.ViewState
import com.bofu.coroutinesretrofitmvvm.models.YearFilter
import com.bofu.coroutinesretrofitmvvm.services.BeerService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel(application: Application) : AndroidViewModel (application) {

    private val TAG = javaClass.simpleName
    private val beerService = BeerService()
    private val data = mutableListOf<Beer>()
    var yearFilter: YearFilter? = null

    // For internal usage
    private val _liveData = MutableLiveData<MutableList<Beer>>()
    // Only publicly expose LiveData, never mutable
    val liveData: LiveData<MutableList<Beer>> = _liveData

    private val _viewState = MutableLiveData(ViewState(isLoading = false, hasConnection = false, emptyResult = false))
    val viewState: LiveData<ViewState> = _viewState

    init {
        fetchData()
    }

    private fun checkConnection(): Boolean {
        val isConnected = getApplication<Application>().applicationContext.isConnectedToNetwork()
        _viewState.value = _viewState.value!!.copy(hasConnection = isConnected)
        return isConnected
    }

    fun fetchData() {

        if(!checkConnection()) return

        Log.d(TAG, ": beginning fetchData")

        viewModelScope.launch {
            beerService.getBeers()
                .onStart {
                    //isLoading.value = true
                    _viewState.value = _viewState.value!!.copy(isLoading = true)
                }
                .catch { exception ->
                    Log.d(TAG, ": Exception: ${exception.localizedMessage}")
                    //hasConnection.value = false
                    _viewState.value = _viewState.value!!.copy(hasConnection = false)
                }
                .onCompletion {
                    //isLoading.value = false
                    _viewState.value = _viewState.value!!.copy(isLoading = false)
                    filterData()
                }.collect {
                    data.add(it)
                }
        }
    }

    fun setFilter(filter: YearFilter?){
        filter?.let {
            yearFilter = filter
            filterData()
        }
    }

    private fun filterData(){
        if(yearFilter == null){
            _liveData.value = data
        } else {
            _liveData.value = data.filter { beer ->
                beer.year >= yearFilter!!.start && beer.year <= yearFilter!!.end
            } as MutableList<Beer>
        }
        _viewState.value = _viewState.value!!.copy(emptyResult = _liveData.value!!.size == 0)
    }



}


