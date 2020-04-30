package br.com.eleicao.caboeleitorais.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.Pagamento
import kotlinx.android.synthetic.main.item_pagamento.view.*

class ListaPagamentosAdapter(
    private val context: Context,
    pagamentos: List<Pagamento> = listOf()
) : RecyclerView.Adapter<ListaPagamentosAdapter.ViewHolder>() {

    private val pagamentos = pagamentos.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_pagamento,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount(): Int = pagamentos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pagamento = pagamentos[position]
        holder.vincula(pagamento)
    }

    fun add(pagamentos: List<Pagamento>) {
        notifyItemRangeRemoved(0, this.pagamentos.size)
        this.pagamentos.clear()
        this.pagamentos.addAll(pagamentos)
        notifyItemRangeInserted(0, this.pagamentos.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val id by lazy {
            itemView.item_pagamento_id
        }
        private val preco by lazy {
            itemView.item_pagamento_preco
        }

        fun vincula(pagamento: Pagamento) {
            id.text = pagamento.id.toString()
            preco.text = pagamento.preco
        }
    }
}
