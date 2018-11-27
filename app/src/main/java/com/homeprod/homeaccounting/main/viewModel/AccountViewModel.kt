/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 28.11.18 19:02
 */

package com.homeprod.homeaccounting.main.viewModel

object AccountViewModel {
    var name: String = ""
    var description: String = ""
    fun clear() {
        name = ""
        description = ""
    }
}