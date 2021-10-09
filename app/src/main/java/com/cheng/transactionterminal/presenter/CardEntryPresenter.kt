package com.cheng.transactionterminal.presenter

import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.contract.ICardEntryPresenter
import com.cheng.transactionterminal.contract.ICardEntryView
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.usecase.ExpiryTimeUtil
import com.cheng.transactionterminal.usecase.ExpiryTimeValidationResult

class CardEntryPresenter(private val view: ICardEntryView) : ICardEntryPresenter {

    override fun submitTransaction(
        cardNumber: CharSequence?,
        expiry: CharSequence?,
        cvv: CharSequence?,
        cardStored: Boolean,
        moToType: MoToType?,
        noCvvReason: NoCvvReason?
    ) {
        var isValid = true

        if (cardNumber.isNullOrBlank()) {
            isValid = false
            view.setCardNumberError(R.string.warning_invalid_card_number)
        } else {
            view.setCardNumberError(0)
        }

        val expiryWarningId = when (ExpiryTimeUtil.validateExpiryTime(expiry.toString())) {
            ExpiryTimeValidationResult.CARD_EXPIRED -> R.string.warning_card_expired
            ExpiryTimeValidationResult.INVALID_TIME -> R.string.warning_invalid_month_year
            else ->
                if (expiry.isNullOrBlank()) R.string.warning_invalid_month_year
                else 0
        }
        if (expiryWarningId != 0) {
            isValid = false
        }
        view.setExpiryError(expiryWarningId)

        if (moToType == null) {
            isValid = false
            view.setMotoTypeError(R.string.warning_moto_type_missing)
        } else {
            view.setMotoTypeError(0)
        }

        if (cvv.isNullOrBlank()) {
            view.showNoCvvReason()
            if (noCvvReason == null) {
                isValid = false
                view.setNoCvvReasonError(R.string.warning_no_cvv_reason_missing)
            } else {
                view.setNoCvvReasonError(0)
            }
        }

        if (isValid) {
            // TODO: save the transaction

            view.onTransactionSaved()
        }
    }

}
