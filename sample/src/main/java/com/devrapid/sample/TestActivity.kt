package com.devrapid.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devrapid.dialogbuilder.support.QuickDialogBindingFragment
import com.devrapid.dialogbuilder.support.QuickDialogFragment
import com.devrapid.sample.databinding.FragmentDialogBindBinding
import kotlinx.android.synthetic.main.activity_main.btn1
import kotlinx.android.synthetic.main.activity_main.btn2
import kotlinx.android.synthetic.main.activity_main.btn3
import kotlinx.android.synthetic.main.fragment_dialog_test.view.btn
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
            fetchComponents = { v, df ->
                v.btn.setOnClickListener {
                    Toast.makeText(v.context, "Clicked!", Toast.LENGTH_SHORT).show()
                    /* What you want to do! */
                }
                v.tv.text = "I was clicked!"
            }
            onTransitionBlock = {
                it.window?.attributes.windowAnimations = R.style.DialogAnimation
            }
        }.build()

    /** Normal Alert Dialog */
    private val originalDialog = QuickDialogFragment.Builder(this) {
        themeStyle = R.style.OtherTheme
        btnNegativeText = "negative" to { d -> /* What you want to do! */ }
        btnPositiveText = "positive" to { d -> /* What you want to do! */ }
        message = "The is message!"
        title = "This is title!"
        cancelable = false
    }.build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            // Normal Alert Dialog
            originalDialog.show()
        }
        btn2.setOnClickListener {
            // Normal Customization
            dialog.show()
        }
        btn3.setOnClickListener {
            // Data binding
            QuickDialogBindingFragment.Builder<FragmentDialogBindBinding>(this) {
                viewResCustom = R.layout.fragment_dialog_bind
            }.build().apply {
                bind = {
                    it.vm = TestActivityViewModel()
                }
            }.show()
        }
    }

    override fun onPause() {
        dialog.dismiss()
//        originalDialog.dismiss()
        super.onPause()
    }
}
