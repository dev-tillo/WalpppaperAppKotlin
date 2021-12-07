package com.example.walpppaperappkotlin.ui.fragments.tayyorlar

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.walpppaperappkotlin.MainActivity
import com.example.walpppaperappkotlin.R
import com.example.walpppaperappkotlin.databace.DataHelper
import com.example.walpppaperappkotlin.databace.Entity
import com.example.walpppaperappkotlin.databinding.BottomDialogBinding
import com.example.walpppaperappkotlin.databinding.FragmentImageBinding
import com.example.walpppaperappkotlin.models.RandomClass
import com.example.walpppaperappkotlin.models.Result
import com.google.android.material.bottomsheet.BottomSheetDialog
import eightbitlab.com.blurview.RenderScriptBlur
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.os.Environment

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import java.io.File
import java.lang.Exception


class ImageFragment : Fragment() {

    private lateinit var result: Result
    private lateinit var dataHelper: DataHelper
    private var isLike = false
    private var isprolike = false
    private lateinit var entity: Entity
    private var int: Int = 0
    private var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity?)?.hideBlurView()
        arguments?.let {
            int = it.getInt("key")
            if (int == 1) {
                result = it.getSerializable("image") as Result
                entity = Entity(result.id,
                    result.user.name,
                    result.urls.regular,
                    result.likes,
                    result.width,
                    result.height)
            } else {
                int = it.getInt("key1")
                entity = it.getSerializable("image") as Entity
            }
        }
    }

    lateinit var fragment: FragmentImageBinding

    @SuppressLint("SetTextI18n", "CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        fragment = FragmentImageBinding.inflate(inflater, container, false)
        dataHelper = DataHelper.getInstance(requireContext())

        Glide.with(requireActivity()).load(entity.url).into(fragment.image)

        dataHelper.pdpDao()
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                for (en in it) {
                    if (en.id == entity.id) {
                        isprolike = false
                        fragment.imgLike.setImageResource(R.drawable.ic_heart)
                        break
                    } else {
                        isprolike = true
                        fragment.imgLike.setImageResource(R.drawable.ic_liked)
                    }
                }
            }

        fragment.iconLike.setOnClickListener {
            when (counter) {
                0 -> {
                    counter++
                    dataHelper.pdpDao().deleteSplash(Entity(entity.id,
                        entity.name,
                        entity.url,
                        entity.counts,
                        entity.width,
                        entity.height))
                    fragment.imgLike.setImageResource(R.drawable.ic_liked)
                }
                1 -> {
                    counter--
                    dataHelper.pdpDao().addSplash(Entity(entity.id,
                        entity.name,
                        entity.url,
                        entity.counts,
                        entity.width,
                        entity.height))
                    fragment.imgLike.setImageResource(R.drawable.ic_heart)
                }
                0 -> {
                    counter++
                    dataHelper.pdpDao().deleteSplash(Entity(entity.id,
                        entity.name,
                        entity.url,
                        entity.counts,
                        entity.width,
                        entity.height))
                    fragment.imgLike.setImageResource(R.drawable.ic_liked)
                }
                else -> {
                    counter--
                    dataHelper.pdpDao().addSplash(Entity(entity.id,
                        entity.name,
                        entity.url,
                        entity.counts,
                        entity.width,
                        entity.height))
                    fragment.imgLike.setImageResource(R.drawable.ic_heart)
                }
            }
        }

        val fromTop = AnimationUtils.loadAnimation(requireContext(), R.anim.from_top)
        fragment.iconBack.startAnimation(fromTop)
        fragment.iconShare.startAnimation(fromTop)
        fragment.iconAbout.startAnimation(fromTop)
        val fromBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom)
        fragment.iconDownload.startAnimation(fromBottom)
        fragment.iconPut.startAnimation(fromBottom)
        fragment.iconCustomize.startAnimation(fromBottom)
        fragment.iconLike.startAnimation(fromBottom)


        fragment.iconBack.setOnClickListener {
            findNavController().popBackStack()
            onDestroyView1()
        }

        fragment.iconDownload.setOnClickListener {
            downloadImageNew(entity.name, entity.url)
        }

        fragment.iconShare.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, entity.url)
            sendIntent.type = "text/plain"
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        fragment.iconAbout.setOnClickListener {
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            val bottomDialogBinding: BottomDialogBinding =
                BottomDialogBinding.inflate(layoutInflater)

            bottomDialogBinding.authorTv.text = entity.name
            bottomDialogBinding.downloadTv.text = entity.counts.toString()
            bottomDialogBinding.websiteTv.text = entity.url
            bottomDialogBinding.sizeTv.text = entity.height.toString()

            bottomSheetDialog.window!!
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bottomSheetDialog.setCancelable(true)
            bottomSheetDialog.setContentView(bottomDialogBinding.root)


            val radius = 20f
            bottomSheetDialog.window!!
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            bottomDialogBinding.blurViewDialog.setupWith(container!!)
                .setBlurAlgorithm(RenderScriptBlur(requireContext()))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(true)
                .setHasFixedTransformationMatrix(true)
            bottomSheetDialog.show()
        }
        fragment.iconPut.setOnClickListener {
            val bundle1 = Bundle()
            bundle1.putSerializable("image", entity.url)
            findNavController()
                .navigate(R.id.action_imageFragment_to_setWalppaper, bundle1)
        }
        return fragment.root
    }

    private fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm =
                requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri: Uri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES,
                    File.separator.toString() + filename + ".jpg")
            dm.enqueue(request)
            Toast.makeText(requireContext(), "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun onDestroyView1() {
        super.onDestroyView()
        (activity as MainActivity?)!!.showBlurView()
    }
}
//            if (isLike) {
//                isLike = false
//                dataHelper.pdpDao().deleteSplash(Entity(entity.id,
//                    entity.name,
//                    entity.url,
//                    entity.counts,
//                    entity.width,
//                    entity.height))
//                fragment.imgLike.setImageResource(R.drawable.ic_heart)
//            } else {
//                isLike = true
//                dataHelper.pdpDao().addSplash(Entity(entity.id,
//                    entity.name,
//                    entity.url,
//                    entity.counts,
//                    entity.width,
//                    entity.height))
//                fragment.imgLike.setImageResource(R.drawable.ic_liked)
//            }