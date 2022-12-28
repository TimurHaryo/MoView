package com.timtam.uikit.extension

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@SuppressLint("CheckResult")
fun ImageView.loadImage(
    url: String?,
    usePlaceHolder: Boolean = true,
    priority: Priority = Priority.HIGH
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(priority)
        .dontAnimate()
        .apply {
            if (usePlaceHolder) {
                placeholder(ColorDrawable(Color.LTGRAY))
                transition(DrawableTransitionOptions.withCrossFade())
            }
        }
        .into(this)
}

fun ImageView.loadImage(
    @DrawableRes resId: Int?,
    priority: Priority = Priority.HIGH
) {
    Glide.with(this)
        .load(resId)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(priority)
        .into(this)
}

@SuppressLint("CheckResult")
fun ImageView.loadImageWithRadius(
    url: String?,
    @IntRange(from = 1) radius: Int,
    usePlaceHolder: Boolean = true,
    priority: Priority = Priority.HIGH
) {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(
            RequestOptions().transform(
                CenterCrop(),
                RoundedCorners(radius)
            )
        )
        .priority(priority)
        .dontAnimate()
        .apply {
            if (usePlaceHolder) {
                placeholder(ColorDrawable(Color.LTGRAY))
                transition(DrawableTransitionOptions.withCrossFade())
            }
        }
        .into(this)
}
