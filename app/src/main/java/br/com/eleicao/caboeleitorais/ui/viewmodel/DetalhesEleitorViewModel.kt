package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class DetalhesEleitorViewModel(
    produtoId: Long,
    repository: EleitorRepository
) : ViewModel() {

    val produtoEncontrado = repository.buscaPorId(produtoId)

}