package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.DetalhesEleitorViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.detalhes_eleitor.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesEleitorFragment : BaseFragment() {

    private val argumentos by navArgs<DetalhesEleitorFragmentArgs>()
    private val eleitorId by lazy { argumentos.eleitorId }
    private val viewModel: DetalhesEleitorViewModel by viewModel { parametersOf(eleitorId) }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_eleitor,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(appBar = true)
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.eleitor.observe(viewLifecycleOwner, Observer {
            it?.let { eleitor ->
                detalhes_eleitor_nome.text = eleitor.nome
                detalhes_eleitor_endereco.text = eleitor.endereco
                detalhes_eleitor_setor.text = eleitor.setor
                detalhes_eleitor_telefone.text = eleitor.telefone
                detalhes_eleitor_data_nascimento.text = eleitor.dataNascimento
                if (UsuarioInstance.isAdmin()) {
                    detalhes_eleitor_cabo_eleitoral.visibility = View.VISIBLE
                    detalhes_eleitor_cabo_eleitoral_label.visibility = View.VISIBLE
                    detalhes_eleitor_cabo_eleitoral.text = eleitor.caboEleitoral
                }
                detalhes_eleitor_colegio.text = eleitor.colegioDeVotacao ?: ""
                detalhes_eleitor_observacao.text = eleitor.observacao ?: ""
            }
        })
    }

}