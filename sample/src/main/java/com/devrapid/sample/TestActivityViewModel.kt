package com.devrapid.sample

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import android.view.View

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class TestActivityViewModel : BaseObservable() {
    val test by lazy { ObservableField<String>("Hello world!!!!!") }

    fun click(view: View) {
    }
}
