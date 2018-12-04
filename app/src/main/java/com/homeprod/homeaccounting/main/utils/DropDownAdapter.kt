
package com.homeprod.homeaccounting.main.utils

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DropDownAdapter<T>(
    context: Context,
    items: MutableList<T> = mutableListOf(),
    private val itemId: (T.() -> Long)? = null,
    val itemToString: T.() -> String = { this.toString() },
    @IdRes private val textViewId: Int = 0,
    @LayoutRes private val layoutRes: Int = android.R.layout.simple_spinner_dropdown_item
) : ArrayAdapter<T>(context, layoutRes, items) {

    fun setItems(items: List<T>) {
        clear()
        addAll(items)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
        createViewFromResource(position, convertView, parent)

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
        createViewFromResource(position, convertView, parent)

    override fun getItemId(position: Int): Long = itemId?.let { getItem(position).it() }
        ?: super.getItemId(position)

    fun getItemPosition(item: T?): Int? = getPosition(item).takeIf { it != -1 }

    fun getItemPosition(id: Long): Int? = (0 until count).firstOrNull { getItemId(it) == id }

    private fun createViewFromResource(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(layoutRes, parent, false).apply {
                tag = ViewHolder(this, textViewId)
            }
        val holder = view.tag as ViewHolder
        val item = getItem(position)
        val itemToString1 = item.itemToString()
        holder.textView.text = itemToString1
        return view
    }


    private class ViewHolder(view: View, textViewId: Int) {
        val textView: TextView = if (textViewId == 0) {
            view as TextView
        } else {
            view.findViewById(textViewId)
        }
    }
}