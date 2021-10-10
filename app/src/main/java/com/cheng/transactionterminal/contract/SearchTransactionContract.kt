package com.cheng.transactionterminal.contract

import com.cheng.transactionterminal.db.BankCardWithTransactions

interface ISearchTransactionView {
    fun showResult(transactions: List<BankCardWithTransactions>)
}

interface ISearchTransactionPresenter {
    fun searchTransaction(cardNumberLastFour: String)
}
