package com.devrapid.dialogbuilder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devrapid.dialogbuilder.typedata.DFBtn

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogFragment private constructor(protected val _activity: Activity?,
                                              protected val _fragment: Fragment?,
    //region Alert Dialog Parameters
                                              /** This is for Alert Dialog Parameter. */
                                              protected var _title: String?,
                                              protected var _message: String = "",
                                              protected val _btnPositive: DFBtn?,
                                              protected val _btnNegative: DFBtn?,
                                              protected val _cancelable: Boolean,
                                              protected val _tag: String,
    //endregion
    //region Customize View Parameters
                                              /**
                                               *  The below parameters are for the customization view.
                                               *  Once view is set, the parameters above here will be ignored.
                                               */
                                              @LayoutRes
                                              protected val _viewCustom: Int,
                                              protected var _fetchComponents: ((View) -> Unit)? = {}
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
                                                                        _fetchComponents) {
    private val viewList by lazy { mutableListOf<View>() }

    init {
        isCancelable = mCancelable
    }

    private constructor(builder: Builder) : this(builder.activity,
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
                                                 builder.viewCustom,
                                                 builder.fetchComponents
        //endregion
                                                )

    /**
     * A builder of [QuickDialogFragment].
     */
    class Builder : DialogFragmentTemplate.Builder {
        constructor(activity: Activity, block: DialogFragmentTemplate.Builder.() -> Unit) : super(activity, block)

        constructor(fragment: Fragment, block: DialogFragmentTemplate.Builder.() -> Unit) : super(fragment, block)

        override fun build() = QuickDialogFragment(this)
    }

    override fun provideView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        if (0 < viewCustom) {
            LayoutInflater
                .from(activity.applicationContext)
                .inflate(viewCustom, null)
                .also { onCreateDialogView(it) }
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
}