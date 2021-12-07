package com.example.walpppaperappkotlin.ui.fragments.tayyorlar

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.databace.Entity
import com.example.walpppaperappkotlin.databinding.FragmentSetWalppaperBinding
import com.example.walpppaperappkotlin.models.Result
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.IOException
import java.lang.Exception

class SetWalppaper : Fragment() {

    lateinit var fragment: FragmentSetWalppaperBinding
    private lateinit var param1: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity?)?.hideBlurView()
        arguments?.let {
            param1 = it.getString("image") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentSetWalppaperBinding.inflate(inflater, container, false)

        Picasso.get().load(param1).into(fragment.image)

        val fromTop = AnimationUtils.loadAnimation(requireContext(), R.anim.from_top)
        val fromBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom)
        fragment.iconBack.startAnimation(fromTop)
        fragment.iconBoth.startAnimation(fromBottom)
        fragment.iconLock.startAnimation(fromBottom)
        fragment.iconHome.startAnimation(fromBottom)

        fragment.iconBack.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment.iconHome.setOnClickListener {
            val result = arrayOf<Bitmap?>(null)
            Picasso.get().load(param1).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    result[0] = bitmap
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            })
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(result[0],
                        null,
                        false,
                        WallpaperManager.FLAG_SYSTEM)
                    Toast.makeText(requireContext(), "Successfully set", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fragment.iconLock.setOnClickListener {
            val result = arrayOf<Bitmap?>(null)
            Picasso.get().load(param1).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    result[0] = bitmap
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            })
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(result[0],
                        null,
                        false,
                        WallpaperManager.FLAG_LOCK)
                    Toast.makeText(requireContext(), "Successfully set", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fragment.iconBoth.setOnClickListener {
            val result = arrayOf<Bitmap?>(null)
            Picasso.get().load(param1).into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                    result[0] = bitmap
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
                override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
            })
            val wallpaperManager = WallpaperManager.getInstance(requireContext())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    wallpaperManager.setBitmap(result[0],
                        null,
                        false,
                        WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
                    Toast.makeText(requireContext(), "Successfully set", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return fragment.root
    }
}