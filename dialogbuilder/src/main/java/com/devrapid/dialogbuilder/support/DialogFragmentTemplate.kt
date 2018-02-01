package com.devrapid.dialogbuilder.support

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.devrapid.dialogbuilder.typedata.DFBtn
import com.devrapid.dialogbuilder.typedata.DSFListeners

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
abstract class DialogFragmentTemplate internal constructor(val mActivity: AppCompatActivity?,
                                                           val mFragment: Fragment?,
                                                           val btnPositive: DFBtn?,
                                                           val btnNegative: DFBtn?,
                                                           val clickListeners: DSFListeners?,
                                                           val mCancelable: Boolean,
                                                           val mTag: String,
    // TODO(jieyi): 7/12/17 Implement the request code function.
                                                           val requestCode: Int,
                                                           @LayoutRes
                                                           val viewCustom: Int,
                                                           var fetchComponents: ((View) -> Unit)? = {},
                                                           var message: String = "",
                                                           var title: String?) : DialogFragment() {
    private val viewList by lazy { mutableListOf<View>() }

    init {
        isCancelable = mCancelable
    }

    private constructor(builder: Builder) : this(builder.activity,
                                                 builder.parentFragment,
                                                 builder.btnPositiveText,
                                                 builder.btnNegativeText,
                                                 builder.clickListener,
                                                 builder.cancelable,
                                                 builder.tag,
                                                 builder.requestCode,
                                                 builder.viewResCustom,
                                                 builder.fetchComponents,
                                                 builder.message.orEmpty(),
                                                 builder.title)

    /**
     * A builder of [DialogFragmentTemplate].
     */
    abstract class Builder {
        constructor(activity: AppCompatActivity, block: Builder.() -> Unit) {
            this.activity = activity
            parentFragment = null
            apply(block)
        }

        constructor(fragment: Fragment, block: Builder.() -> Unit) {
            activity = null
            parentFragment = fragment
            apply(block)
        }

        val activity: AppCompatActivity?
        val parentFragment: Fragment?
        var fetchComponents: ((View) -> Unit)? = null
        var btnNegativeText: DFBtn? = null
        var btnPositiveText: DFBtn? = null
        var cancelable: Boolean = true
        var clickListener: DSFListeners? = null
        var message: String? = null
        var requestCode: Int = -1
        var tag: String = "default"
        var title: String? = null
        @LayoutRes
        var viewResCustom: Int = -1

        abstract fun build(): DialogFragmentTemplate
    }

    fun show() = show((mFragment?.fragmentManager ?: mActivity?.supportFragmentManager), mTag)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // If viewResCustom is set then create a custom fragment; otherwise, just using simple AlertDialog.
        val dialog = if (0 < viewCustom) {
            super.onCreateDialog(savedInstanceState)
        }
        else {
            AlertDialog.Builder(activity!!).create().also {
                message.takeIf { it.isNotBlank() }.let(it::setMessage)
                btnPositive?.let { (text, listener) ->
                    it.setButton(Dialog.BUTTON_POSITIVE, text, listener)
                }
                btnNegative?.let { (text, listener) ->
                    it.setButton(Dialog.BUTTON_NEGATIVE, text, listener)
                }
            }
        }

        return dialog.also {
            title?.let(it::setTitle)
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        if (0 < viewCustom) {
            provideView(inflater, container, savedInstanceState)
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (view is ViewGroup && 1 == view.childCount && view.getChildAt(0) is ViewGroup) {
            view.getChildAt(0).layoutParams.apply {
                if (ViewGroup.LayoutParams.MATCH_PARENT == height) height = resources.displayMetrics.heightPixels
                if (ViewGroup.LayoutParams.MATCH_PARENT == width) width = resources.displayMetrics.widthPixels
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        fetchComponents = null
        if (0 < viewCustom) {
            viewList.forEach { it.setOnClickListener(null) }
            viewList.clear()
        }
    }

    abstract fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    fun onCreateDialogView(view: View?) {
        view?.let {
            // Fetch the components from a view.
            fetchComponents?.let { self -> self(it) }
            // Set the listener into each of views.
            clickListeners?.forEach { (id, listener) ->
                viewList.add(it.findViewById<View>(id).apply {
                    setOnClickListener {
                        listener(this@DialogFragmentTemplate, it)
                    }
                })
            }
        }
    }
}