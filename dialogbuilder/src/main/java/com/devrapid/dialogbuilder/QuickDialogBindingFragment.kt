package com.devrapid.dialogbuilder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import com.devrapid.dialogbuilder.typedata.DFBtn

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogBindingFragment<B : ViewDataBinding> private constructor(
    private val mActivity: Activity?,
    private val mFragment: Fragment?,
    //region Alert Dialog Parameters
    /** This is for Alert Dialog Parameter. */
    private var title: String = "",
    private var message: String = "",
    private val btnPositive: DFBtn?,
    private val btnNegative: DFBtn?,
    private val mCancelable: Boolean,
    private val mTag: String,
    //endregion
    //region Customize View Parameters
    /**
     *  The below parameters are for the customization view.
     *  Once view is set, the parameters above here will be ignored.
     */
    @LayoutRes
    private val viewCustom: Int,
    @StyleRes
    private var themeStyle: Int? = null
    //endregion
) : DialogFragment() {
    init {
        isCancelable = mCancelable
    }

    var bind: (binding: B) -> Unit = {}
    private lateinit var binding: B

    private constructor(builder: Builder<B>) : this(builder.activity,
                                                    builder.parentFragment,
                                                    builder.title,
                                                    builder.message,
                                                    builder.btnPositiveText,
                                                    builder.btnNegativeText,
                                                    builder.cancelable,
                                                    builder.tag,
                                                    builder.viewCustom,
                                                    builder.themeStyle)

    /**
     * A builder of [QuickDialogBindingFragment].
     */
    class Builder<B : ViewDataBinding> {
        constructor(activity: Activity, block: Builder<B>.() -> Unit) {
            this.activity = activity
            parentFragment = null
            apply(block)
        }

        constructor(fragment: Fragment, block: Builder<B>.() -> Unit) {
            activity = null
            parentFragment = fragment
            apply(block)
        }

        val activity: Activity?
        val parentFragment: Fragment?
        var title = ""
        var message = ""
        var btnNegativeText: DFBtn? = null
        var btnPositiveText: DFBtn? = null
        var cancelable = true
        var tag = "default"
        @LayoutRes
        var viewCustom = -1
        @StyleRes
        var themeStyle: Int? = null

        fun build() = QuickDialogBindingFragment(this)
    }

    fun show() = show((mFragment?.fragmentManager ?: mActivity?.fragmentManager), mTag)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // If viewResCustom is set then create a custom fragment; otherwise, just using simple AlertDialog.
        val dialog = if (0 < viewCustom) {
            themeStyle.takeIf { null != it }?.let { setStyle(STYLE_NORMAL, it) }

            super.onCreateDialog(savedInstanceState)
        }
        else {
            AlertDialog.Builder(activity).create().also {
                message.takeIf { it.isNotBlank() }.let(it::setMessage)
                btnPositive?.let { (text, listener) ->
                    it.setButton(Dialog.BUTTON_POSITIVE, text, { dialog, _ -> listener(dialog) })
                }
                btnNegative?.let { (text, listener) ->
                    it.setButton(Dialog.BUTTON_NEGATIVE, text, { dialog, _ -> listener(dialog) })
                }
            }
        }

        return dialog.also {
            title.let(it::setTitle)
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
        if (0 < viewCustom) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity.applicationContext),
                                              viewCustom,
                                              null,
                                              false)!!
            bind(binding)
            binding.root
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }

    override fun onResume() {
        super.onResume()

        if (0 < viewCustom) {
            dialog.window.setLayout(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
        }
    }
}