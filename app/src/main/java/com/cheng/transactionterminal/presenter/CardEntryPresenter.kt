package com.cheng.transactionterminal.presenter

import androidx.core.content.edit
import com.cheng.transactionterminal.MainApplication
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.contract.ICardEntryPresenter
import com.cheng.transactionterminal.contract.ICardEntryView
import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.entity.TransactionRecord
import com.cheng.transactionterminal.usecase.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CardEntryPresenter(private val view: ICardEntryView) : ICardEntryPresenter {

    override fun saveTempTransaction(
        cardNumber: CharSequence?,
        expiry: CharSequence?,
        cvv: CharSequence?,
        cardStored: Boolean,
        moToType: MoToType?,
        noCvvReason: NoCvvReason?
    ) {
        // Save current input into encrypted shared preferences
        SharedPrefHelper.defaultSharedPref.edit {
            val encryptedCardNumber = CardNumberUtil.encryptCardNumber(cardNumber?.toString() ?: "")
            putString(SharedPrefKey.KEY_ENCRYPTED_CURRENT_CARD_NUMBER, encryptedCardNumber)

            expiry?.let {
                putString(SharedPrefKey.KEY_EXPIRY_DATE, it.toString())
            }
            cvv?.let {
                putString(SharedPrefKey.KEY_CVV, it.toString())
            }
            moToType?.let {
                putString(SharedPrefKey.KEY_MOTO_TYPE, it.toString())
            }
            noCvvReason?.let {
                putString(SharedPrefKey.KEY_NO_CVV_REASON, it.toString())
            }
            putBoolean(SharedPrefKey.KEY_IS_CARD_STORED, cardStored)
        }
    }

    override fun readTempTransaction(): Pair<BankCard, TransactionRecord> {
        val encryptedNumber = SharedPrefHelper.defaultSharedPref.getString(
            SharedPrefKey.KEY_ENCRYPTED_CURRENT_CARD_NUMBER, null
        )
        val cardNumber = if (encryptedNumber == null) "" else CardNumberUtil.decryptCardNumber(encryptedNumber)
        val expiry = SharedPrefHelper.defaultSharedPref.getString(SharedPrefKey.KEY_EXPIRY_DATE, "")!!
        val cvv = SharedPrefHelper.defaultSharedPref.getString(SharedPrefKey.KEY_CVV, "")!!
        val bankCard = BankCard(cardNumber, expiry, cvv)

        val motoTypeString = SharedPrefHelper.defaultSharedPref.getString(SharedPrefKey.KEY_MOTO_TYPE, "")
        val motoType = MoToType.toMotoType(motoTypeString)
        val noCvvReasonString = SharedPrefHelper.defaultSharedPref.getString(SharedPrefKey.KEY_NO_CVV_REASON, "")
        val noCvvReason = NoCvvReason.toNoCvvReason(noCvvReasonString)
        val isCardStored = SharedPrefHelper.defaultSharedPref.getBoolean(SharedPrefKey.KEY_IS_CARD_STORED, false)
        val transactionRecord = TransactionRecord(
            0, 0, 0L,
            motoType, noCvvReason, isCardStored
        )

        return Pair(bankCard, transactionRecord)
    }

    override fun submitTransaction(
        cardNumber: CharSequence?,
        expiry: CharSequence?,
        cvv: CharSequence?,
        isCardStored: Boolean,
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
            CoroutineScope(Dispatchers.IO).launch {
                MainApplication.appRepository?.apply {
                    // TODO: amountInCents is hardcoded as 999
                    val encryptedCardNumber = StringUtil.encrypt(cardNumber.toString(), StringUtil.ENCRYPT_PASSWORD)
                    val bankCard = BankCard(encryptedCardNumber, expiry.toString(), cvv.toString())
                    insertTransaction(999, Date(), bankCard, moToType!!, noCvvReason, isCardStored)

                    // TODO: Clear saved transaction in shared preference
                    withContext(Dispatchers.Main) {
                        view.onTransactionSaved()
                    }
                }
            }
        }
    }

}
