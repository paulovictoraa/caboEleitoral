package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.repository.UsuarioRepository
import kotlinx.coroutines.launch

class CadastroUsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {

    val mensagem: MutableLiveData<String> = MutableLiveData()
    val onLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError: MutableLiveData<Exception> = MutableLiveData()

    fun cadastrar(login: Login) {
        onLoading.value = true
        viewModelScope.launch {
            try {
                mensagem.value = repository.salvarUsuario(login).message
            } catch (e: Exception) {
                onError.value = e
            }
            onLoading.value = false
        }
    }
}