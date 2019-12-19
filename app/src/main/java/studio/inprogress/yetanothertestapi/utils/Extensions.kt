package studio.inprogress.yetanothertestapi.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import studio.inprogress.yetanothertestapi.BuildConfig
import studio.inprogress.yetanothertestapi.R


inline fun <T> T.showToast(message: String): Unit where T : Fragment {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

inline fun logDebug(lambda: () -> String?) {
    if (BuildConfig.DEBUG) {
        Log.d("----------->", lambda() ?: "")
    }
}

inline fun <T> T.applyIf(ifValue: Boolean, block: T.() -> Unit): T {
    if (ifValue) block(this)
    return this
}

val Context.glide
    get() = Glide.with(this.applicationContext)

fun ImageView.load(
    urlStr: String,
    transformation: BitmapTransformation,
    progress: ProgressBar? = null
) {
    if (urlStr.isEmpty()) return

    val reqListener = object : RequestListener<Bitmap> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            progress?.hide(true)
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: com.bumptech.glide.request.target.Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            progress?.hide(true)
            this@load.setImageBitmap(resource)
            return true
        }
    }
    context.glide.asBitmap().load(urlStr).listener(reqListener)
        .apply(RequestOptions.bitmapTransform(transformation))
        .placeholder(R.drawable.placeholder_avatar)
        .into(this)
}

var View.visible
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

@BindingAdapter("loadAvatar")
fun loadAvatar(view: ImageView, url: String?) {
    url?.let { view.load(url, CircleCrop()) }
}

inline fun View.binding(): ViewDataBinding? {
    return DataBindingUtil.bind(this)
}
