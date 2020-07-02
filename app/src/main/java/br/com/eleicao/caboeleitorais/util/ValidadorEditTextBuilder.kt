package br.com.eleicao.caboeleitorais.util

import android.widget.EditText

class ValidadorEditTextBuilder {

    private val MESAGEM_VALOR_OBRIGATORIO = "Campo obrigatório"
    var succes: Boolean = true

    fun isObrigatorio(field: EditText?): ValidadorEditTextBuilder {
        if (field == null || field.text.isNullOrEmpty()) {
            field?.error = MESAGEM_VALOR_OBRIGATORIO
            succes = false
        }
        return this
    }

    fun build(): Boolean = succes
}