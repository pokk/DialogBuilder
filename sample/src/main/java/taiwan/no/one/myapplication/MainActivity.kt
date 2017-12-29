package taiwan.no.one.myapplication

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.btn
import taiwan.no.one.lib.QuickDialogFragment

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            QuickDialogFragment.Builder(this) {
                viewCustom = R.layout.fragment_dialog_test
            }.build().show()
        }
    }
}