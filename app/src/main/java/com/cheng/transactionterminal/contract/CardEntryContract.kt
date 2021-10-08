package com.cheng.transactionterminal.contract

interface ICardEntryView {

}

interface ICardEntryPresenter {
    fun formatCardNumber(input: String): String
}
