package br.com.eleicao.caboeleitorais.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.extension.showSnackBar
import br.com.eleicao.caboeleitorais.model.Filtro
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.ui.viewmodel.FiltroDialogViewModel
import kotlinx.android.synthetic.main.dialog_filter.*
import org.koin.android.viewmodel.ext.android.viewModel


class FiltroDialog : DialogFragment() {

    private val viewModel: FiltroDialogViewModel by viewModel()
    private lateinit var filtro: Filtro
    private lateinit var onFinished: (filtro: Filtro) -> Unit

    companion object {
        fun getInstance(
            filtro: Filtro,
            onFinished: (filtro: Filtro) -> Unit
        ): FiltroDialog {
            val dialog = FiltroDialog()
            dialog.filtro = filtro
            dialog.onFinished = onFinished
            return dialog
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoCompleteTextViewSetor.setText(filtro.setor, false)
        configurarEditTextCabo()
        observaLoading()
        observaSetores()
        configuraBotaoLimpar()
        configuraBotaSalvar()
    }

    private fun configurarEditTextCabo() {
        if (UsuarioInstance.isAdmin()) {
            editTextViewCabo.visibility = VISIBLE
        } else {
            editTextViewCabo.visibility = GONE
        }
    }

    private fun configuraBotaSalvar() {
        buttonApply.setOnClickListener {
            val setor = autoCompleteTextViewSetor.text.toString()
            val cabo = editTextViewCabo.text.toString()
            when {
                setor.isEmpty() -> {
                    showSnackBar("Nenhum setor selecionado")
                }
                isSetorInvalido(setor) -> {
                    showSnackBar("Setor selecionado inválido")
                }
                else -> {
                    onFinished(Filtro(setor, cabo))
                    this.dismiss()
                }
            }
        }
    }

    private fun isSetorInvalido(nomeSetor: String): Boolean {
        return viewModel.buscarPorNome(nomeSetor) == null
    }

    private fun configuraBotaoLimpar() {
        buttonClean.setOnClickListener {
            autoCompleteTextViewSetor.setText("", false)
            onFinished(Filtro(""))
            this.dismiss()
        }
    }

    private fun observaLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                autoCompleteTextViewSetor.visibility = View.INVISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
                autoCompleteTextViewSetor.visibility = View.VISIBLE
            }
        })
    }

    private fun observaSetores() {
        viewModel.isLoading.value = true
        viewModel.buscarTodos().observe(viewLifecycleOwner, Observer { setores ->
            val nomeSetores = setores.map { it.nome }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this@FiltroDialog.context!!,
                android.R.layout.simple_dropdown_item_1line, nomeSetores
            )
            autoCompleteTextViewSetor.setAdapter(adapter)
            viewModel.isLoading.value = false
        })
    }
}