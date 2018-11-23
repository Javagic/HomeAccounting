/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 20.11.18 19:38
 */

package com.homeprod.homeaccounting.main

data class Operation(
    val time: Long,
    val type: OperationType,
    val value: Double,
    val from: Account,
    val to: Account,
    val description: String
) {
    enum class OperationType {
        INCOME,
        OUTGO,
        TRANSACTION;
    }
}