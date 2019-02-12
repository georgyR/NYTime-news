package com.androidacademy.msk.exerciseproject.utils

object EnumUtils {

    @JvmStatic
    fun <T : Enum<T>> convertEnumValuesToCapitalizedList(enumArray: Array<T>): List<String> {
        return enumArray.map { it.toString().toLowerCase().capitalize() }
    }
}