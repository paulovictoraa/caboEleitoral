package br.com.eleicao.caboeleitorais.ui.recyclerview.adapter

import EleitorDiffUtilCallBack
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import kotlinx.android.synthetic.main.item_eleitor.view.*

class EleitorAdapter(
    private val context: Context,
    var onItemClickListener: (eleitor: Eleitor) -> Unit = {}
) : PagedListAdapter<Eleitor, EleitorAdapter.ViewHolder>(EleitorDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_eleitor,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val eleitor = getItem(position)
        eleitor?.let {
            holder.vincula(it)
        }
    }

    fun atualiza(eleitoresNovos: List<Eleitor>) {
//        notifyItemRangeRemoved(0, eleitors.size)
//        eleitors.clear()
//        eleitors.addAll(eleitoresNovos)
//        notifyItemRangeInserted(0, eleitors.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var eleitor: Eleitor
        private val campoInicial by lazy { itemView.item_eleitores_inicial }
        private val campoNome by lazy { itemView.item_eleitores_nome }
        private val campoEndereco by lazy { itemView.item_eleitores_endereco }
        private val campoSetor by lazy { itemView.item_eleitores_setor }
        private val caboEleitoral by lazy { itemView.item_eleitores_cabo_eleitoral }
        private val caboEleitoralLabel by lazy { itemView.item_eleitores_cabo_eleitoral_label }

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
            campoInicial.text = eleitor.nome.first().toUpperCase().toString()
            campoNome.text = nome
            campoSetor.text = eleitor.setor
            campoEndereco.text = eleitor.endereco
            if (UsuarioInstance.isAdmin()) {
                caboEleitoral.visibility = View.VISIBLE
                caboEleitoralLabel.visibility = View.VISIBLE
                caboEleitoral.text = eleitor.caboEleitoral
            }
        }

    }

}
