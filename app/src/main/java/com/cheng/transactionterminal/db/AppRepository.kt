package com.cheng.transactionterminal.db

import androidx.annotation.WorkerThread
import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.TransactionRecord

class AppRepository(
    private val bankCardDao: BankCardDao,
    private val transactionRecordDao: TransactionRecordDao
) {
    // TODO: fetch all transactions saved in DB

    fun insertTransaction(amountInCents: Int, bankCard: BankCard, moToType: MoToType) {
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
            moToType
        )
        transactionRecordDao.insert(transactionRecord)
    }
}
