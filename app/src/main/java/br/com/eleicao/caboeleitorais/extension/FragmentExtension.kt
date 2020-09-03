package br.com.eleicao.caboeleitorais.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.eleicao.caboeleitorais.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.supportFragmentManager(execute: FragmentManager.() -> Unit) {
    val supportFragmentManager = activity?.supportFragmentManager
        ?: throw IllegalArgumentException("Activity null")
    execute(supportFragmentManager)
}

fun Fragment.showSnackBar(message: String?) {
    if (message.isNullOrEmpty()) return
    val view = this.activity?.findViewById<View>(R.id.snackbar)
    view?.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
    }
}


fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboardFrom(it) }
}

fun Context.hideKeyboardFrom(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.isOffiline() = !isOnline()

fun Fragment.isOnline() = view?.let { activity?.isOnline() } ?: false

fun Context.isOnline(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    if (cm != null) {
        cm.activeNetworkInfo?.let {
            return it.isConnectedOrConnecting
        }
    }

    return false
}