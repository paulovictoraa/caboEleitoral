package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val controlador by lazy { findNavController() }
    private val viewModel: LoginViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.login,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        configuraBotaoLogin()
        configuraBotaoCadastrarUsuario()
        observaSeEstaLogado()
        observaErro()
    }

    private fun observaErro() {
        viewModel.onError.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun observaSeEstaLogado() {
        viewModel.isLogado.observe(viewLifecycleOwner, Observer { isLogado ->
            if (isLogado) vaiParaListaProdutos()
        })
    }

    private fun configuraBotaoCadastrarUsuario() {
        login_botao_cadastrar_usuario.setOnClickListener {
            vaiParaCadastrarUsuario()
        }
    }

    private fun vaiParaCadastrarUsuario() {
        val direcao = LoginFragmentDirections.acaoLoginParaCadastroUsuario()
        controlador.navigate(direcao)
    }

    private fun configuraBotaoLogin() {
        login_botao_logar.setOnClickListener {
            val id = login_usuario_text_id.text.toString()
            val hash = login_usuario_text_senha.text.toString()
            val login = Login(id, hash)
            viewModel.login(login)
        }
    }

    private fun vaiParaListaProdutos() {
        val direcao = LoginFragmentDirections.acaoLoginParaListaEleitores()
        controlador.navigate(direcao)
    }

}