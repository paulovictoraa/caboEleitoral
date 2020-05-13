package br.com.eleicao.caboeleitorais.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Eleitor(
    @PrimaryKey(autoGenerate = true)
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
