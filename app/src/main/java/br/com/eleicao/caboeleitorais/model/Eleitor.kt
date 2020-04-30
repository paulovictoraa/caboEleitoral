package br.com.eleicao.caboeleitorais.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Eleitor(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val endereco: String,
    val setor: String,
    val telefone: String,
    val data_nascimento: String,
    val colegio: String,
    val observacao: String
)
