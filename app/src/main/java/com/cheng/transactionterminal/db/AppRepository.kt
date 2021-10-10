package com.cheng.transactionterminal.db

import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.entity.TransactionRecord
import com.cheng.transactionterminal.usecase.CardNumberUtil
import com.cheng.transactionterminal.usecase.StringUtil
import java.util.*

class AppRepository(
    private val bankCardDao: BankCardDao,
    private val transactionRecordDao: TransactionRecordDao
) {
    private val bankCardWithTransactionsList: MutableList<BankCardWithTransactions> = mutableListOf()

    fun fetchAllBankCardWithTransactions() {
        val cardsWithTransactions = bankCardDao.getBankCardAndTransactions()
        bankCardWithTransactionsList.clear()
        bankCardWithTransactionsList.addAll(cardsWithTransactions)
    }

    fun searchTransactionsByCardNumber(lastFourDigits: String): List<BankCardWithTransactions> {
        if (lastFourDigits.isBlank() || lastFourDigits.length != 4) {
            return emptyList()
        }

        // The result may contain record from other consumers whose cards have the same last 4 digits
        val bankCards = bankCardWithTransactionsList.filter {
            CardNumberUtil.decryptCardNumber(it.bankCard.cardNumber).endsWith(lastFourDigits)
        }
        val decryptedBankCards = mutableListOf<BankCardWithTransactions>()
        bankCards.forEach {
            val bankCard = it.bankCard
            val decryptedNumber = CardNumberUtil.decryptCardNumber(bankCard.cardNumber)
            val decryptedCard = BankCard(decryptedNumber, bankCard.expiryDate, bankCard.cvv, bankCard.id)
            decryptedBankCards.add(
                BankCardWithTransactions(decryptedCard, it.transactionRecords)
            )
        }

        return decryptedBankCards
    }

    fun insertTransaction(amountInCents: Int, date: Date,
                          bankCard: BankCard,
                          motoType: MoToType,
                          noCvvReason: NoCvvReason?,
                          isCardStored: Boolean
    ) {
        var cardId: Long?
        val cardInDb = bankCardDao.findByBankCardNum(bankCard.cardNumber)
        if (cardInDb == null) {
            bankCardDao.insert(bankCard).also { ids ->
                cardId = ids[0]
            }
        } else {
            cardId = cardInDb.id
        }

        val transactionRecord = TransactionRecord(
            amountInCents,
            cardId!!,
            date.time,
            motoType,
            noCvvReason,
            isCardStored
        )
        transactionRecordDao.insert(transactionRecord)
    }
}
