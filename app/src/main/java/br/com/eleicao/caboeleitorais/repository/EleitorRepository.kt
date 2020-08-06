package br.com.eleicao.caboeleitorais.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eleicao.caboeleitorais.database.dao.EleitorDAO
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
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

    suspend fun buscaTodos(): List<Eleitor> {
        try {
            val eleitores = if (UsuarioInstance.isAdmin()) {
                service.listar()
            } else {
                service.listarPorId(UsuarioInstance.getCodigo())
            }
            salvaTodos(eleitores)
        } catch (e: Exception) {
            Log.e("", e.message ?: "")
        } finally {
            return dao.buscaTodos()
        }

    }

    fun salvaTodos(eleitores: List<Eleitor>) = dao.salvaTodos(eleitores)

    suspend fun fetch(offSet: Int): List<Eleitor> {
        return try {
            if (UsuarioInstance.isAdmin()) {
                service.listar(offSet)
            } else {
                service.listarPorId(UsuarioInstance.getCodigo())
            }
        } catch (e: Exception) {
            Log.e("", e.message ?: "")
            emptyList()
        }
    }

    fun buscaPorId(id: Long): LiveData<Eleitor> = dao.buscaPorId(id)

    fun salva(eleitor: Eleitor): LiveData<Resource<Long>> {
        return MutableLiveData<Resource<Long>>().also { liveData ->
            scope.launch {
                try {
                    val eleitorNuvem = service.salvar(UsuarioInstance.getCodigo(), eleitor)
                    val id = dao.salva(eleitorNuvem)
                    liveData.postValue(Resource(id))
                } catch (e: Exception) {
                    val id = dao.salva(eleitor)
                    liveData.postValue(Resource(id))
                }
            }
        }
    }

}