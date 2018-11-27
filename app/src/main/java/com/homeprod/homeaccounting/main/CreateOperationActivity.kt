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
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.exceptions.IncompleteDataException
import com.homeprod.homeaccounting.main.exceptions.InsufficientFundsException
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository
import com.homeprod.homeaccounting.main.utils.DropDownAdapter
import com.homeprod.homeaccounting.main.utils.showToast
import kotlinx.android.synthetic.main.activity_create_operation.*

class CreateOperationActivity : AppCompatActivity() {

    private val fromAdapter by lazy { DropDownAdapter(this, AccountRepository.all()) }
    private val toAdapter by lazy { DropDownAdapter(this, AccountRepository.all()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_operation)
        if (AccountRepository.all().size == 1) {
            rbTransaction.visibility = View.INVISIBLE
        }
        btnSave.setOnClickListener {
            if (etValue.text.isEmpty() || etDescription.text.isEmpty()) {
                showToast(IncompleteDataException())
                return@setOnClickListener
            }
            val type = when {
                rbIncome.isChecked -> Operation.OperationType.INCOME
                rbOutgo.isChecked -> Operation.OperationType.OUTGO
                else -> Operation.OperationType.TRANSACTION
            }
            try {
                OperationsRepository.createOperation(
                    Operation(
                        type,
                        etValue.text.toString().toInt(),
                        fromAdapter.getItem(spFrom.selectedItemPosition)!!,
                        toAdapter.getItem(spTo.selectedItemPosition)!!,
                        etDescription.text.toString()
                    )
                )
                finish()
            } catch (e: InsufficientFundsException) {
                showToast(e)
            }
        }
        spFrom.adapter = fromAdapter
        spTo.adapter = toAdapter

        rbIncome.setOnCheckedChangeListener { _, b ->
            if (b) {
                spFrom.visibility = View.GONE
                titleFrom.visibility = View.GONE
                spTo.visibility = View.VISIBLE
                titleTo.visibility = View.VISIBLE
            }
        }
        rbOutgo.setOnCheckedChangeListener { _, b ->
            if (b) {
                spTo.visibility = View.GONE
                titleTo.visibility = View.GONE
                spFrom.visibility = View.VISIBLE
                titleFrom.visibility = View.VISIBLE
            }
        }
        rbTransaction.setOnCheckedChangeListener { _, b ->
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