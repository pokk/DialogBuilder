package com.devrapid.dialogbuilder.support

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Dialog.BUTTON_NEGATIVE
import android.app.Dialog.BUTTON_POSITIVE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.devrapid.dialogbuilder.typedata.DFBtn

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
abstract class DialogFragmentTemplate internal constructor(
    private val mActivity: AppCompatActivity?,
    private val mFragment: Fragment?,
    //region Alert Dialog Parameters
    /** This is for Alert Dialog Parameter. */
    protected var title: String = "",
    protected var message: String = "",
    private val btnPositive: DFBtn?,
    private val btnNegative: DFBtn?,
    protected val mCancelable: Boolean,
    private val mTag: String,
    //endregion
    //region Customize View Parameters
    /**
     *  The below parameters are for the customization view.
     *  Once view is set, the parameters above here will be ignored.
     */
    @LayoutRes
    protected val viewCustom: Int,
    @StyleRes
    private var themeStyle: Int? = null,
    private var fetchComponents: ((View, DialogFragment) -> Unit)? = null
    //endregion
) : DialogFragment() {
    init {
        isCancelable = mCancelable
    }

    private constructor(builder: Builder) : this(builder.activity,
                                                 builder.parentFragment,
                                                 builder.title,
                                                 builder.message,
                                                 builder.btnPositiveText,
                                                 builder.btnNegativeText,
                                                 builder.cancelable,
                                                 builder.tag,
                                                 builder.viewResCustom,
                                                 builder.themeStyle,
                                                 builder.fetchComponents)

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
        var title: String = ""
        var message: String = ""
        var btnPositiveText: DFBtn? = null
        var btnNegativeText: DFBtn? = null
        var tag: String = "default"
        var cancelable: Boolean = true
        @LayoutRes
        var viewResCustom: Int = -1
        @StyleRes
        var themeStyle: Int? = null
        var fetchComponents: ((View, DialogFragment) -> Unit)? = null

        abstract fun build(): DialogFragmentTemplate
    }

    fun show() = show((mFragment?.fragmentManager ?: mActivity?.supportFragmentManager), mTag)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // If viewResCustom is set then create a custom fragment; otherwise, just using simple AlertDialog.
        val dialog = if (0 < viewCustom) {
            themeStyle.takeIf { null != it }?.let { setStyle(STYLE_NORMAL, it) }

            super.onCreateDialog(savedInstanceState)
        }
        else {
            (if (null != themeStyle) AlertDialog.Builder(activity!!, themeStyle!!) else AlertDialog.Builder(activity!!))
                .create()
                .also {
                    message.takeIf { it.isNotBlank() }.let(it::setMessage)
                    btnPositive?.let { (text, listener) ->
                        it.setButton(BUTTON_POSITIVE, text, { dialog, _ -> listener(dialog) })
                    }
                    btnNegative?.let { (text, listener) ->
                        it.setButton(BUTTON_NEGATIVE, text, { dialog, _ -> listener(dialog) })
                    }
                }
        }

        return dialog.also {
            title.let(it::setTitle)
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
                if (MATCH_PARENT == height) height = resources.displayMetrics.heightPixels
                if (MATCH_PARENT == width) width = resources.displayMetrics.widthPixels
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        fetchComponents = null
    }

    abstract fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    protected fun onCreateDialogView(view: View?) {
        view?.let {
            // Fetch the components from a view.
            fetchComponents?.let { self -> self(it, this) }
        }
    }
}
