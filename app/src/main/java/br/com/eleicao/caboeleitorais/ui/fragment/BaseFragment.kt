package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.NavGraphDirections
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.extension.supportFragmentManager
import br.com.eleicao.caboeleitorais.ui.dialog.FiltroDialog
import br.com.eleicao.caboeleitorais.ui.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        verificaSeEstaLogado()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_principal, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_principal_deslogar) {
            loginViewModel.desloga()
            vaiParaLogin()
        }

        return super.onOptionsItemSelected(item)
    }

    fun vaiParaLogin() {
        val direcao = NavGraphDirections.acaoGlobalLogin()
        controlador.navigate(direcao)
    }

    private fun verificaSeEstaLogado() {
        if (loginViewModel.naoEstaLogado()) {
            vaiParaLogin()
        }
    }

}