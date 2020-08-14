package br.com.eleicao.caboeleitorais.model.eleitor

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ELEITOR")
data class EleitorPersistence(
    @PrimaryKey(autoGenerate = true)
    val codigo: Long = 0,
    val nome: String = "",
    val endereco: String = "",
    val setor: String = "",
    val telefone: String = "",
    @ColumnInfo(name = "data_nascimento")
    val dataNascimento: String,
    @ColumnInfo(name = "cabo_eleitoral")
    val caboEleitoral: String = "",
    @ColumnInfo(name = "colegio_votacao")
    val colegioDeVotacao: String? = "",
    val observacao: String? = "",
    @ColumnInfo(name = "data_insercao")
    val dataInsercao: String? = "",
    @ColumnInfo(name = "nome_consulta")
    val nomeConsulta: String = ""
)