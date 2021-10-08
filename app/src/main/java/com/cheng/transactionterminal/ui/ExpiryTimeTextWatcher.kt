package com.cheng.transactionterminal.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.cheng.transactionterminal.usecase.ExpiryTimeUtil
import java.lang.ref.WeakReference

class ExpiryTimeTextWatcher(
    private val editTextRef: WeakReference<EditText>
): TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        editTextRef.get()?.let {
            val input = charSequence.toString()
            val formattedInput = ExpiryTimeUtil.formatExpiryTime(input)
            // count is 0 means the user is deleting numbers, should NOT format user input otherwise will stuck at "11/"
            if (input != formattedInput && count != 0) {
                it.setText(formattedInput)
                it.setSelection(formattedInput.length)
            }
        }
    }

    override fun afterTextChanged(editable: Editable) {
    }

}
