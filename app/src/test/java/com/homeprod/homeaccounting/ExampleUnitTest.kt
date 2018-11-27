package com.homeprod.homeaccounting

import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test_app() {
        val initialValue = 500
        val acc1 = Account("Account1", "AccountDescription1", initialValue)
        val acc2 = Account("Account2", "AccountDescription2", initialValue)
        AccountRepository.createAccount(acc1)
        AccountRepository.createAccount(acc2)
        assert(listOf(acc1, acc2) == AccountRepository.all())
        val op1 = Operation(
            Operation.OperationType.TRANSACTION,
            200,
            acc1,
            acc2,
            "OperationDescription1"
        )
        val op2 = Operation(
            Operation.OperationType.TRANSACTION,
            200,
            acc2,
            acc1,
            "OperationDescription2"
        )
        OperationsRepository.createOperation(op1)
        OperationsRepository.createOperation(op2)
        assert(listOf(op1, op2) == OperationsRepository.all())
        OperationsRepository.deleteOperation(op1)
        assert(acc1.value == initialValue + op1.value)
        deleteAccount(acc2, acc1)
        assert(acc1.value == acc2.value + op1.value + initialValue)
    }

    fun deleteAccount(deleted: Account, to: Account) {
        AccountRepository.incrementValue(to, deleted.value)
        AccountRepository.deleteAccount(deleted)
    }
}
