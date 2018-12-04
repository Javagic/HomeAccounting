
package com.homeprod.homeaccounting.main.utils

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun AppCompatActivity.showToast(throwable: Throwable) = showToast(throwable.message!!)
fun AppCompatActivity.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
