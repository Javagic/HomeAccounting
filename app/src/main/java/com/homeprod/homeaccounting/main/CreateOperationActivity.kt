/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:38
 */

package com.homeprod.homeaccounting.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.homeprod.homeaccounting.R
import kotlinx.android.synthetic.main.activity_create_operation.*

class CreateOperationActivity : AppCompatActivity() {

    val fromAdapter by lazy { DropDownAdapter(this, AccountRepository.all()) }
    val toAdapter by lazy { DropDownAdapter(this, AccountRepository.all()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_operation)
        btnSave.setOnClickListener {
            val type = when {
                rbIncome.isChecked -> Operation.OperationType.INCOME
                rbOutgo.isChecked -> Operation.OperationType.OUTGO
                else -> Operation.OperationType.TRANSACTION
            }
            OperationsRepository.addOperation(
                Operation(
                    System.currentTimeMillis(),
                    type, etValue.text.toString().toDouble(),
                    fromAdapter.getItem(spFrom.selectedItemPosition)!!,
                    toAdapter.getItem(spTo.selectedItemPosition)!!,
                    etDescription.text.toString()
                )
            )
            finish()
        }
        spFrom.adapter = fromAdapter
        spTo.adapter = toAdapter

        rbIncome.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                spFrom.visibility = View.GONE
                titleFrom.visibility = View.GONE
                spTo.visibility = View.VISIBLE
                titleTo.visibility = View.VISIBLE
            }
        }
        rbOutgo.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                spTo.visibility = View.GONE
                titleTo.visibility = View.GONE
                spFrom.visibility = View.VISIBLE
                titleFrom.visibility = View.VISIBLE
            }
        }
        rbTransaction.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                spTo.visibility = View.VISIBLE
                titleTo.visibility = View.VISIBLE
                spFrom.visibility = View.VISIBLE
                titleFrom.visibility = View.VISIBLE
            }
        }
        rbIncome.isChecked = true
    }
}