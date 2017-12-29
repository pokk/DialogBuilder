package taiwan.no.one.sample

import android.databinding.BaseObservable
import android.databinding.ObservableField

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class TestActivityViewModel : BaseObservable() {
    val test by lazy { ObservableField<String>("Hello world!!!!!") }
}