package com.devrapid.dialogbuilder.support

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
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
    @StyleRes private var themeStyle: Int? = null,
    private var onStartBlock: ((DialogFragment) -> Unit)? = null
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
                                                    builder.themeStyle,
                                                    builder.onStartBlock)

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
        @StyleRes
        var themeStyle: Int? = null
        var onStartBlock: ((DialogFragment) -> Unit)? = null

        fun build() = QuickDialogBindingFragment(this)
    }

    fun show() = show((mFragment?.fragmentManager ?: mActivity?.supportFragmentManager), mTag)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onStartBlock?.invoke(this)
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
                        it.setButton(Dialog.BUTTON_POSITIVE, text) { dialog, _ -> listener(dialog) }
                    }
                    btnNegative?.let { (text, listener) ->
                        it.setButton(Dialog.BUTTON_NEGATIVE, text) { dialog, _ -> listener(dialog) }
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
            binding = DataBindingUtil.inflate(inflater, viewCustom, container, false)!!
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
