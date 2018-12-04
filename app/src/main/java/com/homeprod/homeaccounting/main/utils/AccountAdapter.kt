
package com.homeprod.homeaccounting.main.utils

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.data.Account
import java.util.*

class AccountAdapter : RecyclerView.Adapter<AccountAdapter.AccountHolder>() {
    var data: MutableList<Account> = ArrayList()
        set(value) {
            this.data.clear()
            this.data.addAll(value)
            this.notifyDataSetChanged()
        }

    fun remove(account: Account) {
        data.remove(account)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountHolder =
        AccountHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_account,
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: AccountHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    class AccountHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name by lazy { itemView.findViewById<TextView>(R.id.tvName) }
        val balance by lazy { itemView.findViewById<TextView>(R.id.tvBalance) }
        fun bind(account: Account) {
            name.text = account.name
            balance.text = account.value.toString()
        }

    }

}