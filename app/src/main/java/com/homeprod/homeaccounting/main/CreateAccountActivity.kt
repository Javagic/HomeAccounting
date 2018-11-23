/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:39
 */

package com.homeprod.homeaccounting.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.homeprod.homeaccounting.R
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        btnSave.setOnClickListener {
            AccountRepository.createAccount(
                Account(
                    etName.text.toString(),
                    etDescription.text.toString()
                )
            )
            finish()
        }
    }
}