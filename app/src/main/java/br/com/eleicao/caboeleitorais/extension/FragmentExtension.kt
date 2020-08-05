package br.com.eleicao.caboeleitorais.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import br.com.eleicao.caboeleitorais.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.supportFragmentManager(execute: FragmentManager.() -> Unit) {
    val supportFragmentManager = activity?.supportFragmentManager
        ?: throw IllegalArgumentException("Activity null")
    execute(supportFragmentManager)
}

fun Fragment.showSnackBar(message: String) {
    val view = this.activity?.findViewById<View>(R.id.snackbar)
    view?.let{
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
    }
}
