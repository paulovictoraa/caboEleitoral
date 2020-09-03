package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import br.com.eleicao.caboeleitorais.repository.LoginRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: EleitorRepository,
    private val repositoryLogin: LoginRepository
) : ViewModel() {

    companion object {
        private const val PAGE_LIMIT = 50
    }

    private val _mensagem = MutableLiveData<String>()
    val mensagem: LiveData<String> = _mensagem
    val onFinish = MutableLiveData<Boolean>()
    val onError = MutableLiveData<Exception>()

    init {
        sincronizar()
    }

    private fun sincronizar() {
        val refreshToken = refreshToken()
        viewModelScope.launch(Dispatchers.IO) {
            _mensagem.postValue("Iniciando sincronização")
            refreshToken.await()
            sincronizarEleitoresNaoEnviados()
            repository.removerTodos()
            sincronizarEleitores()
            finalizarSincronizacao()
        }
    }

    private fun refreshToken(): Deferred<Unit> {
        return viewModelScope.async {
            for (i in 0..3) {
                UsuarioInstance.usuario?.login?.let {
                    try {
                        repositoryLogin.login(it)
                        return@async
                    } catch (e: Exception) {
                        if (i == 3) {
                            onError.value = Exception("Falha ao obter Token")
                        }
                    }
                }
            }
        }
    }


    private fun finalizarSincronizacao() {
        _mensagem.postValue("Sincronização finalizada!")
        onFinish.postValue(true)
    }

    private fun sincronizarEleitoresNaoEnviados() {
        val eleitoresNaoEnviados = repository.buscarTodosNaoEnviados()
        if (eleitoresNaoEnviados.isNotEmpty()) {
            _mensagem.postValue("Enviando dados!")
            repository.salvarTodosNuvem(eleitoresNaoEnviados)
        }
    }

    private suspend fun sincronizarEleitores() {
        _mensagem.postValue("Carregando dados!")
        var offset = 0
        var qtdEleitores = 0
        var continuar = true
        while (continuar) {
            val eleitores = repository.fetch(offset)
            repository.salvarTodos(eleitores)
            qtdEleitores += eleitores.size
            _mensagem.postValue("Carregando eleitores: $qtdEleitores")
            if (eleitores.size < PAGE_LIMIT) {
                continuar = false
            }
            offset++
        }
    }

}