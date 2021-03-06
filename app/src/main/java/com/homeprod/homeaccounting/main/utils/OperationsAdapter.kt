
package com.homeprod.homeaccounting.main.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.data.Operation
import java.text.SimpleDateFormat
import java.util.*

class OperationsAdapter : RecyclerView.Adapter<OperationsAdapter.OperationsHolder>() {
    var data: MutableList<Operation> = ArrayList()
        set(value) {
            this.data.clear()
            this.data.addAll(value)
            this.notifyDataSetChanged()
        }

    fun remove(operation: Operation) {
        data.remove(operation)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationsHolder =
        OperationsHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_operation,
                parent,
                false
            )
        )

    override fun onBindViewHolder(viewHolder: OperationsHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size


    class OperationsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name by lazy { itemView.findViewById<TextView>(R.id.tvName) }
        val time by lazy { itemView.findViewById<TextView>(R.id.tvTime) }
        val value by lazy { itemView.findViewById<TextView>(R.id.tvValue) }
        val fromTo by lazy { itemView.findViewById<TextView>(R.id.tvFromTo) }
        fun bind(operation: Operation) {
            time.text = itemView.context.dateTimeFormat.format(Date(operation.time))
            value.text = operation.value.toString()
            fromTo.text = when (operation.type) {
                Operation.OperationType.TRANSACTION -> itemView.context.getString(
                    R.string.name_from_to,
                    operation.from,
                    operation.to
                )
                Operation.OperationType.INCOME -> itemView.context.getString(
                    R.string.name_to,
                    operation.to
                )
                Operation.OperationType.OUTGO -> itemView.context.getString(
                    R.string.name_from,
                    operation.from
                )
            }
        }

        val Context.dateTimeFormat: SimpleDateFormat
            get() =
                SimpleDateFormat(getString(R.string.date_time_format), Locale("RU"))
    }

}