package com.widget.bitframe

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.extensions.R
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

class MediaView @JvmOverloads constructor(context : Context, attrs : AttributeSet? = null, defStyle : Int = 0) : ViewGroup(context, attrs, defStyle), View.OnClickListener {
    private val imageViews = arrayOfNulls<RelativeLayout>(MAX_IMAGE_VIEW_COUNT)
    private var imageLoader : Picasso? = null
    private var mediaEntities : List<FeedImage>? = emptyList()
    private val path = Path()
    private val rect = RectF()
    private var mediaDividerSize : Int = 0
    private var imageCount : Int = 0
    private val radii = FloatArray(MAX_IMAGE_VIEW_COUNT * 2)
    private var mediaBgColor : Int = 0
    private var internalRoundedCornersEnabled : Boolean = false
    private var onMediaClickListener : OnMediaClickListener? = null
    private var roundedCornersRadii : Int = 0
    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)

    init {
        if (!isInEditMode)
            initAttributes(attrs, defStyle, Picasso.with(context))
    }

    private fun initAttributes(attrs : AttributeSet?, defStyle : Int, loader : Picasso) {
        this.imageLoader = loader
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.MediaView, defStyle, 0)
        try {
            roundedCornersRadii = a.getDimensionPixelSize(R.styleable.MediaView_corner_radii, DEFAULT_CORNER_RADII)
            mediaDividerSize = a.getDimensionPixelSize(R.styleable.MediaView_divider_size, DEFAULT_DIVIDER_SIZE)
            mediaBgColor = a.getColor(R.styleable.MediaView_background_color, Color.WHITE)
        } finally {
            a.recycle()
        }
    }

    private fun setRoundedCornersRadii(radii : Int) {
        for (i in 0 until MAX_IMAGE_VIEW_COUNT * 2)
            this.radii[i] = radii.toFloat()
        requestLayout()
    }

    override fun onLayout(changed : Boolean, left : Int, top : Int, right : Int, bottom : Int) {
        if (imageCount > 0) {
            layoutImages()
        }
    }

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
        val size :Size = if (imageCount > 0) {
            measureImages(widthMeasureSpec, heightMeasureSpec)
        } else {
            Size.EMPTY
        }
        setMeasuredDimension(size.width, size.height)
    }

    override fun onSizeChanged(w : Int, h : Int, oldw : Int, oldh : Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        path.reset()
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        path.addRoundRect(rect, radii, Path.Direction.CW)
        path.close()
    }

    override fun dispatchDraw(canvas : Canvas) {
        if (internalRoundedCornersEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            val saveState = canvas.save()
            canvas.clipPath(path)
            super.dispatchDraw(canvas)
            canvas.restoreToCount(saveState)
        } else {
            super.dispatchDraw(canvas)
        }
    }

    fun setOnMediaClickListener(onMediaClickListener :OnMediaClickListener) {
        this.onMediaClickListener = onMediaClickListener
    }

    interface OnMediaClickListener {
        fun onMediaClick(view : View, index : Int)
    }

    override fun onClick(view : View) {
        val mediaEntityIndex = view.tag as Int
        if (onMediaClickListener != null && mediaEntities != null && !mediaEntities!!.isEmpty()) {
            onMediaClickListener!!.onMediaClick(view, mediaEntityIndex)
        }
    }

    fun setMedias(mediaEntities : List<FeedImage>) {
        this.mediaEntities = mediaEntities
        setRoundedCornersRadii(roundedCornersRadii)
        clearImageViews()
        initializeImageViews(mediaEntities)
        internalRoundedCornersEnabled = true
        requestLayout()
    }

    private fun measureImages(widthMeasureSpec : Int, heightMeasureSpec : Int) :Size {
        val widthO = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightO = View.MeasureSpec.getSize(heightMeasureSpec)
        val widthHalf = (widthO - mediaDividerSize) / 2
        val heightHalf = (heightO - mediaDividerSize) / 2
        val heightOneThird = (heightO - mediaDividerSize) / 3
        when (imageCount) {
            1 -> measureImageView(0, widthO, heightO)
            2 -> {
                measureImageView(0, widthHalf, heightO)
                measureImageView(1, widthHalf, heightO)
            }
            3 -> {
                measureImageView(0, widthHalf, heightO)
                measureImageView(1, widthHalf, heightHalf)
                measureImageView(2, widthHalf, heightHalf)
            }
            4 -> {
                measureImageView(0, widthHalf, heightHalf)
                measureImageView(1, widthHalf, heightHalf)
                measureImageView(2, widthHalf, heightHalf)
                measureImageView(3, widthHalf, heightHalf)
            }
            5 -> {
                measureImageView(0, widthHalf, heightHalf)
                measureImageView(1, widthHalf, heightOneThird)
                measureImageView(2, widthHalf, heightHalf)
                measureImageView(3, widthHalf, heightOneThird)
                measureImageView(4, widthHalf, heightOneThird)
            }
            else -> {
            }
        }
        setImageCount()
        return Size.fromSize(widthO, heightO)
    }

    private fun measureImageView(i : Int, width : Int, height : Int) {
        imageViews[i]?.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY))
    }

    private fun layoutImages() {
        val widthO = measuredWidth
        val heightO = measuredHeight
        val widthHalf = (widthO - mediaDividerSize) / 2
        val heightHalf = (heightO - mediaDividerSize) / 2
        val heightOneThird = (heightO - mediaDividerSize) / 3
        val middle = widthHalf + mediaDividerSize

        when (imageCount) {
            1 -> layoutImage(0, 0, 0, widthO, heightO)
            2 -> {
                layoutImage(0, 0, 0, widthHalf, heightO)
                layoutImage(1, widthHalf + mediaDividerSize, 0, widthO, heightO)
            }
            3 -> {
                layoutImage(0, 0, 0, widthHalf, heightO)
                layoutImage(1, middle, 0, widthO, heightHalf)
                layoutImage(2, middle, heightHalf + mediaDividerSize, widthO, heightO)
            }
            4 -> {
                layoutImage(0, 0, 0, widthHalf, heightHalf)
                layoutImage(1, middle, 0, widthO, heightHalf)
                layoutImage(2, 0, heightHalf + mediaDividerSize, widthHalf, heightO)
                layoutImage(3, middle, heightHalf + mediaDividerSize, widthO, heightO)
            }
            5 -> {
                layoutImage(0, 0, 0, widthHalf, heightHalf)
                layoutImage(1, middle, 0, widthO, heightOneThird)
                layoutImage(2, 0, heightHalf + mediaDividerSize, widthHalf, heightO)
                layoutImage(3, middle, heightOneThird + mediaDividerSize, widthO, heightOneThird * 2)
                layoutImage(4, middle, heightOneThird * 2 + mediaDividerSize, widthO, heightO)
            }
            else -> {
            }
        }
        setImageCount()
    }

    private fun layoutImage(i : Int, left : Int, top : Int, right : Int, bottom : Int) {
        val view = imageViews[i]
        if (view!!.left == left && view.top == top && view.right == right && view.bottom == bottom) {
            return
        }

        view.layout(left, top, right, bottom)
    }

    private fun clearImageViews() {
        (0 until imageCount)
            .mapNotNull {imageViews[it]}
            .forEach {it.visibility = View.GONE}
        imageCount = 0
    }

    private fun setImageCount() {
        if (imageViews.isNotEmpty()) {
            if (mediaEntities!!.size > 5) {
                val imageView = imageViews[imageCount - 1]
                val textView = imageView!!.findViewById<View>(R.id.txt_count) as TextView
                textView.text = String.format(context.getString(R.string.txt_image_count), mediaEntities!!.size - imageCount)
                textView.visibility = View.VISIBLE
            }
        }
    }

    private fun initializeImageViews(mediaEntities : List<FeedImage>) {
        imageCount = Math.min(MAX_IMAGE_VIEW_COUNT, mediaEntities.size)

        for (index in 0 until imageCount) {
            val imageView = getOrCreateImageView(index)
            setAltText(imageView.findViewById<View>(R.id.img_photo) as ImageView, "media")
            setMediaImage(imageView.findViewById<View>(R.id.img_photo) as ImageView, imageView.findViewById<View>(R.id.img_video) as ImageView, mediaEntities[index])
            setOverlayImage(imageView.findViewById<View>(R.id.img_photo) as OverlayImageView)
        }
    }

    @SuppressLint("InflateParams")
    private fun getOrCreateImageView(index : Int) : RelativeLayout {
        var imageView : RelativeLayout? = imageViews[index]
        if (imageView == null) {
            imageView = layoutInflater.inflate(R.layout.layout_bitframe_photo, null) as RelativeLayout
            imageView.layoutParams = generateDefaultLayoutParams()
            imageView.setOnClickListener(this)
            imageViews[index] = imageView
            addView(imageView, index)
        } else {
            measureImageView(index, 0, 0)
            layoutImage(index, 0, 0, 0, 0)
        }

        imageView.visibility = View.VISIBLE
        imageView.setBackgroundColor(mediaBgColor)
        imageView.tag = index

        return imageView
    }

    @SuppressLint("PrivateResource")
    private fun setAltText(imageView : ImageView, description : String) {
        if (!TextUtils.isEmpty(description)) {
            imageView.contentDescription = description
        } else {
            imageView.contentDescription = CONTENT_DESC
        }
    }

    @SuppressLint("PrivateResource")
    private fun setOverlayImage(imageView :OverlayImageView) {
        imageView.setOverlayDrawable(null)
    }

    private fun setMediaImage(imageView : ImageView, imgVideoPlaceHolder : ImageView, bean : FeedImage) {
        if (imageLoader != null) {
            imageLoader!!.load(if (bean.isImage == 1) bean.image else bean.feedThumb)
                .fit()
                .centerCrop()
                .error(if (bean.isImage == 1) R.drawable.icon_product_cover_place_holder else R.drawable.icon_feed_place_holder)
                .placeholder(if (bean.isImage == 1) R.drawable.icon_product_cover_place_holder else R.drawable.icon_feed_place_holder)
                .into(imageView, PicassoCallback(imageView))
            imgVideoPlaceHolder.visibility = if (bean.isImage == 1) View.GONE else View.VISIBLE
        } else {
            imageView.setImageResource(if (bean.isImage == 1) R.drawable.icon_product_cover_place_holder else R.drawable.icon_feed_place_holder)
            imgVideoPlaceHolder.visibility = if (bean.isImage == 1) View.GONE else View.VISIBLE
        }
    }

    private class PicassoCallback internal constructor(imageView : ImageView) : com.squareup.picasso.Callback {
        private val imageViewWeakReference : WeakReference<ImageView> = WeakReference(imageView)
        override fun onSuccess() {
            val imageView = imageViewWeakReference.get()
            imageView?.setBackgroundResource(android.R.color.transparent)
        }

        override fun onError() {
        }
    }

    class Size private constructor(internal val width : Int = 0, internal val height : Int = 0) {
        companion object {
            internal val EMPTY = Size()
            internal fun fromSize(w : Int, h : Int) :Size {
                val boundedWidth = Math.max(w, 0)
                val boundedHeight = Math.max(h, 0)
                return if (boundedWidth != 0 || boundedHeight != 0)
                    Size(boundedWidth, boundedHeight)
                else
                    EMPTY
            }
        }
    }

    companion object {
        internal val MAX_IMAGE_VIEW_COUNT = 5 //4
        private val CONTENT_DESC = "content_description"
        private val DEFAULT_DIVIDER_SIZE = 2
        private val DEFAULT_CORNER_RADII = 5
    }
}
