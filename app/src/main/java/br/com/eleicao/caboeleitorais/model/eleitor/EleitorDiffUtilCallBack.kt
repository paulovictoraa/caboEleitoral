import androidx.recyclerview.widget.DiffUtil
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor

class EleitorDiffUtilCallBack : DiffUtil.ItemCallback<Eleitor>() {
    override fun areItemsTheSame(oldItem: Eleitor, newItem: Eleitor): Boolean {
        return oldItem.codigo == newItem.codigo
    }

    override fun areContentsTheSame(oldItem: Eleitor, newItem: Eleitor): Boolean {
        return oldItem == newItem
    }
}