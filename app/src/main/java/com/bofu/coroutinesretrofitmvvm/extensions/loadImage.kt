package com.bofu.coroutinesretrofitmvvm.extensions

import android.widget.ImageView
import com.bofu.coroutinesretrofitmvvm.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(uri: String?){
    val options = RequestOptions()
        .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}