package com.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.extensions.layout.inflateBindView
import io.reactivex.disposables.CompositeDisposable

@Suppress("unused", "MemberVisibilityCanPrivate", "UNCHECKED_CAST")
abstract class BaseFragment<VDB : ViewDataBinding, BVM :BaseViewModel> : Fragment() {
    var mBaseActivity: BaseActivity<*, *>? = null
    lateinit var mContext: Context
    lateinit var mViewDataBinding: VDB
    lateinit var mViewModel: BVM
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = container!!.inflateBindView(getLayoutId()) as VDB
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = getViewModel()
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.mBaseActivity = activity
        }
    }

    override fun onDetach() {
        mBaseActivity = null
        super.onDetach()
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
