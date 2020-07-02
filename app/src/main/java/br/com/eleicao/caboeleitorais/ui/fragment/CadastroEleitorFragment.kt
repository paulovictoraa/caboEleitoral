package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.extension.showSnackBar
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.ui.viewmodel.CadastroEleitorViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.util.Mask
import br.com.eleicao.caboeleitorais.util.ValidadorEditTextBuilder
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
        observaSetores()
        observaLoading()
        configurarMascara(Mask.MaskType.TEL, editar_telefone.editText)
        configurarMascara(Mask.MaskType.DATA_NASC, editar_data_nascimento.editText)
    }

    private fun configurarMascara(type: Mask.MaskType , editText: EditText?) {
        editText?.let{
            val mask = Mask.insert(type, it)
            it.addTextChangedListener(mask)
        }
    }

    private fun configuraBotaoSalvar() {
        editar_button_salvar.setOnClickListener {
            val isCamposValidos = validarCampos()
            if (isCamposValidos) {
                salvarEleitor()?.let(this::salvar) ?: showSnackBar(FALHA_AO_CADASTRAR_DADOS)
            }
        }
    }

    private fun salvar(eleitor: Eleitor) {
        viewModel.salva(eleitor)
            .observe(this, Observer {
                it?.dado?.let {
                    showSnackBar("Salvo com sucesso")
                    vaiParaListaEleitores()
                }
            })
    }

    private fun vaiParaListaEleitores() {
        val direcao =
            CadastroEleitorFragmentDirections.acaoCadastroEleitorFragmentParaListaEleitores()
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

    private fun validarCampos(): Boolean {
        return ValidadorEditTextBuilder()
            .isObrigatorio(editar_eleitor_nome.editText)
            .isObrigatorio(editar_endereco.editText)
            .isObrigatorio(editar_setor.editText)
            .isObrigatorio(editar_telefone.editText)
            .isObrigatorio(editar_data_nascimento.editText)
            .isObrigatorio(editar_colegio_votacao.editText)
            .build()
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

    private fun observaSetores() {
        viewModel.isLoading.value = true
        viewModel.buscarSetores().observe(viewLifecycleOwner, Observer { setores ->
            val nomeSetores = setores.map { it.nome }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this@CadastroEleitorFragment.context!!,
                android.R.layout.simple_dropdown_item_1line, nomeSetores
            )
            setor.setAdapter(adapter)
            viewModel.isLoading.value = false
        })
    }

    private fun observaLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                setor.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
                setor.visibility = View.VISIBLE
            }
        })
    }

}