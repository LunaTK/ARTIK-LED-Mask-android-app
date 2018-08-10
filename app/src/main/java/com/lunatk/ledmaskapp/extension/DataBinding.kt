package com.lunatk.ledmaskapp.extension

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.ViewTarget
import com.firebase.ui.storage.images.FirebaseImageLoader
import com.google.firebase.storage.StorageReference
import com.lunatk.ledmaskapp.objects.AnalysisHistory
import com.lunatk.ledmaskapp.adapter.AnalysisHistoryAdapter
import com.lunatk.ledmaskapp.R
import com.lunatk.ledmaskapp.adapter.LEDMaskAdapter
import com.lunatk.ledmaskapp.adapter.TherapyHistoryAdapter
import com.lunatk.ledmaskapp.objects.LEDMask
import com.lunatk.ledmaskapp.objects.TherapyHistory

@BindingAdapter("items")
fun bindItems(recyclerView: RecyclerView, historyList: ObservableArrayList<AnalysisHistory>) {
    val adapter = recyclerView.adapter as AnalysisHistoryAdapter?
    Log.i("AdapterBindings", "Hello")
    if (adapter != null) {
        adapter.historyList = historyList
    }
}

@BindingAdapter("maskList")
fun bindMaskList(recyclerView: RecyclerView, maskList: ObservableArrayList<LEDMask>) {
    val adapter = recyclerView.adapter as LEDMaskAdapter?
    Log.i("AdapterBindings", "Hello")
    if (adapter != null) {
        adapter.masks = maskList
    }
}

@BindingAdapter("therapyHistoryList")
fun bindTherapyList(recyclerView: RecyclerView, therapyHistoryList: ObservableArrayList<TherapyHistory>) {
    val adapter = recyclerView.adapter as TherapyHistoryAdapter?
    if (adapter != null) {
        adapter.setTherapyHistoryList(therapyHistoryList)
    }
}


@BindingAdapter("storageReference")
fun bindStorageReference(imageView: ImageView, storageReference: StorageReference) {
    Glide.with(imageView.context)
            .using(FirebaseImageLoader())
            .load(storageReference)
            //                .placeholder(android.R.drawable.ic_menu_gallery)
            .into(object : ViewTarget<ImageView, GlideDrawable>(imageView) {
                override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) {
                    imageView.setImageDrawable(resource.current)
                    //                        ((FragmentActivity)imageView.getContext()).startPostponedEnterTransition();
                }

                override fun onLoadFailed(e: Exception?, errorDrawable: Drawable?) {
                    super.onLoadFailed(e, errorDrawable)
                    imageView.setImageResource(R.drawable.ic_broken_image_black_24dp)
                    //                        ((FragmentActivity)imageView.getContext()).startPostponedEnterTransition();
                }
            })
}