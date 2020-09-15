package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.extension.showToast
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.ui.viewmodel.CadastroUsuarioViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.util.ValidadorEditTextBuilder
import kotlinx.android.synthetic.main.cadastro_usuario.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CadastroUsuarioFragment : Fragment() {

    private val controlador by lazy {
        findNavController()
    }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val viewModel: CadastroUsuarioViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.cadastro_usuario,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais()
        configurarBotaoCadastro()
        observarMensagem()
        observarErro()
        observarLoading()
    }

    private fun observarMensagem() {
        viewModel.mensagem.observe(viewLifecycleOwner, Observer {
            showToast(it)
        })
    }

    private fun observarErro() {
        viewModel.onError.observe(viewLifecycleOwner, Observer {
            showToast("Erro inesperado tente novamente mais tarde")
        })
    }

    private fun observarLoading() {
        viewModel.onLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                progressBarUsuario.visibility = View.VISIBLE
                cadastro_usuario_botao_cadastrar.visibility = View.INVISIBLE
            } else {
                progressBarUsuario.visibility = View.GONE
                cadastro_usuario_botao_cadastrar.visibility = View.VISIBLE
            }
        })
    }

    private fun configurarBotaoCadastro() {
        cadastro_usuario_botao_cadastrar.setOnClickListener {
            val nome = editTextLogin.text.toString()
            val senha = editTextSenha.text.toString()
            val success = ValidadorEditTextBuilder()
                .isObrigatorio(editTextLogin)
                .isObrigatorio(editTextSenha)
                .isObrigatorio(editTextConfirmarSenha)
                .isMesmoValor(editTextSenha, editTextConfirmarSenha)
                .build()
            if (success) {
                val login = Login(nome, senha)
                viewModel.cadastrar(login)
            }
        }
    }

}