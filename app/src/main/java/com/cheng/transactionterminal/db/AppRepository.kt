package com.cheng.transactionterminal.db

import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.TransactionRecord
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

    fun searchTransactionsByCardNumber(lastFourDigits: String): List<TransactionRecord> {
        val transactionRecordList = mutableListOf<TransactionRecord>()
        val bankCardWithTransactions = bankCardWithTransactionsList.filter {
            it.bankCard.cardNumber.endsWith(lastFourDigits)
        }
        // The result may contain record from other consumers whose cards have the same last 4 digits
        for (cardWithTransaction in bankCardWithTransactions) {
            transactionRecordList.addAll(cardWithTransaction.transactionRecords)
        }

        return transactionRecordList
    }

    fun insertTransaction(amountInCents: Int, date: Date, bankCard: BankCard, motoType: MoToType) {
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
            motoType
        )
        transactionRecordDao.insert(transactionRecord)
    }
}
