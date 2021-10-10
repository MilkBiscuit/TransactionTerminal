package com.cheng.transactionterminal.contract

import com.cheng.transactionterminal.entity.TransactionRecord

interface ISearchTransactionView {
    fun showResult(transactions: List<TransactionRecord>)
}

interface ISearchTransactionPresenter {
    fun searchTransaction(cardNumberLastFour: String)
}
