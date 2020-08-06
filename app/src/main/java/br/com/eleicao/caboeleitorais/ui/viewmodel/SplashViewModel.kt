package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: EleitorRepository) : ViewModel() {

    companion object {
        private const val PAGE_LIMIT = 50
    }

    private lateinit var eleitores: LiveData<PagedList<Eleitor>>
    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem
    val onFinish = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _mensagem.postValue("Carregando...")
            sincronizar()
        }
    }

    private fun finalizarSincronizacao() {
        _mensagem.postValue("Sincronização finalizada!")
        onFinish.postValue(true)
    }

    private suspend fun sincronizar() {
        var offset = 0
        var qtdEleitores = 0
        var continuar = true
        while (continuar) {
            val eleitores = repository.fetch(offset)
            repository.salvaTodos(eleitores)
            qtdEleitores += eleitores.size
            _mensagem.postValue("Carregando eleitores: $qtdEleitores")
            if (eleitores.size < PAGE_LIMIT) {
                continuar = false
            }
            offset++
        }
        finalizarSincronizacao()
    }

}