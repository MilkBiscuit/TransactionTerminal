package com.cheng.transactionterminal.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.usecase.CardNumberUtil
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.WeakReference

class CardNumberTextWatcher(
    private val contextRef: WeakReference<Context>,
    private val editTextRef: WeakReference<EditText>,
    private val inputLayoutRef: WeakReference<TextInputLayout>
): TextWatcher {

    override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
        val input = charSequence.toString()
        val inputLength = input.length
        val formattedInput = CardNumberUtil.formatCardNumber(input)
        val formattedLength = formattedInput.length
        if (input != formattedInput) {
            editTextRef.get()?.apply {
                setText(formattedInput)

                /**
                 * Given the current text field is not empty
                 * When copy and paste a string into the beginning or middle of the existing text
                 * The cursor position needs alignment
                 *
                 * However, it is such a rare use case, doesn't seem necessary here
                 */
                if (start + count == inputLength) {
                    // Cursor is at the end of the Edit Text
                    setSelection(formattedLength)
                } else if (count == 1 && cursorAtTheEndOfSection(start)) {
                    // Cursor is at the end of a card number section, and a SPACE was just added at the cursor position
                    setSelection(start + count + 1)
                } else {
                    setSelection(start + count)
                }
            }
        }
        if (formattedLength >= CardNumberUtil.CARD_NUMBER_MIN_LENGTH) {
            val cardNumberString = CardNumberUtil.removeWhiteSpace(input)
            if (CardNumberUtil.isMasterCard(cardNumberString)) {
                inputLayoutRef.get()?.helperText = contextRef.get()?.getText(R.string.helper_text_master_card)
            } else if (CardNumberUtil.isVisaCard(cardNumberString)) {
                inputLayoutRef.get()?.helperText = contextRef.get()?.getText(R.string.helper_text_visa_card)
            }
            inputLayoutRef.get()?.error = null
        } else {
            inputLayoutRef.get()?.helperText = null
        }
    }

    override fun afterTextChanged(editable: Editable) {
    }

    private fun cursorAtTheEndOfSection(cursorPosition: Int): Boolean {
        if (cursorPosition <= 0 || cursorPosition > CardNumberUtil.CARD_NUMBER_MAX_LENGTH) {
            return false
        }

        return when (cursorPosition) {
            CardNumberUtil.CARD_NUMBER_SECTION_LENGTH,
            CardNumberUtil.CARD_NUMBER_MAX_LENGTH -> true
            CardNumberUtil.CARD_NUMBER_SECTION_LENGTH * 2 + 1 -> true
            CardNumberUtil.CARD_NUMBER_SECTION_LENGTH * 3 + 2 -> true
            CardNumberUtil.CARD_NUMBER_SECTION_LENGTH * 4 + 3 -> true
            else -> false
        }
    }

}
