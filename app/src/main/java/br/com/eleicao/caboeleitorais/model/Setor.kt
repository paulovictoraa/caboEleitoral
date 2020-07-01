package br.com.eleicao.caboeleitorais.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Setor(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String
)