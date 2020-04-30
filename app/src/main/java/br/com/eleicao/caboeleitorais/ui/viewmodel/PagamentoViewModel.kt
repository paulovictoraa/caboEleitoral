package br.com.eleicao.caboeleitorais.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.eleicao.caboeleitorais.model.Pagamento
import br.com.eleicao.caboeleitorais.repository.PagamentoRepository
import br.com.eleicao.caboeleitorais.repository.EleitorRepository

class PagamentoViewModel(
    private val pagamentoRepository: PagamentoRepository,
    private val eleitorRepository: EleitorRepository) : ViewModel() {

    fun salva(pagamento: Pagamento) = pagamentoRepository.salva(pagamento)
    fun buscaEleitorPorId(id: Long) = eleitorRepository.buscaPorId(id)

    fun todos(): LiveData<List<Pagamento>> = pagamentoRepository.todos()

}