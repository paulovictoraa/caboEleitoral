package br.com.eleicao.caboeleitorais.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.Eleitor
import kotlinx.android.synthetic.main.item_eleitor.view.*

class EleitorAdapter(
    private val context: Context,
    private val eleitors: MutableList<Eleitor> = mutableListOf(),
    var onItemClickListener: (eleitor: Eleitor) -> Unit = {}
) : RecyclerView.Adapter<EleitorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_eleitor,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = eleitors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(eleitors[position])
    }

    fun atualiza(eleitoresNovos: List<Eleitor>) {
        notifyItemRangeRemoved(0, eleitors.size)
        eleitors.clear()
        eleitors.addAll(eleitoresNovos)
        notifyItemRangeInserted(0, eleitors.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var eleitor: Eleitor
        private val campoNome by lazy { itemView.item_eleitores_nome }
        private val campoEndereco by lazy { itemView.item_eleitores_endereco }

        init {
            itemView.setOnClickListener {
                if (::eleitor.isInitialized) {
                    onItemClickListener(eleitor)
                }
            }
        }

        fun vincula(eleitor: Eleitor) {
            this.eleitor = eleitor
            val nome = "${eleitor.codigo} - ${eleitor.nome}"
            campoNome.text = nome
            campoEndereco.text = eleitor.endereco
        }

    }

}
