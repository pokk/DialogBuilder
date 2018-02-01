package com.devrapid.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.devrapid.dialogbuilder.support.QuickDialogFragment
import kotlinx.android.synthetic.main.activity_main.btn

/**
 * @author  jieyi
 * @since   2017/12/29
 */
class TestActivity : AppCompatActivity() {
    private val dialog =
        QuickDialogFragment.Builder(this) { viewResCustom = R.layout.fragment_dialog_test }
            .build()
            .apply { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            // Normal
            dialog.show()

            // Databinding
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
        super.onPause()
    }
}