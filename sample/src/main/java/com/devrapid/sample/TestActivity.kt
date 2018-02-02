package com.devrapid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.devrapid.dialogbuilder.support.QuickDialogFragment
import kotlinx.android.synthetic.main.activity_main.btn
import kotlinx.android.synthetic.main.activity_main.view.btn
import kotlinx.android.synthetic.main.fragment_dialog_test.view.tv

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class TestActivity : AppCompatActivity() {
    /** Customization View */
    private val dialog =
        QuickDialogFragment.Builder(this) {
            viewResCustom = R.layout.fragment_dialog_test
            fetchComponents = { v ->
                v.btn.setOnClickListener { /* What you want to do! */ }
                v.tv.text = "I was clicked!"
            }
        }.build()

    /** Normal Alert Dialog */
    private val originalDialog = QuickDialogFragment.Builder(this) {
        btnNegativeText = "negative" to { d -> /* What you want to do! */ }
        btnPositiveText = "positive" to { d -> /* What you want to do! */ }
        message = "The is message!"
        title = "This is title!"
        cancelable = false
    }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            // Normal Alert Dialog
//            originalDialog.show()
            // Normal Customization
            dialog.show()

            // Data binding
//            QuickDialogBindingFragment.Builder<FragmentDialogBindBinding>(this) {
//                viewResCustom = R.layout.fragment_dialog_bind
//            }.build().apply {
//                bind = {
//                    it.vm = TestActivityViewModel()
//                }
//            }.show()
        }
    }

    override fun onPause() {
        dialog.dismiss()
        originalDialog.dismiss()
        super.onPause()
    }
}