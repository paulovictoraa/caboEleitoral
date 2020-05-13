package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _isLogado: MutableLiveData<Boolean> = MutableLiveData()
    val isLogado: LiveData<Boolean> = _isLogado

    fun login(login: Login) {
        viewModelScope.launch {
            try{
                repository.login(login)
                _isLogado.value = true
            } catch (e: Exception) {
                _isLogado.value = false
            }
        }
    }

    fun desloga() {
        repository.desloga()
    }

    fun estaLogado(): Boolean = repository.estaLogado()

    fun naoEstaLogado(): Boolean = !estaLogado()

}
