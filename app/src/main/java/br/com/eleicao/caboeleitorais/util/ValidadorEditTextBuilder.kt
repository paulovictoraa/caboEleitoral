package br.com.eleicao.caboeleitorais.util

import android.widget.EditText
import br.com.eleicao.caboeleitorais.extension.toCalendar

class ValidadorEditTextBuilder {

    private val MESAGEM_VALOR_OBRIGATORIO = "Campo obrigatório"
    private val MESAGEM_DATA_INVALIDA = "Data inválida"
    private val MESAGEM_VALOR_DIFERENTE_ANTERIOR = "Valor diferente do campo anterior"
    var succes: Boolean = true

    fun build(): Boolean = succes

    fun isObrigatorio(field: EditText?): ValidadorEditTextBuilder {
        if (field == null || field.text.isNullOrEmpty()) {
            field?.error = MESAGEM_VALOR_OBRIGATORIO
            succes = false
        }
        return this
    }

    fun isMesmoValor(field: EditText?, field2: EditText?): ValidadorEditTextBuilder {
        if (field == null || field2 == null) return this
        val valor = field.text.toString()
        val valor2 = field2.text.toString()
        if (valor != valor2) {
            field2.error = MESAGEM_VALOR_DIFERENTE_ANTERIOR
            succes = false
        }

        return this
    }

    fun isCampoData(field: EditText?): ValidadorEditTextBuilder {
        field?.let {
            val data = it.text.toString()
            val dataCalendar = data.toCalendar()
            if (dataCalendar == null) {
                it.error = MESAGEM_DATA_INVALIDA
                succes = false
            }
        }

        return this
    }

}