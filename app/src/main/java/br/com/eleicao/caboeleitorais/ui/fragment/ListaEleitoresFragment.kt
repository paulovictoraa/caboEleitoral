package br.com.eleicao.caboeleitorais.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.EleitorAdapter
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.EleitoresViewModel
import kotlinx.android.synthetic.main.lista_eleitores.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListaEleitoresFragment : BaseFragment() {

    private val viewModel: EleitoresViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val adapter: EleitorAdapter by inject()
    private val controlador by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buscaEleitores()
    }

    private fun buscaEleitores() {
        viewModel.buscaTodos().observe(this, Observer {
            it?.let {
                adapter.atualiza(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.lista_eleitores,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        estadoAppViewModel.temComponentes = ComponentesVisuais(
            appBar = true,
            bottomNavigation = true)
        configuraRecyclerView()
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        lista_eleitores_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = {
            vaiParaDetalhesDoEleitor(it.id)
        }
        lista_eleitores_recyclerview.adapter = adapter
    }

    private fun vaiParaDetalhesDoEleitor(eleitorId: Long) {
        val direcao = ListaEleitoresFragmentDirections
            .acaoListaEleitoresParaDetalhesEleitor(eleitorId)
        controlador.navigate(direcao)
    }

}
