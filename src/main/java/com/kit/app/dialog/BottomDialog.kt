package com.kit.app.dialog

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

/**
 * BottomDialog
 */
class BottomDialog : BaseBottomDialog() {
    private var mFragmentManager: FragmentManager? = null
    override var cancelOutside = super.cancelOutside
        private set
    override var fragmentTag = super.fragmentTag
        private set
    private var mDimAmount = getDimAmount()
    override var height = super.height
        private set

    @LayoutRes
    override var layoutRes = 0
        private set



    private var mViewListener: ViewListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            height = savedInstanceState.getInt(KEY_HEIGHT)
            mDimAmount = savedInstanceState.getFloat(KEY_DIM)
            cancelOutside = savedInstanceState.getBoolean(KEY_CANCEL_OUTSIDE)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_HEIGHT, height)
        outState.putFloat(KEY_DIM, mDimAmount)
        outState.putBoolean(KEY_CANCEL_OUTSIDE, cancelOutside)
        super.onSaveInstanceState(outState)
    }

    override  fun bindView(dialog: BaseBottomDialog, view: View?, vb: ViewBinding?) {
        mViewListener?.bindView(this, view, vb)
    }

    fun setFragmentManager(manager: FragmentManager?): BottomDialog {
        mFragmentManager = manager
        return this
    }

    fun setViewListener(listener: ViewListener): BottomDialog {
        mViewListener = listener
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <VB : ViewBinding> setViewBindingClass(vb: Class<VB>?): BottomDialog {
        vbClass = vb as Class<ViewBinding>
        return this
    }

    fun setLayoutRes(@LayoutRes layoutRes: Int): BottomDialog {
        this.layoutRes = layoutRes
        return this
    }

    fun setCancelOutside(cancel: Boolean): BottomDialog {
        cancelOutside = cancel
        return this
    }

    fun setTag(tag: String?): BottomDialog {
        fragmentTag = tag
        return this
    }

    fun setDimAmount(dim: Float): BottomDialog {
        mDimAmount = dim
        return this
    }

    fun setHeight(heightPx: Int): BottomDialog {
        height = heightPx
        return this
    }

    override fun getDimAmount(): Float {
        return mDimAmount
    }

    interface ViewListener  {
        fun bindView(dialog: BottomDialog, view: View?, vb: ViewBinding?)
    }

    fun show(): BaseBottomDialog {
        if (layoutRes == 0 && vbClass == null) {
            throw IllegalArgumentException("BottomDialog layoutRes and viewBinding class is null")
        }
        mFragmentManager?.let {
            show(it)
        }
        return this
    }

    companion object {
        private const val KEY_LAYOUT_RES = "bottom_layout_res"
        private const val KEY_HEIGHT = "bottom_height"
        private const val KEY_DIM = "bottom_dim"
        private const val KEY_CANCEL_OUTSIDE = "bottom_cancel_outside"
        fun create(manager: FragmentManager?): BottomDialog {
            val dialog = BottomDialog()
            dialog.fragmentManager = manager
            return dialog
        }
    }


}