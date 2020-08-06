package br.com.eleicao.caboeleitorais.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor

@Entity(
    foreignKeys = [ForeignKey(
        entity = Eleitor::class,
        parentColumns = ["codigo"],
        childColumns = ["produtoId"]
    )],
    indices = [Index("produtoId")]
)
class Pagamento(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val numeroCartao: Int,
        val dataValidade: String,
        val cvc: Int,
        val preco: String,
        val produtoId: Long
)