package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class CadastroEleitorViewModel(
    private val eleitorRepository: EleitorRepository
) : ViewModel() {

    fun salva(eleitor: Eleitor) = eleitorRepository.salva(eleitor)

}