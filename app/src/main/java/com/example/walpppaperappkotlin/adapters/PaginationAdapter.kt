package com.example.walpppaperappkotlin.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.databinding.ItemPhotoBinding
import com.example.walpppaperappkotlin.databinding.ItemProgressBinding
import com.example.walpppaperappkotlin.models.Result
import com.squareup.picasso.Picasso

class PaginationAdapter(
    private val context: Context,
    var list: ArrayList<Result>,
    var listener: onItemClicked,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoadingAdded = false
    private var LOADING = 0
    private var DATA = 1

    fun addAll(otherList: List<Result>) {
        val size = list.size
        list.addAll(otherList)
        notifyItemRangeChanged(size, list.size)
    }

    fun addLoading() {
        isLoadingAdded = true
    }

    inner class UserVh(var itemUserBinding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
    }

    inner class ProgressVh(var itemProgressBinding: ItemProgressBinding) :
        RecyclerView.ViewHolder(itemProgressBinding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING) {
            return ProgressVh(
                ItemProgressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return UserVh(
                ItemPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA) {
            val vh = holder as UserVh
            vh.itemUserBinding.apply {
                val item = list[position]

                Glide.with(context).load(item.urls.thumb)
                    .placeholder(R.drawable.ic_launcher_foreground).addListener(object :
                    RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
//                        progress.visibility = View.GONE
                        return false
                    }
                }).into(image)

                image.setOnClickListener {
                    listener.onCliked(item)
                }
            }
        } else {
            val vhProgress = holder as ProgressVh
            vhProgress.itemProgressBinding.apply {

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == list.size - 1 && isLoadingAdded) {
            return LOADING
        }
        return DATA
    }

    interface onItemClicked {
        fun onCliked(url: Result)
    }
}