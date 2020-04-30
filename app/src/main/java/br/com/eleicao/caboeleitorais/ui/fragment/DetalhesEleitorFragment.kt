package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.DetalhesEleitorViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.detalhes_eleitor.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetalhesEleitorFragment : BaseFragment() {

    private val argumentos by navArgs<DetalhesEleitorFragmentArgs>()
    private val produtoId by lazy {
        argumentos.produtoId
    }
    private val viewModel: DetalhesEleitorViewModel by viewModel { parametersOf(produtoId) }
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val controlador by lazy { findNavController() }

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
        configuraBotaoComprar()
    }

    private fun configuraBotaoComprar() {
        detalhes_eleitor_editar.setOnClickListener {
            viewModel.produtoEncontrado.value?.let {
                vaiParaPagamento()
            }
        }
    }

    private fun vaiParaPagamento() {
        val direcao = DetalhesEleitorFragmentDirections
            .acaoDetalhesEleitorParaPagamento(produtoId)
        controlador.navigate(direcao)
    }

    private fun buscaProduto() {
        viewModel.produtoEncontrado.observe(this, Observer {
            it?.let { eleitor ->
                detalhes_eleitor_nome.text = eleitor.nome
                detalhes_eleitor_endereco.text = eleitor.endereco
                detalhes_eleitor_setor.text = eleitor.setor
                detalhes_eleitor_telefone.text = eleitor.telefone
                detalhes_eleitor_data_nascimento.text = eleitor.data_nascimento
                detalhes_eleitor_colegio.text = eleitor.colegio
                detalhes_eleitor_observacao.text = eleitor.observacao
            }
        })
    }

}