package br.com.eleicao.caboeleitorais.repository

import androidx.lifecycle.LiveData
import br.com.eleicao.caboeleitorais.database.dao.CadastradosDAO
import br.com.eleicao.caboeleitorais.model.Eleitor

class EleitorRepository(private val dao: CadastradosDAO) {

    fun buscaTodos(): LiveData<List<Eleitor>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Eleitor> = dao.buscaPorId(id)

}