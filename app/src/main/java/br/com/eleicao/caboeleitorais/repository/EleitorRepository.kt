package br.com.eleicao.caboeleitorais.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eleicao.caboeleitorais.database.dao.CadastradosDAO
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.service.CaboEleitoralService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EleitorRepository(
    private val dao: CadastradosDAO,
    private val service: CaboEleitoralService
) {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    suspend fun buscaTodos(): List<Eleitor> {
        val eleitores = service.listar()
        dao.salvaTodos(eleitores)
        return dao.buscaTodos()
    }

    fun buscaPorId(id: Long): LiveData<Eleitor> = dao.buscaPorId(id)

    fun salva(eleitor: Eleitor): LiveData<Resource<Long>> {
        return MutableLiveData<Resource<Long>>().also { liveDate ->
            scope.launch {
                try {
                    val eleitorNuvem = service.salvar(UsuarioInstance.getCodigo(), eleitor)
                    val id = dao.salva(eleitorNuvem)
                    liveDate.postValue(Resource(id))
                } catch (e: Exception) {
                    Log.e("", "")
                }
            }
        }
    }

}