package br.com.eleicao.caboeleitorais.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.eleicao.caboeleitorais.database.dao.PagamentoDAO
import br.com.eleicao.caboeleitorais.model.Pagamento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PagamentoRepository(private val dao: PagamentoDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun salva(pagamento: Pagamento): LiveData<Resource<Long>> {
        return MutableLiveData<Resource<Long>>().also { liveDate ->
            scope.launch {
                val idPagamento = dao.salva(pagamento)
                liveDate.postValue(Resource(idPagamento))
            }
        }
    }

    fun todos(): LiveData<List<Pagamento>> = dao.todos()

}
