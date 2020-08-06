package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.splash.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

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
        estadoAppViewModel.temComponentes =
            ComponentesVisuais(appBar = false, bottomNavigation = false)
        observarMensagem()
        observarOnFinish()
    }

    private fun observarOnFinish() {
        splashViewModel.onFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                val direcao = SplashFragmentDirections.actionSplashFragmentToListaEleitores()
                controlador.navigate(direcao)
            }
        })
    }

    private fun observarMensagem() {
        splashViewModel.mensagem.observe(viewLifecycleOwner, Observer {
            it?.let { mensagem ->
                textViewCarregando.text = mensagem
            }
        })
    }
}