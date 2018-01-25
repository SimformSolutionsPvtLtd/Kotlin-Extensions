package com.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

@Suppress("unused", "MemberVisibilityCanPrivate")
abstract class BaseActivity<VDB : ViewDataBinding, BVM :BaseViewModel> : AppCompatActivity() {
    lateinit var mViewDataBinding: VDB
    lateinit var mViewModel: BVM
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    fun getViewDataBinding(): VDB {
        return mViewDataBinding
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearMemory()
        compositeDisposable.dispose()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        clearMemory()
    }

    private fun clearMemory() {
        try {
            System.gc()
            Runtime.getRuntime().gc()
        } catch (e: Exception) {
        }
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): BVM
    abstract fun getBindingVariable(): Int
}