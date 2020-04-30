package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.model.Pagamento
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.PagamentoViewModel
import kotlinx.android.synthetic.main.pagamento.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


private const val FALHA_AO_EDITAR_DADOS = "Falha ao editar dados"
private const val EDICAO_REALIZADA = "Edição realizada"

class PagamentoFragment : BaseFragment() {

    private val argumentos by navArgs<PagamentoFragmentArgs>()
    private val eleitorId by lazy {
        argumentos.produtoId
    }
    private val viewModel: PagamentoViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private lateinit var eleitorEscolhido: Eleitor
    private val controlador by lazy { findNavController() }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
                R.layout.pagamento,
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(appBar = true)
        configuraBotaoSalvarEdicao()
        buscaEleitor()
    }

    private fun buscaEleitor() {

        viewModel.buscaEleitorPorId(eleitorId).observe(this, Observer {
            it?.let {
                eleitorEscolhido = it
                nome.text = Editable.Factory.getInstance().newEditable(it.nome)
                endereco.text = Editable.Factory.getInstance().newEditable(it.endereco)
                setor.text = Editable.Factory.getInstance().newEditable(it.setor)
                telefone.text = Editable.Factory.getInstance().newEditable(it.telefone)
                nascimento.text = Editable.Factory.getInstance().newEditable(it.data_nascimento)
                colegio.text = Editable.Factory.getInstance().newEditable(it.colegio)
                observacao.text = Editable.Factory.getInstance().newEditable(it.observacao)

            }
        })
    }

    private fun configuraBotaoSalvarEdicao() {
//        editar_button_salvar.setOnClickListener {
//            salvaEdicao()?.let(this::salva) ?: Toast.makeText(
//                    context,
//                    FALHA_AO_EDITAR_DADOS,
//                    Toast.LENGTH_LONG
//            ).show()
//        }
    }

//    private fun salva(eleitor: Eleitor) {
//        if (::eleitorEscolhido.isInitialized) {
//            viewModel.salva(eleitor)
//                    .observe(this, Observer {
//                        it?.dado?.let {
//                            Toast.makeText(
//                                    context,
//                                    EDICAO_REALIZADA,
//                                    Toast.LENGTH_SHORT
//                            ).show()
//                            vaiParaListaEleitores()
//                        }
//                    })
//        }
//    }

    private fun vaiParaListaEleitores() {
        val direcao = PagamentoFragmentDirections.acaoPagamentoParaListaEleitor()
        controlador.navigate(direcao)
    }

    private fun salvaEdicao(): Eleitor? {

        val nomeEleitor = editar_eleitor_nome.editText?.text.toString()
        val enderecoEleitor = editar_endereco.editText?.text.toString()
        val setorEleitor = editar_setor.editText?.text.toString()
        val telefoneEleitor = editar_telefone.editText?.text.toString()
        val dataNascimentoEleitor = editar_data_nascimento.editText?.text.toString()
        val colegioVotacaoEleitor = editar_colegio_votacao.editText?.text.toString()
        val observacaoEleitor = editar_observacao.editText?.text.toString()

        return salvarEdicao(nomeEleitor, enderecoEleitor, setorEleitor, telefoneEleitor, dataNascimentoEleitor, colegioVotacaoEleitor, observacaoEleitor)
    }

    private fun salvarEdicao(

            nomeEleitor: String,
            enderecoEleitor: String,
            setorEleitor: String,
            telefoneEleitor: String,
            dataNascimentoEleitor: String,
            colegioVotacaoEleitor: String,
            observacaoEleitor: String

    ): Eleitor? = try {
        Eleitor(
                id = eleitorEscolhido.id,
                nome = nomeEleitor,
                endereco = enderecoEleitor,
                setor = setorEleitor,
                telefone = telefoneEleitor,
                data_nascimento = dataNascimentoEleitor,
                colegio = colegioVotacaoEleitor,
                observacao = observacaoEleitor
        )
    } catch (e: NumberFormatException) {
        null
    }

}