
package com.homeprod.homeaccounting.main.exceptions

import com.homeprod.homeaccounting.R
import com.homeprod.homeaccounting.main.App

class InsufficientFundsException : Exception(App.instance.getString(R.string.error_insufficient_funds))