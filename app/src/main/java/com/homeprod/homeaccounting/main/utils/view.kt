/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 27.11.18 17:36
 */

package com.homeprod.homeaccounting.main.utils

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun AppCompatActivity.showToast(throwable: Throwable) = Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
