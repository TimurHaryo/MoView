package com.timtam.uikit.extension

import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(
    url: String?,
    priority: Priority = Priority.HIGH
) = onContextAlive {
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(priority)
        .dontAnimate()
        .into(this)
}

fun ImageView.loadImageWithRadius(
    url: String?,
    @IntRange(from = 1) radius: Int,
    priority: Priority = Priority.HIGH
) = onContextAlive {
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
        .into(this)
}

private inline fun ImageView.onContextAlive(crossinline block: () -> Unit) {
    if ((context as? AppCompatActivity)?.isDestroyed == true) block()
}
