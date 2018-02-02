package com.devrapid.dialogbuilder.typedata

import android.content.DialogInterface
import android.view.View
import com.devrapid.dialogbuilder.support.DialogFragmentTemplate
import com.devrapid.dialogbuilder.support.QuickDialogBindingFragment
import java.util.ArrayList

/**
 * @author  jieyi
 * @since   11/14/17
 */
    /**
     * [String]: the text of the button.
     * [(dialog: DialogInterface) -> Unit]: the button's callback function.
     */
typealias DFBtn = Pair<String, (dialog: DialogInterface) -> Unit>
typealias DFListeners = ArrayList<Pair<Int, (com.devrapid.dialogbuilder.DialogFragmentTemplate, View?) -> Unit>>
    /**
     * [Int]: the resource id of a view.
     * [(com.devrapid.dialogbuilder.QuickDialogBindingFragment<B>, View?) -> Unit>]: the view's click listener.
     */
typealias DSFListeners = ArrayList<Pair<Int, (DialogFragmentTemplate, View?) -> Unit>>
typealias DFBListeners<B> = ArrayList<Pair<Int, (com.devrapid.dialogbuilder.QuickDialogBindingFragment<B>, View?) -> Unit>>
typealias DSFBListeners<B> = ArrayList<Pair<Int, (QuickDialogBindingFragment<B>, View?) -> Unit>>
