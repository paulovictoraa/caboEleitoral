package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import br.com.eleicao.caboeleitorais.repository.SetorRepository

class CadastroEleitorViewModel(
    private val eleitorRepository: EleitorRepository,
    private val setorRepository: SetorRepository
) : ViewModel() {

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun salva(eleitor: Eleitor) = eleitorRepository.salva(eleitor)

    fun buscarSetores() = setorRepository.buscarTodos()

    fun buscarPorNome(setor: String) = setorRepository.buscarPorNome(setor)

}