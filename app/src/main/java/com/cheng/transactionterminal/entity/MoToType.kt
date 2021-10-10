package com.cheng.transactionterminal.entity

enum class MoToType {
    Single,
    Recurring;

    companion object {
        fun toMotoType(type: String?): MoToType? {
            return values().find { it.name == type }
        }
    }
}
