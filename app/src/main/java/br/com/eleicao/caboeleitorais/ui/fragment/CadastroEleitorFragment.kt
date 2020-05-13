package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.ui.viewmodel.CadastroEleitorViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.formulario_eleitor.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


private const val FALHA_AO_CADASTRAR_DADOS = "Falha ao editar dados"
private const val SALVO_COM_SUCESSO = "Salvo com sucesso"

class CadastroEleitorFragment : BaseFragment() {

    private val viewModel: CadastroEleitorViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.formulario_eleitor,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(appBar = false)
        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        editar_button_salvar.setOnClickListener {
            salvarEleitor()?.let(this::salvar) ?: Toast.makeText(
                context,
                FALHA_AO_CADASTRAR_DADOS,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun salvar(eleitor: Eleitor) {
        viewModel.salva(eleitor)
            .observe(this, Observer {
                it?.dado?.let {
                    Toast.makeText(
                        context,
                        SALVO_COM_SUCESSO,
                        Toast.LENGTH_SHORT
                    ).show()
                    vaiParaListaEleitores()
                }
            })
    }

    private fun vaiParaListaEleitores() {
        val direcao = CadastroEleitorFragmentDirections.acaoCadastroEleitorFragmentParaListaEleitores()
        controlador.navigate(direcao)
    }

    private fun salvarEleitor(): Eleitor? {
        val nomeEleitor = editar_eleitor_nome.editText?.text.toString()
        val enderecoEleitor = editar_endereco.editText?.text.toString()
        val setorEleitor = editar_setor.editText?.text.toString()
        val telefoneEleitor = editar_telefone.editText?.text.toString()
        val dataNascimentoEleitor = editar_data_nascimento.editText?.text.toString()
        val colegioVotacaoEleitor = editar_colegio_votacao.editText?.text.toString()
        val observacaoEleitor = editar_observacao.editText?.text.toString()

        return salvarEleitor(
            nomeEleitor,
            enderecoEleitor,
            setorEleitor,
            telefoneEleitor,
            dataNascimentoEleitor,
            colegioVotacaoEleitor,
            observacaoEleitor
        )
    }

    private fun salvarEleitor(
        nomeEleitor: String,
        enderecoEleitor: String,
        setorEleitor: String,
        telefoneEleitor: String,
        dataNascimentoEleitor: String,
        colegioVotacaoEleitor: String,
        observacaoEleitor: String
    ): Eleitor? = try {
        Eleitor(
            nome = nomeEleitor,
            endereco = enderecoEleitor,
            setor = setorEleitor,
            telefone = telefoneEleitor,
            dataNascimento = dataNascimentoEleitor,
            colegioDeVotacao = colegioVotacaoEleitor,
            observacao = observacaoEleitor
        )
    } catch (e: NumberFormatException) {
        null
    }

}