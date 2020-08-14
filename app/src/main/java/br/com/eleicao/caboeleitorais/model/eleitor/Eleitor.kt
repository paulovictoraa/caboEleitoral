package br.com.eleicao.caboeleitorais.model.eleitor

import com.google.gson.annotations.SerializedName

data class Eleitor(
    val codigo: Long = 0,
    val nome: String = "",
    val endereco: String = "",
    val setor: String = "",
    val telefone: String = "",
    @SerializedName("data_nascimento")
    val dataNascimento: String,
    @SerializedName("cabo_eleitoral")
    val caboEleitoral: String = "",
    @SerializedName("colegio_de_votacao")
    val colegioDeVotacao: String? = "",
    val observacao: String? = "",
    @SerializedName("data_insercao")
    val dataInsercao: String? = ""
)
