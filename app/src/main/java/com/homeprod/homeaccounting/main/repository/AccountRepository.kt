
package com.homeprod.homeaccounting.main.repository

import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.exceptions.InsufficientFundsException

object AccountRepository {
    private val accountList = ArrayList<Account>()
    fun createAccount(account: Account) {
        accountList.add(account)
    }

    fun all() = ArrayList<Account>().apply { addAll(accountList) }

    fun incrementValue(account: Account, value: Int) {
        accountList.find { it.name == account.name }?.let {
            it.value += value
        }
    }

    fun decrementValue(account: Account, value: Int) {
        accountList.find { it.name == account.name }?.let {
            if (it.value < value) throw InsufficientFundsException()
            it.value -= value
        }
    }

    fun deleteAccount(account: Account){
        accountList.remove(account)
    }
}