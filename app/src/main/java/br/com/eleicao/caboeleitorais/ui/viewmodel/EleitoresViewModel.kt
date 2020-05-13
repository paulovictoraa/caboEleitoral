package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eleicao.caboeleitorais.extension.unaccent
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EleitoresViewModel(private val repository: EleitorRepository) : ViewModel() {

    private val _eleitores: MutableLiveData<List<Eleitor>> = MutableLiveData()
    val eleitores: LiveData<List<Eleitor>> = _eleitores
    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val onError: MutableLiveData<String> = MutableLiveData()

    init {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _eleitores.postValue(repository.buscaTodos())
            } catch (e: Exception) {
                onError.postValue(e.message)
            } finally {
                isLoading.postValue(false)
            }
        }
    }

    fun filter(newText: String?): List<Eleitor> {
        val eleitores = _eleitores.value ?: mutableListOf()
        return if (newText.isNullOrEmpty()) {
            eleitores
        } else {
            val query = newText.toUpperCase(Locale.getDefault()).unaccent()
            eleitores.filter {
                it.nome.toUpperCase(Locale.getDefault()).unaccent().contains(query)
            }.toMutableList()
        }
    }

}