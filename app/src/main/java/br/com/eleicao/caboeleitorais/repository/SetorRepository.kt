package br.com.eleicao.caboeleitorais.repository

import br.com.eleicao.caboeleitorais.database.dao.SetorDAO

class SetorRepository(private val dao: SetorDAO) {

    fun buscarTodos() = dao.buscarTodos()

    fun buscarPorNome(nome: String) = dao.buscarPorNome(nome)

}