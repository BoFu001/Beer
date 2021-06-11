package com.bofu.coroutinesretrofitmvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bofu.coroutinesretrofitmvvm.databinding.RowBeerBinding
import com.bofu.coroutinesretrofitmvvm.extensions.loadImage
import com.bofu.coroutinesretrofitmvvm.models.Beer

class MainAdapter(
    private val item: ArrayList<Beer>,
    private val onClickListener: (Beer, Int) -> Unit
): RecyclerView.Adapter<MainAdapter.MainAdapter>(){

    fun update(newData: ArrayList<Beer>) {
        item.clear()
        item.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter {
        val binding = RowBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainAdapter(binding)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MainAdapter, position: Int) {
        holder.beerName.text = item[position].name
        holder.beerTagline.text = item[position].tagline
        holder.beerYear.text = item[position].year.toString()
        holder.beerImg.loadImage(item[position].image_url)

        holder.itemView.setOnClickListener {
            onClickListener(item[position], position)
        }
    }

    class MainAdapter(binding: RowBeerBinding) : RecyclerView.ViewHolder(binding.root) {
        var beerName: TextView = binding.mainBeerName
        var beerTagline: TextView = binding.mainBeerTagline
        var beerYear: TextView = binding.mainBeerYear
        var beerImg: ImageView = binding.mainBeerImg
    }

}