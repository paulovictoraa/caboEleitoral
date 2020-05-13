package br.com.eleicao.caboeleitorais.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.eleicao.caboeleitorais.R
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.EleitorAdapter
import br.com.eleicao.caboeleitorais.ui.viewmodel.ComponentesVisuais
import br.com.eleicao.caboeleitorais.ui.viewmodel.EleitoresViewModel
import br.com.eleicao.caboeleitorais.ui.viewmodel.EstadoAppViewModel
import kotlinx.android.synthetic.main.lista_eleitores.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListaEleitoresFragment : BaseFragment() {

    private val viewModel: EleitoresViewModel by viewModel()
    private val estadoAppViewModel: EstadoAppViewModel by sharedViewModel()
    private val adapter: EleitorAdapter by inject()
    private val controlador by lazy { findNavController() }

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
            bottomNavigation = false
        )
        configuraRecyclerView()
        configuraFabButton()
        observaEleitores()
        observaIsLoading()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchAction = menu.findItem(R.id.search_action)
        searchAction.isVisible = true
        val searchView = searchAction?.actionView as SearchView
        configuraSearchView(searchView)
    }

    private fun configuraSearchView(searchView: SearchView) {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val eleitoresFiltrados = viewModel.filter(newText)
                adapter.atualiza(eleitoresFiltrados)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun configuraFabButton() {
        lista_eleitores_button.setOnClickListener {
            vaiParaCadastroDoEleitor()
        }
    }

    private fun observaIsLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                progress_bar.visibility = View.VISIBLE
                lista_eleitores_recyclerview.visibility = View.GONE
            } else {
                progress_bar.visibility = View.GONE
                lista_eleitores_recyclerview.visibility = View.VISIBLE
            }
        })
    }

    private fun observaEleitores() {
        viewModel.eleitores.observe(viewLifecycleOwner, Observer {
            it?.let { adapter.atualiza(it) }
        })
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        lista_eleitores_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = {
            vaiParaDetalhesDoEleitor(it.codigo)
        }
        lista_eleitores_recyclerview.adapter = adapter
    }

    private fun vaiParaDetalhesDoEleitor(eleitorId: Long) {
        val direcao = ListaEleitoresFragmentDirections
            .acaoListaEleitoresParaDetalhesEleitor(eleitorId)
        controlador.navigate(direcao)
    }

    private fun vaiParaCadastroDoEleitor() {
        val direcao = ListaEleitoresFragmentDirections
            .acaoListaEleitoresParaCadastroEleitorFragment()
        controlador.navigate(direcao)
    }
}
