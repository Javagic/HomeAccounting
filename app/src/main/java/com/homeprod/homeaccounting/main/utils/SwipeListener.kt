/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 27.11.18 17:14
 */

package com.homeprod.homeaccounting.main.utils

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class SwipeListener< T : RecyclerView.ViewHolder>(val action: (T) -> Unit) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    @Suppress("UNCHECKED_CAST")
    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {
        (holder as? T)?.let { action(it) }
    }

}