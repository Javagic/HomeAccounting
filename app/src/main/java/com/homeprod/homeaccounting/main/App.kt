

package com.homeprod.homeaccounting.main

import android.app.Application
import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
//        TEST()
    }

    fun TEST() {
        val acc1 = Account("Account1", "AccountDescription1", 500)
        val acc2 = Account("Account2", "AccountDescription2", 500)
        AccountRepository.createAccount(acc1)
        AccountRepository.createAccount(acc2)
        OperationsRepository.createOperation(
            Operation(
                Operation.OperationType.TRANSACTION,
                200,
                acc1,
                acc2,
                "OperationDescription1"
            )
        )
        OperationsRepository.createOperation(
            Operation(
                Operation.OperationType.TRANSACTION,
                200,
                acc2,
                acc1,
                "OperationDescription2"
            )
        )
    }

}