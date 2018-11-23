package com.homeprod.homeaccounting.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.homeprod.homeaccounting.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val operationsAdapter by lazy {
        OperationsAdapter()
    }
    val accountAdapter by lazy {
        AccountAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fabCreateAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
        rvOperations.layoutManager = LinearLayoutManager(this)
        rvOperations.adapter = operationsAdapter
        rvAccounts.layoutManager = LinearLayoutManager(this)
        rvAccounts.adapter = accountAdapter
        fabCreateOperation.setOnClickListener {
            startActivity(Intent(this, CreateOperationActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        operationsAdapter.data = OperationsRepository.all()
        accountAdapter.data = AccountRepository.all()
    }
}
