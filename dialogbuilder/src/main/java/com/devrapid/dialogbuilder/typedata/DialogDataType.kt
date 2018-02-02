package com.devrapid.dialogbuilder.typedata

import android.content.DialogInterface

/**
 * @author  jieyi
 * @since   11/14/17
 */
    /**
     * [String]: the text of the button.
     * [(dialog: DialogInterface) -> Unit]: the button's callback function.
     */
typealias DFBtn = Pair<String, (dialog: DialogInterface) -> Unit>