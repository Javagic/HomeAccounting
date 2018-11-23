/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:33
 */

package com.homeprod.homeaccounting.main

object AccountRepository {
    private val accountList = ArrayList<Account>()
    fun createAccount(account: Account) {
        accountList.add(account)
    }

    fun all() = ArrayList<Account>().apply { addAll(accountList) }
}