package com.devrapid.dialogbuilder.support

import android.annotation.SuppressLint
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.devrapid.dialogbuilder.typedata.DFBtn

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogBindingFragment<B : ViewDataBinding> private constructor(
    private val mActivity: AppCompatActivity?,
    private val mFragment: Fragment?,
    //region Alert Dialog Parameters
    /** This is for Alert Dialog Parameter. */
    private var title: String?,
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
    @LayoutRes private val viewCustom: Int,
    protected var otherStyle: Pair<Int, Int>? = null,
    @StyleRes private var themeStyle: Int? = null
    //endregion
) : DialogFragment() {
    /** This is for data binding. */
    var bind: (binding: B) -> Unit = {}
    private lateinit var binding: B

    init {
        isCancelable = mCancelable
    }

    private constructor(builder: Builder<B>) : this(builder.activity,
                                                    builder.parentFragment,
                                                    builder.title,
                                                    builder.message,
                                                    builder.btnPositiveText,
                                                    builder.btnNegativeText,
                                                    builder.cancelable,
                                                    builder.tag,
                                                    builder.viewResCustom,
                                                    builder.otherStyle,
                                                    builder.themeStyle)

    /**
     * A builder of [QuickDialogBindingFragment].
     */
    class Builder<B : ViewDataBinding> {
        constructor(activity: AppCompatActivity, block: Builder<B>.() -> Unit) {
            this.activity = activity
            parentFragment = null
            apply(block)
        }

        constructor(fragment: Fragment, block: Builder<B>.() -> Unit) {
            activity = null
            parentFragment = fragment
            apply(block)
        }

        val activity: AppCompatActivity?
        val parentFragment: Fragment?
        var title = ""
        var message = ""
        var btnNegativeText: DFBtn? = null
        var btnPositiveText: DFBtn? = null
        var cancelable = true
        var tag = "default"
        @LayoutRes
        var viewResCustom = -1
        var otherStyle: Pair<Int, Int>? = null
        var themeStyle: Int? = null

        fun build() = QuickDialogBindingFragment(this)
    }

    fun show() = show((mFragment?.fragmentManager ?: mActivity?.supportFragmentManager), mTag)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        otherStyle.takeIf { null != it }?.let { setStyle(it.first, it.second) }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // If viewResCustom is set then create a custom fragment; otherwise, just using simple AlertDialog.
        val dialog = if (0 < viewCustom) {
            super.onCreateDialog(savedInstanceState)
        }
        else {
            (if (null != themeStyle) AlertDialog.Builder(activity!!, themeStyle!!) else AlertDialog.Builder(activity!!))
                .create()
                .also {
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
            title?.let(it::setTitle)
            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
            it.setCanceledOnTouchOutside(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        if (0 < viewCustom) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity?.applicationContext),
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