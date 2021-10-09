package com.cheng.transactionterminal.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.usecase.ExpiryTimeUtil
import com.cheng.transactionterminal.usecase.ExpiryTimeValidationResult
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

class ExpiryTimeTextWatcher(
    private val contextRef: WeakReference<Context>,
    private val editTextRef: WeakReference<EditText>,
    private val inputLayoutRef: WeakReference<TextInputLayout>
): TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val input = charSequence.toString()
        val inputLength = input.length
        val formattedInput = ExpiryTimeUtil.formatExpiryTime(input)
        // count is 0 means the user is deleting inputs, should NOT format text otherwise will stuck at "11/"
        if (inputLength != formattedInput.length && count != 0) {
            editTextRef.get()?.let {
                it.setText(formattedInput)
                it.setSelection(formattedInput.length)
            }
        }
        if (inputLength >= 4) {
            // e.g. "8/20", "11/20"
            val errorText = when (ExpiryTimeUtil.validateExpiryTime(input)) {
                ExpiryTimeValidationResult.INVALID_TIME -> contextRef.get()?.getString(R.string.warning_invalid_month_year)
                ExpiryTimeValidationResult.CARD_EXPIRED -> contextRef.get()?.getString(R.string.warning_card_expired)
                else -> null
            }
            inputLayoutRef.get()?.error = errorText
        }
    }

    override fun afterTextChanged(editable: Editable) {
    }

}
