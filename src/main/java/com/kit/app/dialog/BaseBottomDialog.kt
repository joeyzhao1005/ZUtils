package com.kit.app.dialog

import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.kit.basic.R

/**
 * BaseBottomDialog
 */
abstract class BaseBottomDialog : DialogFragment() {

    protected var vbClass: Class<ViewBinding>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let {
            it.window?.requestFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(cancelOutside)
        }


        val vb = viewBinding(container)

        return if (vb == null) {
            val v = inflater.inflate(layoutRes, container, false)
            bindView(this, v, null)
            v
        } else {
            bindView(this, vb.root, vb)
            vb.root
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun viewBinding(parent: ViewGroup?): ViewBinding? {
        var bindView: ViewBinding? = null
        if (vbClass != null) {
            try {
                val method = vbClass!!.getDeclaredMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.javaPrimitiveType
                )
                bindView =
                    method.invoke(null, LayoutInflater.from(context), parent, false) as ViewBinding?


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return bindView
    }


    @get:LayoutRes
    abstract val layoutRes: Int
    abstract fun bindView(dialog: BaseBottomDialog, view: View?, vb: ViewBinding?)
    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        val params = window!!.attributes
        params.dimAmount = getDimAmount()
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        if (height > 0) {
            params.height = height
        } else {
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        params.gravity = Gravity.BOTTOM
        window.attributes = params
    }

    open fun getDimAmount(): Float {
        return DEFAULT_DIM
    }

    open val height: Int
        get() = -1
    open val cancelOutside: Boolean
        get() = true
    open val fragmentTag: String?
        get() = TAG

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, fragmentTag)
    }

    companion object {
        private const val TAG = "base_bottom_dialog"
        private const val DEFAULT_DIM = 0.2f
    }
}