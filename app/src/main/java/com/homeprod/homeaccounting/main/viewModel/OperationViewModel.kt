
package com.homeprod.homeaccounting.main.viewModel

import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.repository.AccountRepository

object OperationViewModel {
    var operationType = Operation.OperationType.INCOME
    var description: String = ""
    var value: String = ""
    var from: Account = AccountRepository.all().first()
    var to: Account = AccountRepository.all().first()
    fun clear() {
        operationType = Operation.OperationType.INCOME
        description = ""
        value = ""
        from = AccountRepository.all().first()
        to = AccountRepository.all().first()
    }

}