package com.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.util.SparseArray
import android.widget.ImageView
import java.lang.ref.SoftReference

/**
 * The type Faster animations container.
 * Instantiates a new Faster animations container.
 *
 * @param imageView the image view
 */
class FasterAnimationsContainer(imageView :ImageView, private val intTimeInterval :Int, private val isLooping :Boolean) {
    private var mAnimationFrames :SparseArray<AnimationFrame>? = null // list for all frames of animation
    private var mIndex :Int = 0 // index of current frame
    private var mShouldRun :Boolean = false // true if the animation should continue running. Used to stop the animation
    private var mIsRunning :Boolean = false // true if the animation prevents starting the animation twice
    private var mSoftReferenceImageView :SoftReference<ImageView>? = null // Used to prevent holding ImageView when it should be dead.
    private var mHandler :Handler? = null // Handler to communication with UIThread
    private var mSize :Int = 0
    // Listeners
    private var mOnAnimationListener :OnAnimationListener? = null

    private val next :AnimationFrame
        get() {
            mIndex++
            if(mIndex >= mAnimationFrames!!.size())
                mIndex = 0
            val key = mAnimationFrames!!.keyAt(mIndex)
            return mAnimationFrames!!.get(key)
        }

    private inner class AnimationFrame

    /**
     * Instantiates a new Animation frame.
     *
     * @param resourceId the resource id
     * @param duration   the duration
     */
    internal constructor(
        /**
         * Gets resource id.
         *
         * @return the resource id
         */
        val resourceId :Int,
        /**
         * Gets duration.
         *
         * @return the duration
         */
        val duration :Int)

    init {
        init(imageView)
    }

    /**
     * initialize imageview and frames
     *
     * @param imageView the image view
     */
    private fun init(imageView :ImageView) {
        mAnimationFrames = SparseArray()
        mSoftReferenceImageView = SoftReference(imageView)

        mHandler = Handler()
        if(mIsRunning == true) {
            stop()
        }

        mShouldRun = false
        mIsRunning = false

        mIndex = -1
    }

    /**
     * add all frames of animation
     *
     * @param resIds   resource id of drawable
     * @param interval milliseconds
     */
    fun addAllFrames(context :Context, resIds :IntArray, interval :Int, size :Int) {
        if(size == 0)
            mSize = size
        else
            mSize = resIds.size - 1
        for(resId in resIds.indices) {
            mAnimationFrames!!.put(resId, AnimationFrame(resIds[resId], interval))
        }
    }

    /**
     * Add all frames.
     *
     * @param resIds   the res ids
     * @param interval the interval
     */
    fun addAllFrames(context :Context, resIds :TypedArray, interval :Int, size :Int) {
        if(size == 0)
            mSize = size
        else
            mSize = resIds.length() - 1
        for(resId in 0 until resIds.length()) {
            mAnimationFrames!!.put(resId, AnimationFrame(resIds.getResourceId(resId, -1), interval))
        }
    }

    /**
     * remove a frame with index
     *
     * @param index index of animation
     */
    fun removeFrame(index :Int) {
        val key = mAnimationFrames!!.keyAt(index)
        mAnimationFrames!!.remove(key)
    }

    /**
     * clear all frames
     */
    fun removeAllFrames() {
        mAnimationFrames!!.clear()
    }

    /**
     * change a frame of animation
     *
     * @param index    index of animation
     * @param resId    resource id of drawable
     * @param interval milliseconds
     */
    fun replaceFrame(context :Context, index :Int, resId :Int, interval :Int) {
        val key = mAnimationFrames!!.keyAt(index)
        mAnimationFrames!!.append(key, AnimationFrame(resId, interval))
    }

    /**
     * Listener of animation to detect stopped
     */
    interface OnAnimationListener {
        fun onAnimationStart()
        fun onAnimationStopped()
        fun onAnimationRestart()
        fun onAnimationFrameChanged(index :Int)
    }

    /**
     * set a listener for OnAnimationStoppedListener
     *
     * @param listener OnAnimationStoppedListener
     */
    fun setOnAnimationListener(listener :OnAnimationListener) {
        mOnAnimationListener = listener
    }

    /**
     * Starts the animation
     */
    @Synchronized
    fun start() {
        mShouldRun = true
        if(mIsRunning)
            return
        if(mOnAnimationListener != null)
            mOnAnimationListener!!.onAnimationStart()
        mHandler!!.post(FramesSequenceAnimation())
    }

    /**
     * Stops the animation
     */
    @Synchronized
    fun stop() {
        mShouldRun = false
        mIsRunning = false
    }

    private inner class FramesSequenceAnimation :Runnable {
        override fun run() {
            val imageView = mSoftReferenceImageView!!.get()
            if(!mShouldRun || imageView == null) {
                mIsRunning = false
                if(mOnAnimationListener != null)
                    mOnAnimationListener!!.onAnimationStopped()
                return
            }
            mIsRunning = true

            if(imageView.isShown) {
                val frame = next
                val task = GetImageDrawableTask(imageView)
                task.execute(frame.resourceId)
                if(mOnAnimationListener != null)
                    mOnAnimationListener!!.onAnimationFrameChanged(mIndex)

                if(mIndex == mSize) {
                    if(isLooping) {
                        mHandler!!.postDelayed(this, intTimeInterval.toLong())
                        if(mOnAnimationListener != null)
                            mOnAnimationListener!!.onAnimationRestart()
                    } else {
                        stop()
                        if(mOnAnimationListener != null)
                            mOnAnimationListener!!.onAnimationStopped()
                    }
                } else
                    mHandler!!.postDelayed(this, frame.duration.toLong())
            }
        }
    }

    inner class GetImageDrawableTask(private val mImageView :ImageView) :AsyncTask<Int, Void, Drawable>() {
        override fun doInBackground(vararg params :Int?) :Drawable? {
            return params[0]?.let {ContextCompat.getDrawable(mImageView.context, it)}
        }

        override fun onPostExecute(result :Drawable?) {
            super.onPostExecute(result)
            if(result != null)
                mImageView.setImageDrawable(result)
            if(mOnAnimationListener != null)
                mOnAnimationListener!!.onAnimationFrameChanged(mIndex)
        }
    }
}