package com.cheng.transactionterminal.db

import androidx.room.TypeConverter
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason

class Converter {
    @TypeConverter
    fun fromMotoType(motoType: MoToType): String {
        return motoType.name
    }
    @TypeConverter
    fun toMotoType(type: String): MoToType? {
        return MoToType.toMotoType(type)
    }

    @TypeConverter
    fun fromNoCvvReason(noCvvReason: NoCvvReason?): String {
        return noCvvReason?.name ?: ""
    }
    @TypeConverter
    fun toNoCvvReason(reason: String): NoCvvReason? {
        return NoCvvReason.toNoCvvReason(reason)
    }
}
