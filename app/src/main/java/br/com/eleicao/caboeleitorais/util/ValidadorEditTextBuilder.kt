package br.com.eleicao.caboeleitorais.util

import android.widget.EditText
import br.com.eleicao.caboeleitorais.extension.toCalendar

class ValidadorEditTextBuilder {

    private val MESAGEM_VALOR_OBRIGATORIO = "Campo obrigatório"
    private val MESAGEM_DATA_INVALIDA = "Data inválida"
    var succes: Boolean = true

    fun build(): Boolean = succes

    fun isObrigatorio(field: EditText?): ValidadorEditTextBuilder {
        if (field == null || field.text.isNullOrEmpty()) {
            field?.error = MESAGEM_VALOR_OBRIGATORIO
            succes = false
        }
        return this
    }

    fun isCampoData(field: EditText?): ValidadorEditTextBuilder {
        field?.let {
            val data = it.text.toString()
            val dataCalendar = data.toCalendar()
            if(dataCalendar == null) {
                it.error = MESAGEM_DATA_INVALIDA
                succes = false
            }
        }

        return this
    }

}