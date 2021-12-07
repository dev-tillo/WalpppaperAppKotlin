package com.example.walpppaperappkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walpppaperappkotlin.databinding.ItemPhotoBinding
import com.example.walpppaperappkotlin.models.RandomClass
import com.example.walpppaperappkotlin.models.Result

class HomeAdapter(var context: Context, var listener: onItemClicked) :
    ListAdapter<RandomClass, HomeAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<RandomClass>() {
        override fun areItemsTheSame(
            oldItem: RandomClass,
            newItem: RandomClass,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: RandomClass,
            newItem: RandomClass,
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemImageBinding.apply {

            Glide.with(context).load(item.urls.regular).into(image)


            image.setOnClickListener {
                listener.onCliked(item, position)
            }
        }
    }

    inner class Vh(var itemImageBinding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(itemImageBinding.root) {
    }

    interface onItemClicked {
        fun onCliked(url: RandomClass, position: Int)
    }
}