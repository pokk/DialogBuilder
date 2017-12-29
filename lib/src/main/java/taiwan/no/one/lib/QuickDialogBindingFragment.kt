package taiwan.no.one.lib

/**
 * @author  jieyi
 * @since   11/14/17
 */
//@SuppressLint("ValidFragment")
//class QuickDialogBindingFragment<B : ViewDataBinding> private constructor(val mActivity: Activity?,
//                                                                          val mFragment: Fragment?,
//                                                                          val btnPositive: DFBtn?,
//                                                                          val btnNegative: DFBtn?,
//                                                                          val clickListeners: DFBListeners<B>?,
//                                                                          val mCancelable: Boolean,
//                                                                          val mTag: String,
//    // TODO(jieyi): 7/12/17 Implement the request code function.
//                                                                          val requestCode: Int,
//                                                                          @LayoutRes
//                                                                          val viewCustom: Int,
//                                                                          var fetchComponents: ((View) -> Unit)? = {},
//                                                                          var message: String = "",
//                                                                          var title: String?) : DialogFragment() {
//    var bind: (binding: B) -> Unit = {}
//    private val viewList by lazy { mutableListOf<View>() }
//    lateinit private var binding: B
//
//    init {
//        isCancelable = mCancelable
//    }
//
//    private constructor(builder: Builder<B>) : this(builder.activity,
//        builder.parentFragment,
//        builder.btnPositiveText,
//        builder.btnNegativeText,
//        builder.clickListener,
//        builder.cancelable,
//        builder.tag,
//        builder.requestCode,
//        builder.viewCustom,
//        builder.fetchComponents,
//        builder.message.orEmpty(),
//        builder.title)
//
//    /**
//     * A builder of [QuickDialogBindingFragment].
//     */
//    class Builder<B : ViewDataBinding> {
//        constructor(activity: Activity, block: Builder<B>.() -> Unit) {
//            this.activity = activity
//            parentFragment = null
//            apply(block)
//        }
//
//        constructor(fragment: Fragment, block: Builder<B>.() -> Unit) {
//            activity = null
//            parentFragment = fragment
//            apply(block)
//        }
//
//        val activity: Activity?
//        val parentFragment: Fragment?
//        var fetchComponents: ((View) -> Unit)? = null
//        var btnNegativeText: DFBtn? = null
//        var btnPositiveText: DFBtn? = null
//        var cancelable: Boolean = true
//        var clickListener: DFBListeners<B>? = null
//        var message: String? = null
//        var requestCode: Int = -1
//        var tag: String = "default"
//        var title: String? = null
//        @LayoutRes
//        var viewCustom: Int = -1
//
//        fun build() = QuickDialogBindingFragment(this)
//    }
//
//    fun show() = show((mFragment?.fragmentManager ?: mActivity?.fragmentManager), mTag)
//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
//        if (0 < viewCustom) {
//            binding = DataBindingUtil.inflate(LayoutInflater.from(activity.applicationContext),
//                viewCustom,
//                null,
//                false)!!
//            binding.root
//        }
//        else {
//            super.onCreateView(inflater, container, savedInstanceState)
//        }
//
//    fun onCreateDialogView(view: View?) {
//        view?.let {
//            // Fetch the components from a view.
//            fetchComponents?.let { self -> self(it) }
//            // Set the listener into each of views.
//            clickListeners?.forEach { (id, listener) ->
//                viewList.add(it.findViewById<View>(id).apply {
//                    setOnClickListener {
//                        listener(this@QuickDialogBindingFragment, it)
//                    }
//                })
//            }
//            bind(binding)
//        }
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        // If viewCustom is set then create a custom fragment; otherwise, just using simple AlertDialog.
//        val dialog = if (0 < viewCustom) {
//            super.onCreateDialog(savedInstanceState)
//        }
//        else {
//            AlertDialog.Builder(activity).create().also {
//                message.takeIf { it.isNotBlank() }.let(it::setMessage)
//                btnPositive?.let { (text, listener) ->
//                    it.setButton(Dialog.BUTTON_POSITIVE, text, listener)
//                }
//                btnNegative?.let { (text, listener) ->
//                    it.setButton(Dialog.BUTTON_NEGATIVE, text, listener)
//                }
//            }
//        }
//
//        return dialog.also {
//            title?.let(it::setTitle)
//            it.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            it.setCanceledOnTouchOutside(true)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        if (0 < viewCustom) {
//            dialog.window.setLayout(resources.displayMetrics.widthPixels, resources.displayMetrics.heightPixels)
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        fetchComponents = null
//        if (0 < viewCustom) {
//            viewList.forEach { it.setOnClickListener(null) }
//            viewList.clear()
//        }
//    }
//}