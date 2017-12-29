package taiwan.no.one.lib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import taiwan.no.one.lib.typedata.DFBtn
import taiwan.no.one.lib.typedata.DFListeners

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogFragment private constructor(val _activity: Activity?,
                                              val _fragment: Fragment?,
                                              val _btnPositive: DFBtn?,
                                              val _btnNegative: DFBtn?,
                                              val _clickListeners: DFListeners?,
                                              val _cancelable: Boolean,
                                              val _tag: String,
    // TODO(jieyi): 7/12/17 Implement the request _code function.
                                              val _requestCode: Int,
                                              @LayoutRes
                                              val _viewCustom: Int,
                                              var _fetchComponents: ((View) -> Unit)? = {},
                                              var _message: String = "",
                                              var _title: String?) : DialogFragmentTemplate(_activity,
    _fragment,
    _btnPositive,
    _btnNegative,
    _clickListeners,
    _cancelable,
    _tag,
    _requestCode,
    _viewCustom,
    _fetchComponents,
    _message,
    _title) {
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
        builder.viewCustom,
        builder.fetchComponents,
        builder.message.orEmpty(),
        builder.title)

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
            LayoutInflater.from(activity.applicationContext).inflate(viewCustom, null).also {
                onCreateDialogView(it)
            }
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
}