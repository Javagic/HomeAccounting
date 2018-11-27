/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 27.11.18 19:05
 */

package com.homeprod.homeaccounting.main.exceptions

import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.App

class IncompleteDataException : Exception(App.instance.getString(R.string.error_incomplete_data))