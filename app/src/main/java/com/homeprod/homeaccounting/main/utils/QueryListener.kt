/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 27.11.18 21:01
 */

package com.homeprod.homeaccounting.main.utils

import android.widget.SearchView
import com.homeprod.homeaccounting.main.repository.OperationsRepository


class QueryListener(val adapter: OperationsAdapter) : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(p0: String?): Boolean = true

    override fun onQueryTextChange(query: String?): Boolean {
        query?.let {
            adapter.data =OperationsRepository.all().filter {
                it.description.contains(query, true)
                        || it.from.name.contains(query, true)
                        || it.from.name.contains(query, true)
                        || it.type.name.contains(query, true)
            }.toMutableList()
        }
        return true
    }
}