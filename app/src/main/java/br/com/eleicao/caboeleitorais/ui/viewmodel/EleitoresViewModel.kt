package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class EleitoresViewModel(private val repository: EleitorRepository) : ViewModel() {

    fun buscaTodos(): LiveData<List<Eleitor>> = repository.buscaTodos()

}
