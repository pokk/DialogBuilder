package com.devrapid.dialogbuilder.support

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.devrapid.dialogbuilder.typedata.DFBtn

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogFragment private constructor(
    _activity: AppCompatActivity?,
    _fragment: Fragment?,
    //region Alert Dialog Parameters
    /** This is for Alert Dialog Parameter. */
    private var _title: String = "",
    private var _message: String = "",
    private val _btnPositive: DFBtn?,
    private val _btnNegative: DFBtn?,
    private val _cancelable: Boolean,
    private val _tag: String,
    //endregion
    //region Customize View Parameters
    /**
     *  The below parameters are for the customization view.
     *  Once view is set, the parameters above here will be ignored.
     */
    @LayoutRes
    private val _viewCustom: Int,
    @StyleRes
    private var _themeStyle: Int? = null,
    private var _fetchComponents: ((View, DialogFragment) -> Unit)? = null,
    private var _onStartBlock: ((DialogFragment) -> Unit)? = null
    //endregion
) : DialogFragmentTemplate(_activity,
                           _fragment,
                           _title,
                           _message,
                           _btnPositive,
                           _btnNegative,
                           _cancelable,
                           _tag,
                           _viewCustom,
                           _themeStyle,
                           _fetchComponents,
                           _onStartBlock) {
    init {
        isCancelable = mCancelable
    }

    private constructor (builder: QuickDialogFragment.Builder) : this(builder.activity,
                                                                      builder.parentFragment,
        //region Alert Dialog Parameters
                                                                      builder.title,
                                                                      builder.message,
                                                                      builder.btnPositiveText,
                                                                      builder.btnNegativeText,
                                                                      builder.cancelable,
                                                                      builder.tag,
        //endregion
        //region Customize View Parameters
                                                                      builder.viewResCustom,
                                                                      builder.themeStyle,
                                                                      builder.fetchComponents,
                                                                      builder.onStartBlock
        //endregion
    )

    class Builder : DialogFragmentTemplate.Builder {
        constructor(activity: AppCompatActivity, block: DialogFragmentTemplate.Builder.() -> Unit) :
            super(activity, block)

        constructor(fragment: Fragment, block: DialogFragmentTemplate.Builder.() -> Unit) :
            super(fragment, block)

        override fun build() = QuickDialogFragment(this)
    }

    override fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        if (0 < viewCustom) {
            LayoutInflater
                .from(activity?.applicationContext)
                .inflate(viewCustom, null)
                .also(::onCreateDialogView)
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
}
