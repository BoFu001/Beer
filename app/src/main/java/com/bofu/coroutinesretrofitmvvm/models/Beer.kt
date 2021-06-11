package com.bofu.coroutinesretrofitmvvm.models

import com.bofu.coroutinesretrofitmvvm.extensions.getYear

data class Beer (
    var name: String,
    val tagline: String,
    val image_url: String,
    var first_brewed: String
) {
    var year:Int
        get() = this.getYear()
        set(value) {
            first_brewed = value.toString()
        }
}