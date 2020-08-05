package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.repository.SetorRepository

class FiltroDialogViewModel(private val setorRepository: SetorRepository) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun buscarTodos() = setorRepository.buscarTodos()

    fun buscarPorNome(nome: String) = setorRepository.buscarPorNome(nome)

}