package com.example.walpppaperappkotlin.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.databace.Entity
import com.example.walpppaperappkotlin.databinding.ItemPhotoBinding
import com.example.walpppaperappkotlin.models.RandomClass

class EntityAdapter(var context: Context, var listenr: onItemClicked) :
    ListAdapter<Entity, EntityAdapter.Vh>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<Entity>() {
        override fun areItemsTheSame(
            oldItem: Entity,
            newItem: Entity,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Entity,
            newItem: Entity,
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class Vh(var itemImageBinding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(itemImageBinding.root) {
    }

    interface onItemClicked {
        fun onCliked(url: Entity)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.itemImageBinding.apply {

            Glide.with(context).load(item.url).placeholder(R.drawable.ic_launcher_foreground)
                .into(image)

            image.setOnClickListener {
                listenr.onCliked(item)
            }
        }
    }
}