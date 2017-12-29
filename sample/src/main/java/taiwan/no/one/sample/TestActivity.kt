package taiwan.no.one.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.btn
import taiwan.no.one.lib.support.QuickDialogBindingFragment
import taiwan.no.one.sample.databinding.FragmentDialogBindBinding

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            //            QuickDialogFragment.Builder(this) {
//                viewCustom = R.layout.fragment_dialog_test
//            }.build().apply {
//
//            }
            QuickDialogBindingFragment.Builder<FragmentDialogBindBinding>(this) {
                viewCustom = R.layout.fragment_dialog_bind
            }.build().apply {
                bind = { it.vm = TestActivityViewModel() }
            }.show()
        }
    }
}