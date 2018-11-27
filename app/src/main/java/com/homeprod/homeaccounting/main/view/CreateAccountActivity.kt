/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:39
 */

package com.homeprod.homeaccounting.main.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.viewModel.AccountViewModel
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
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                AccountViewModel.name = text.toString()
            }
        })
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                AccountViewModel.description = text.toString()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        etName.setText(AccountViewModel.name)
        etDescription.setText(AccountViewModel.description)
    }
}