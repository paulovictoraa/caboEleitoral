package br.com.eleicao.caboeleitorais.model.eleitor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class EleitorDataSource(
    private val repository: EleitorRepository,
    private val scope: CoroutineScope,
    private val onLoading: MutableLiveData<Boolean>? = null,
    private val onError: MutableLiveData<String>? = null
) : PageKeyedDataSource<Int, Eleitor>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Eleitor>
    ) {
        scope.launch {
            onLoading?.value = true
            try {
                val eleitores = repository.fetch(0)
                repository.salvarTodos(eleitores)
                callback.onResult(eleitores, null, 1)
            } catch (e: Exception) {
                Log.i("teste", "teste")
                onError?.value = e.message
            }
            onLoading?.value = false
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Eleitor>) {
        scope.launch {
            try {
                val page = params.key
                val eleitores = repository.fetch(page)
                callback.onResult(eleitores, page.plus(1))
            } catch (e: Exception) {
                onError?.value = e.message
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Eleitor>) {
        TODO("Not yet implemented")
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

}