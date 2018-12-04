
package com.homeprod.homeaccounting.main.repository

import com.homeprod.homeaccounting.main.data.Operation

object OperationsRepository {
    private val operationsList = ArrayList<Operation>()
    fun createOperation(operation: Operation) {
        adjustAccount(operation)
        operationsList.add(operation)
    }

    fun all() = ArrayList<Operation>().apply { addAll(operationsList) }

    fun adjustAccount(operation: Operation) {
        when (operation.type) {
            Operation.OperationType.INCOME -> AccountRepository.incrementValue(operation.to, operation.value)
            Operation.OperationType.OUTGO -> AccountRepository.decrementValue(operation.from, operation.value)
            Operation.OperationType.TRANSACTION -> {
                AccountRepository.decrementValue(operation.from, operation.value)
                AccountRepository.incrementValue(operation.to, operation.value)

            }
        }
    }

    fun deleteOperation(operation: Operation){
        when (operation.type) {
            Operation.OperationType.INCOME -> AccountRepository.decrementValue(operation.to, operation.value)
            Operation.OperationType.OUTGO -> AccountRepository.incrementValue(operation.from, operation.value)
            Operation.OperationType.TRANSACTION -> {
                AccountRepository.incrementValue(operation.from, operation.value)
                AccountRepository.decrementValue(operation.to, operation.value)
            }
        }
        operationsList.remove(operation)
    }
}