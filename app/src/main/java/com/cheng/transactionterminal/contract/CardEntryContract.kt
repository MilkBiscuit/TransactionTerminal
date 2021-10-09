package com.cheng.transactionterminal.contract

import androidx.annotation.StringRes
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason

interface ICardEntryView {
    fun setCardNumberError(@StringRes stringResId: Int)
    fun setExpiryError(@StringRes stringResId: Int)
    fun setMotoTypeError(@StringRes stringResId: Int)
    fun setNoCvvReasonError(@StringRes stringResId: Int)
    fun showNoCvvReason()
    fun hideNoCvvReason()
    fun onTransactionSaved()
}

interface ICardEntryPresenter {
    fun submitTransaction(
        cardNumber: CharSequence?,
        expiry: CharSequence?,
        cvv: CharSequence?,
        cardStored: Boolean,
        moToType: MoToType?,
        noCvvReason: NoCvvReason?
    )
}
