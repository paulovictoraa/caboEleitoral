package br.com.eleicao.caboeleitorais.model

data class Filtro(
    val setor: String = "",
    var cabo: String = "",
    var nome: String = ""
) {
    fun isPossuiFiltro() = setor.isNotEmpty() || cabo.isNotEmpty() || nome.isNotEmpty()

    fun isPossuiTodosFiltros() = setor.isNotEmpty() && cabo.isNotEmpty() && nome.isNotEmpty()

    fun isPossuiFiltrosNomeECabo() = nome.isNotEmpty() && cabo.isNotEmpty()

    fun isPossuiFiltrosNomeESetor() = nome.isNotEmpty() && setor.isNotEmpty()

    fun isPossuiFiltrosSetorECabo() = setor.isNotEmpty() && cabo.isNotEmpty()
}