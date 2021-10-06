package com.android.wallum.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
Created By Aditya Verma on 06/10/21
 **/

class Util {

    companion object {
        fun Fragment.hideKeyboard() {
            view?.let { activity?.hideKeyboard(it) }
        }

        fun Activity.hideKeyboard() {
            hideKeyboard(currentFocus ?: View(this))
        }

        fun Context.hideKeyboard(view: View) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}