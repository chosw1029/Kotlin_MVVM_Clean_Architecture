package com.nextus.baseapp.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager


/**
 * @author ReStartAllKill
 * @created on 2020-01-28
 * @modified by
 * @updated on
 */

abstract class BaseDialog<T: ViewDataBinding, V: BaseViewModel> : DialogFragment() {

    private lateinit var mViewDataBinding: T
    private var mActivity: BaseActivity<*, *>? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun getBindingVariable(): Int

    abstract fun getViewModel(): V

    abstract fun setUp()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is BaseActivity<*, *>) {
            mActivity = context
            mActivity?.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Fragment Option Menu 사용
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), getViewModel())
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()

        setUp()
    }

    fun getBaseActivity() : BaseActivity<*, *>? {
        return mActivity
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    interface CallBack {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String)
    }

    override fun show(fragmentManager: FragmentManager, tag: String?) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    override fun onResume() {
        super.onResume()
        val params = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}