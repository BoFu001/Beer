package com.bofu.coroutinesretrofitmvvm.models

data class ViewState (
    val isLoading: Boolean,
    val hasConnection: Boolean,
    val emptyResult: Boolean
)