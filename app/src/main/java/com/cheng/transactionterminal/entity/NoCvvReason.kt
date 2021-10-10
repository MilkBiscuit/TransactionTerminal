package com.cheng.transactionterminal.entity

enum class NoCvvReason {
    NoCvvOnCard,
    NoCardPresent,
    UnableToRead;

    companion object {
        fun toNoCvvReason(reason: String?): NoCvvReason? {
            return values().find { it.name == reason }
        }
    }
}
