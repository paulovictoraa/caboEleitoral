package br.com.eleicao.caboeleitorais.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.toLiveData
import br.com.eleicao.caboeleitorais.database.dao.EleitorDAO
import br.com.eleicao.caboeleitorais.extension.unaccent
import br.com.eleicao.caboeleitorais.model.Filtro
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import br.com.eleicao.caboeleitorais.model.eleitor.toModel
import br.com.eleicao.caboeleitorais.model.eleitor.toPersistence
import br.com.eleicao.caboeleitorais.service.CaboEleitoralService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EleitorRepository(
    private val dao: EleitorDAO,
    private val service: CaboEleitoralService
) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    companion object {
        private const val PAGE_SIZE = 50
    }

    fun buscaTodos(): LiveData<PagedList<Eleitor>> =
        dao.buscaTodos().map { it.toModel() }.toLiveData(pageSize = PAGE_SIZE)

    fun buscaTodos(filtro: Filtro): LiveData<PagedList<Eleitor>> {
        val nome = "%${filtro.nome.unaccent()}%"
        val setor = filtro.setor
        val cabo = filtro.cabo
        return when {
            filtro.isPossuiTodosFiltros() -> {
                dao.buscaPorNomeESetorECabo(nome, setor, cabo)
            }
            filtro.isPossuiFiltrosNomeECabo() -> {
                dao.buscaPorNomeECabo(nome, cabo)
            }
            filtro.isPossuiFiltrosNomeESetor() -> {
                dao.buscaPorNomeESetor(nome, setor)
            }
            filtro.isPossuiFiltrosSetorECabo() -> {
                dao.buscaPorSetorECabo(setor, cabo)
            }
            filtro.cabo.isNotEmpty() -> {
                dao.buscaPorCabo(cabo)
            }
            filtro.setor.isNotEmpty() -> {
                dao.buscaPorSetor(setor)
            }
            else -> {
                dao.buscaPorNome(nome)
            }
        }.map { it.toModel() }.toLiveData(pageSize = PAGE_SIZE)
    }

    fun buscarTodosNaoEnviados() = dao.buscaTodosNaoEnviados().toModel()

    fun salvarTodos(eleitores: List<Eleitor>) = dao.salvaTodos(eleitores.toPersistence())

    suspend fun fetch(offSet: Int): List<Eleitor> {
        return try {
            if (UsuarioInstance.isAdmin()) {
                service.listar(offSet)
            } else {
                service.listarPorId(UsuarioInstance.getCodigo())
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun buscaPorId(id: Long): LiveData<Eleitor> =
        Transformations.map(dao.buscaPorId(id)) { it.toModel() }

    suspend fun salvar(eleitor: Eleitor) = service.salvar(UsuarioInstance.getCodigo(), eleitor)

    fun salvarLocal(eleitorNuvem: Eleitor) = dao.salva(eleitorNuvem.toPersistence())

    fun salvarTodosNuvem(eleitoresNaoEnviados: List<Eleitor>) {
        scope.launch {
            for (eleitor: Eleitor in eleitoresNaoEnviados) {
                try {
                    service.salvar(UsuarioInstance.getCodigo(), eleitor)
                } catch (e: java.lang.Exception) {
                    Log.e("Eleitor nao enviado: ", eleitor.nome)

                }
            }
        }
    }

    fun removerTodos() = dao.deleteAll()

}