package com.bofu.coroutinesretrofitmvvm.extensions

import com.bofu.coroutinesretrofitmvvm.models.Beer

fun Beer.getYear(): Int {
    return this.first_brewed.split("/").last().toInt()
}