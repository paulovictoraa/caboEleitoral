package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import br.com.eleicao.caboeleitorais.repository.SetorRepository
import kotlinx.coroutines.launch

class CadastroEleitorViewModel(
    private val eleitorRepository: EleitorRepository,
    private val setorRepository: SetorRepository
) : ViewModel() {

    private val _eleitor = MutableLiveData<Eleitor>()
    val eleitor: LiveData<Eleitor> = _eleitor
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError: MutableLiveData<Exception> = MutableLiveData()

    fun salva(eleitor: Eleitor) {
        viewModelScope.launch {
            try {
                val eleitorNuvem = eleitorRepository.salvar(eleitor)
                _eleitor.value = eleitorNuvem
                salvarLocal(eleitorNuvem)
            } catch (e: Exception) {
                salvarLocal(eleitor)
                onError.value = e
            }
        }
    }

    fun buscarSetores() = setorRepository.buscarTodos()

    fun buscarPorNome(setor: String) = setorRepository.buscarPorNome(setor)

    fun salvarLocal(eleitor: Eleitor) = eleitorRepository.salvarLocal(eleitor)

}