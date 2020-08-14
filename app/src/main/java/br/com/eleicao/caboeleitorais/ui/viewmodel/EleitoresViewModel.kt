package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import br.com.eleicao.caboeleitorais.model.Filtro
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class EleitoresViewModel(private val repository: EleitorRepository) : ViewModel() {

    val filtro: MutableLiveData<Filtro> = MutableLiveData(Filtro())

    private val eleitoresPaginado: LiveData<PagedList<Eleitor>> =
        Transformations.switchMap(filtro) { input ->
            if (input.isPossuiFiltro()) {
                repository.buscaTodos(input)
            } else {
                repository.buscaTodos()
            }
        }

    fun buscarEleitoresPaginado(): LiveData<PagedList<Eleitor>> = eleitoresPaginado

    fun filtrar(newText: String?) {
        if (newText == null) return
        val novoFiltro = filtro.value?.also {
            it.nome = newText
        }
        filtrar(novoFiltro)
    }

    fun filtrar(novoFiltro: Filtro?) {
        if (novoFiltro == null) return
        filtro.postValue(novoFiltro)
    }

}
