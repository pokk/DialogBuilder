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
typealias DFBtn = Pair<String, (DialogInterface, Int) -> Unit>
typealias DFListeners = ArrayList<Pair<Int, (com.devrapid.dialogbuilder.DialogFragmentTemplate, View?) -> Unit>>
typealias DSFListeners = ArrayList<Pair<Int, (DialogFragmentTemplate, View?) -> Unit>>
typealias DFBListeners<B> = ArrayList<Pair<Int, (com.devrapid.dialogbuilder.QuickDialogBindingFragment<B>, View?) -> Unit>>
typealias DSFBListeners<B> = ArrayList<Pair<Int, (QuickDialogBindingFragment<B>, View?) -> Unit>>
