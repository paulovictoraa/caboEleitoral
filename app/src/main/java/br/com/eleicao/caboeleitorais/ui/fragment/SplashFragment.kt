package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.extension.isOffiline
import br.com.eleicao.caboeleitorais.extension.showSnackBar
import br.com.eleicao.caboeleitorais.extension.showToast
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.splash.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment() {

    private val controlador by lazy {
        findNavController()
    }
    private val splashViewModel: SplashViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.splash,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (this.isOffiline()) {
            vaiParaListaEleitores()
            showSnackBar("Falha ao conectar com a internet, tente novamente mais tarde")
            return
        }
        estadoAppViewModel.temComponentes =
            ComponentesVisuais(appBar = false, bottomNavigation = false)
        observarMensagem()
        observarOnFinish()
        observaErro()
    }

    private fun observaErro() {
        splashViewModel.onError.observe(viewLifecycleOwner, Observer {
            showToast("Erro na sincronização")
            vaiParaLogin()
        })
    }

    private fun observarOnFinish() {
        splashViewModel.onFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                vaiParaListaEleitores()
            }
        })
    }

    private fun vaiParaListaEleitores() {
        val direcao = SplashFragmentDirections.actionSplashFragmentToListaEleitores()
        controlador.navigate(direcao)
    }

    private fun observarMensagem() {
        splashViewModel.mensagem.observe(viewLifecycleOwner, Observer {
            it?.let { mensagem ->
                textViewCarregando.text = mensagem
            }
        })
    }
}