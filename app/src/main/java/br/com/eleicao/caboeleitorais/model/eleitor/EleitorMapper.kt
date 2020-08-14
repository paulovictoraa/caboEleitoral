package br.com.eleicao.caboeleitorais.model.eleitor

import br.com.eleicao.caboeleitorais.extension.unaccent
import java.util.*

fun EleitorPersistence.toModel(): Eleitor {
    return Eleitor(
        this.codigo,
        this.nome,
        this.endereco,
        this.setor,
        this.telefone,
        this.dataNascimento,
        this.caboEleitoral,
        this.colegioDeVotacao ?: "",
        this.observacao ?: "",
        if (this.dataInsercao.isNullOrEmpty()) {
            null
        } else {
            this.dataInsercao
        }
    )
}

fun List<EleitorPersistence>.toModel(): List<Eleitor> {
    return this.map { it.toModel() }
}

fun Eleitor.toPersistence(): EleitorPersistence {
    return EleitorPersistence(
        this.codigo,
        this.nome,
        this.endereco,
        this.setor,
        this.telefone,
        this.dataNascimento,
        this.caboEleitoral,
        this.colegioDeVotacao ?: "",
        this.observacao ?: "",
        this.dataInsercao,
        this.nome.toUpperCase(Locale.getDefault()).unaccent()
    )
}

fun List<Eleitor>.toPersistence(): List<EleitorPersistence> {
    return this.map { it.toPersistence() }
}