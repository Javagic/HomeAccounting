
package com.homeprod.homeaccounting.main.data

data class Operation(
    val type: OperationType,
    val value: Int,
    val from: Account,
    val to: Account,
    val description: String,
    val time: Long = System.currentTimeMillis()

) {
    enum class OperationType {
        INCOME,
        OUTGO,
        TRANSACTION;
    }
}