/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:48
 */

package com.homeprod.homeaccounting.main

object OperationsRepository {
    private val operationsList = ArrayList<Operation>()
    fun addOperation(operation: Operation) {
        operationsList.add(operation)
    }
    fun all() = ArrayList<Operation>().apply { addAll(operationsList) }

}