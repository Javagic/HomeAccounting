/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 23.11.18 18:38
 */

package com.homeprod.homeaccounting.main.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.exceptions.IncompleteDataException
import com.homeprod.homeaccounting.main.exceptions.InsufficientFundsException
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository
import com.homeprod.homeaccounting.main.utils.DropDownAdapter
import com.homeprod.homeaccounting.main.utils.showToast
import com.homeprod.homeaccounting.main.viewModel.OperationViewModel
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
            onSave()
        }
        spFrom.adapter = fromAdapter
        spTo.adapter = toAdapter
        initRadioButtonListeners()
        initModelListener()
    }

    private fun applyViewModel() {
        OperationViewModel.apply {
            etValue.setText(value)
            etDescription.setText(description)
            when (operationType) {
                Operation.OperationType.INCOME -> rbIncome.isChecked = true
                Operation.OperationType.OUTGO -> rbOutgo.isChecked = true
                Operation.OperationType.TRANSACTION -> rbTransaction.isChecked = true
            }
            spFrom.setSelection(fromAdapter.getPosition(from))
            spTo.setSelection(toAdapter.getPosition(to))
        }
    }

    override fun onResume() {
        super.onResume()
        applyViewModel()
    }

    private fun initModelListener() {
        etValue.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                OperationViewModel.value = text.toString()
            }
        })
        etDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                OperationViewModel.description = text.toString()
            }
        })
        spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(item: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                OperationViewModel.from = fromAdapter.getItem(position)!!
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(item: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                OperationViewModel.to = toAdapter.getItem(position)!!
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun onSave() {
        if (etValue.text.isEmpty() || etDescription.text.isEmpty()) {
            showToast(IncompleteDataException())
            return
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

    private fun initRadioButtonListeners() {
        rbIncome.setOnCheckedChangeListener { _, b ->
            if (b) {
                spFrom.visibility = View.GONE
                titleFrom.visibility = View.GONE
                spTo.visibility = View.VISIBLE
                titleTo.visibility = View.VISIBLE
            }
            OperationViewModel.operationType = Operation.OperationType.INCOME
        }
        rbOutgo.setOnCheckedChangeListener { _, b ->
            if (b) {
                spTo.visibility = View.GONE
                titleTo.visibility = View.GONE
                spFrom.visibility = View.VISIBLE
                titleFrom.visibility = View.VISIBLE
            }
            OperationViewModel.operationType = Operation.OperationType.OUTGO

        }
        rbTransaction.setOnCheckedChangeListener { _, b ->
            if (b) {
                spTo.visibility = View.VISIBLE
                titleTo.visibility = View.VISIBLE
                spFrom.visibility = View.VISIBLE
                titleFrom.visibility = View.VISIBLE
            }
            OperationViewModel.operationType = Operation.OperationType.TRANSACTION

        }
    }

}