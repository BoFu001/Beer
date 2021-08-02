package com.bofu.coroutinesretrofitmvvm.adapters

import android.animation.AnimatorInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bofu.coroutinesretrofitmvvm.R
import com.bofu.coroutinesretrofitmvvm.databinding.RowBeerBinding
import com.bofu.coroutinesretrofitmvvm.extensions.loadImage
import com.bofu.coroutinesretrofitmvvm.models.Beer

class MainAdapter(
    private val item: MutableList<Beer>,
    private val onClickListener: (Beer, Int) -> Unit
): RecyclerView.Adapter<MainAdapter.MainHolder>(){

    private var previousPosition = -1

    fun update(newData: MutableList<Beer>) {
        item.clear()
        item.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = RowBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun getItemCount() = item.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.beerName.text = item[position].name
        holder.beerTagline.text = item[position].tagline
        holder.beerYear.text = item[position].year.toString()
        holder.beerImg.loadImage(item[position].image_url)

        holder.itemView.setOnClickListener {
            onClickListener(item[position], position)
        }

        // call Animation function
        setAnimation(holder.itemView, position)

        println("zzz: " + position + ", " +  ", " + item[position].name)
    }

    class MainHolder(binding: RowBeerBinding) : RecyclerView.ViewHolder(binding.root) {
        val beerName: TextView = binding.mainBeerName
        val beerTagline: TextView = binding.mainBeerTagline
        val beerYear: TextView = binding.mainBeerYear
        val beerImg: ImageView = binding.mainBeerImg
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {

        // scroll to bottom
        if (position > previousPosition) {

            // animation on bottom
//            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_up)
//            viewToAnimate.startAnimation(animation)


            AnimatorInflater.loadAnimator(viewToAnimate.context, R.animator.fadein_up).apply {
                setTarget(viewToAnimate)
                start()
            }


        }

        // scroll to top
        else {

            // animation on top
//            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_down)
//            viewToAnimate.startAnimation(animation)

            AnimatorInflater.loadAnimator(viewToAnimate.context, R.animator.fadein_down).apply {
                setTarget(viewToAnimate)
                start()
            }



        }


        previousPosition = position
    }

}