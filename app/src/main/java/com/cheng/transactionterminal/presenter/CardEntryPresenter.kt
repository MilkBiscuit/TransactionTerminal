package com.cheng.transactionterminal.presenter

import com.cheng.transactionterminal.contract.ICardEntryPresenter

class CardEntryPresenter : ICardEntryPresenter {

    companion object {
        // TODO: hardcode
        const val CURRENT_YEAR = 21
        const val CURRENT_MONTH = 10
    }

    override fun formatCardNumber(input: String): String {
        TODO("Not yet implemented")
    }

}
