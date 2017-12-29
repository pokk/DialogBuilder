package taiwan.no.one.lib.typedata

import android.content.DialogInterface
import android.view.View
import taiwan.no.one.lib.support.DialogFragmentTemplate
import java.util.ArrayList

/**
 * @author  jieyi
 * @since   11/14/17
 */
typealias DFBtn = Pair<String, (DialogInterface, Int) -> Unit>
typealias DFListeners = ArrayList<Pair<Int, (taiwan.no.one.lib.DialogFragmentTemplate, View?) -> Unit>>
typealias DSFListeners = ArrayList<Pair<Int, (DialogFragmentTemplate, View?) -> Unit>>
//typealias DFBListeners<B> = ArrayList<Pair<Int, (taiwan.no.one.lib.QuickDialogBindingFragment<B>, View?) -> Unit>>
//typealias DSFBListeners<B> = ArrayList<Pair<Int, (QuickDialogBindingFragment<B>, View?) -> Unit>>
