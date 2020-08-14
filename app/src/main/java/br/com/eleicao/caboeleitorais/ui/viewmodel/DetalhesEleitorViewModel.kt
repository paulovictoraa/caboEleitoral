package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class DetalhesEleitorViewModel(
    eleitorId: Long,
    repository: EleitorRepository
) : ViewModel() {

    val eleitor = repository.buscaPorId(eleitorId)

}