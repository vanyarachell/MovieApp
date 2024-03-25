package com.vanya.movieapp.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.File
import java.net.URL

/**
 * Created by vanyarachell on Sun, 24 Mar 2024
 * vanyarachel05@gmail.com
 *
 * The use of this function to centralize generalize and make it easier for android team
 * to use glide as image loader
 *
 * @param any the type of element to be loaded to glide
 * @param thumbnail when user needed to show thumbnail
 * @param fitCenter Scales the image uniformly so that one of the dimensions of the image will be equal to the given dimension and the other will be less than the given dimension.
 * @param transition transition from a placeholder to a newly loaded image or from a thumbnail to a full size image.
 * @param placeholder Sets an Android resource id for a Drawable resource to display while a resource is loading.
 * @param error Sets an Android resource id for a Drawable resource to display while a error happens.
 *
 */
@JvmOverloads
fun ImageView.loadImage(
    any: Any,
    thumbnail: Float? = null,
    fitCenter: Boolean = false,
    centerCrop: Boolean = false,
    transition: Boolean = false,
    @DrawableRes placeholder: Int? = null,
    @DrawableRes error: Int? = null
) {

    when (any) {
        is String, is Bitmap, is Int, is URL, is File, is ByteArray -> {
            val image = Glide.with(this.context).load(any).apply {
                if (fitCenter) fitCenter()
                if (centerCrop) centerCrop()
                if (transition) transition(DrawableTransitionOptions.withCrossFade())
                thumbnail?.let { thumbnail(it) }
                placeholder?.let { placeholder(it) }
                error?.let { error(it) }
            }

            if (any is Drawable) {
                image.into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        this@loadImage.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        this@loadImage.setImageDrawable(placeholder)
                    }
                })
            } else {
                image.into(this)
            }
        }
    }
}

@BindingAdapter("srcImage")
fun loadAnyImage(view: ImageView, image: Any?) {
    image?.let { view.loadImage(it) }
}