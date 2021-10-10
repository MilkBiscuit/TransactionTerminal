package com.cheng.transactionterminal.presenter

import com.cheng.transactionterminal.MainApplication
import com.cheng.transactionterminal.contract.ISearchTransactionPresenter
import com.cheng.transactionterminal.contract.ISearchTransactionView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchTransactionPresenter(private val view: ISearchTransactionView): ISearchTransactionPresenter {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            // Search database and cache the result in the memory
            MainApplication.appRepository?.fetchAllBankCardWithTransactions()
        }
    }

    override fun searchTransaction(cardNumberLastFour: String) {
        val transactions = MainApplication.appRepository?.searchTransactionsByCardNumber(cardNumberLastFour)
        if (transactions.isNullOrEmpty()) {
            view.showResult(emptyList())
        } else {
            view.showResult(transactions)
        }
    }

}
