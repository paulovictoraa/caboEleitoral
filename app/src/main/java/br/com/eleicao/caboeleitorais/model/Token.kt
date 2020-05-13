package br.com.eleicao.caboeleitorais.model

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("acess_token")
    val acessToken: String = ""
)