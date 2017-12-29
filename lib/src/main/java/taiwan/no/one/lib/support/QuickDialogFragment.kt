package taiwan.no.one.lib.support

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import taiwan.no.one.lib.typedata.DFBtn
import taiwan.no.one.lib.typedata.DSFListeners

/**
 * @author  jieyi
 * @since   11/14/17
 */
@SuppressLint("ValidFragment")
class QuickDialogFragment private constructor(val _activity: AppCompatActivity?,
                                              val _fragment: Fragment?,
                                              val _btnPositive: DFBtn?,
                                              val _btnNegative: DFBtn?,
                                              val _clickListeners: DSFListeners?,
                                              val _cancelable: Boolean,
                                              val _tag: String,
    // TODO(jieyi): 7/12/17 Implement the request code function.
                                              val _requestCode: Int,
                                              @LayoutRes
                                              val _viewCustom: Int,
                                              var _fetchComponents: ((View) -> Unit)? = {},
                                              var _message: String = "",
                                              var _title: String?) : DialogFragmentTemplate(_activity,
    _fragment,
    _btnNegative,
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

    private constructor(builder: QuickDialogFragment.Builder) : this(builder.activity,
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

    class Builder : DialogFragmentTemplate.Builder {
        constructor(activity: AppCompatActivity, block: DialogFragmentTemplate.Builder.() -> Unit) :
            super(activity, block)

        constructor(fragment: Fragment, block: DialogFragmentTemplate.Builder.() -> Unit) :
            super(fragment, block)

        override fun build() = QuickDialogFragment(this)
    }

    override fun provideView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        if (0 < viewCustom) {
            LayoutInflater.from(activity?.applicationContext).inflate(viewCustom, null).also {
                onCreateDialogView(it)
            }
        }
        else {
            super.onCreateView(inflater, container, savedInstanceState)
        }
}