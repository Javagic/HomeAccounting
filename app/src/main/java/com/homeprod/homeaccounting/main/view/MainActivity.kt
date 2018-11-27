package com.homeprod.homeaccounting.main.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.WindowManager
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.App
import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.exceptions.InsufficientFundsException
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository
import com.homeprod.homeaccounting.main.utils.*
import com.homeprod.homeaccounting.main.viewModel.AccountViewModel
import com.homeprod.homeaccounting.main.viewModel.OperationViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AccountDialog.DeleteAccountListener {

    private val operationsAdapter by lazy {
        OperationsAdapter()
    }
    private val accountAdapter by lazy {
        AccountAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        )
        setContentView(R.layout.activity_main)
        fabCreateAccount.setOnClickListener {
            AccountViewModel.clear()
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
        rvOperations.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            rvOperations.adapter = operationsAdapter
            ItemTouchHelper(SwipeListener(this@MainActivity::deleteOperation)).attachToRecyclerView(this)
        }
        rvAccounts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = accountAdapter
            ItemTouchHelper(SwipeListener(this@MainActivity::deleteAccount)).attachToRecyclerView(this)
        }
        fabCreateOperation.apply {
            setOnClickListener {
                if (AccountRepository.all().isNotEmpty()) {
                    OperationViewModel.clear()
                    startActivity(Intent(this@MainActivity, CreateOperationActivity::class.java))
                } else showToast(App.instance.getString(R.string.error_account))
            }
        }
        searchView.setOnQueryTextListener(QueryListener(operationsAdapter))
    }

    private fun deleteOperation(holder: OperationsAdapter.OperationsHolder) {
        try {
            val operation = operationsAdapter.data[holder.adapterPosition]
            OperationsRepository.deleteOperation(operation)
            operationsAdapter.remove(operation)
            accountAdapter.notifyDataSetChanged()
        } catch (e: InsufficientFundsException) {
            showToast(e)
        }
    }

    private fun deleteAccount(holder: AccountAdapter.AccountHolder) {
        if (accountAdapter.data.size != 1) AccountDialog.show(
            supportFragmentManager,
            accountAdapter.data[holder.adapterPosition]
        )
        else {
            val deleted = accountAdapter.data.first()
            AccountRepository.deleteAccount(deleted)
            accountAdapter.remove(deleted)
        }
    }

    override fun onStart() {
        super.onStart()
        operationsAdapter.data = OperationsRepository.all()
        accountAdapter.data = AccountRepository.all()
    }

    override fun onAccountSelected(deleted: Account, to: Account) {
        AccountRepository.incrementValue(to, deleted.value)
        AccountRepository.deleteAccount(deleted)
        accountAdapter.remove(deleted)

    }
}
