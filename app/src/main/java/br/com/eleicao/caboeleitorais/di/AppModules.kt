package br.com.eleicao.caboeleitorais.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.eleicao.caboeleitorais.database.AppDatabase
import br.com.eleicao.caboeleitorais.database.dao.CadastradosDAO
import br.com.eleicao.caboeleitorais.database.dao.PagamentoDAO
import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import br.com.eleicao.caboeleitorais.repository.LoginRepository
import br.com.eleicao.caboeleitorais.repository.PagamentoRepository
import br.com.eleicao.caboeleitorais.ui.fragment.DetalhesEleitorFragment
import br.com.eleicao.caboeleitorais.ui.fragment.ListaEleitoresFragment
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.EleitorAdapter
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.ListaPagamentosAdapter
import br.com.eleicao.caboeleitorais.ui.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "caboeleitorais.db"
private const val NOME_BANCO_DE_DADOS_TESTE = "caboeleitorais-test.db"

val testeDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
                get(),
                AppDatabase::class.java,
                NOME_BANCO_DE_DADOS_TESTE
        ).fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(IO).launch {
                            val dao: CadastradosDAO by inject()
                            dao.salva(
                                    Eleitor(
                                            nome = "Paulo Victor Alexandre Alves",
                                            endereco = "Rua 1121, número 99",
                                            setor = "Marista",
                                            telefone = "62992694928",
                                            data_nascimento = "25/06/1996",
                                            colegio = "CEJAT",
                                            observacao = "Residência em frente a garagem da Nissan"
                                    ),
                                    Eleitor(
                                            nome = "Jonelito Lenda",
                                            endereco = "Lá no morro do universitário",
                                            setor = "Quebra-caixote",
                                            telefone = "62982954185",
                                            data_nascimento = "15/06/1996",
                                            colegio = "Playboy",
                                            observacao = "Careca de tanta habilidade"
                                    ),
                                    Eleitor(
                                            nome = "Mano Manoel",
                                            endereco = "Rua curvada do Cepal",
                                            setor = "Universitário",
                                            telefone = "62982586328",
                                            data_nascimento = "20/10/1994",
                                            colegio = "Pre-universitario",
                                            observacao = "Monstrao, habilidoso e patim no wow"
                                    )
                            )
                        }
                    }
                }).build()
    }
}

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
                get(),
                AppDatabase::class.java,
                NOME_BANCO_DE_DADOS
        ).build()
    }
}

val daoModule = module {
    single<CadastradosDAO> { get<AppDatabase>().produtoDao() }
    single<PagamentoDAO> { get<AppDatabase>().pagamentoDao() }
    single<EleitorRepository> { EleitorRepository(get()) }
    single<PagamentoRepository> { PagamentoRepository(get()) }
    single<LoginRepository> { LoginRepository(get()) }
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val uiModule = module {
    factory<DetalhesEleitorFragment> { DetalhesEleitorFragment() }
    factory<ListaEleitoresFragment> { ListaEleitoresFragment() }
    factory<EleitorAdapter> { EleitorAdapter(get()) }
    factory<ListaPagamentosAdapter> { ListaPagamentosAdapter(get()) }
}

val viewModelModule = module {
    viewModel<EleitoresViewModel> { EleitoresViewModel(get()) }
    viewModel<DetalhesEleitorViewModel> { (id: Long) -> DetalhesEleitorViewModel(id, get()) }
    viewModel<PagamentoViewModel> { PagamentoViewModel(get(), get()) }
    viewModel<LoginViewModel> { LoginViewModel(get()) }
    viewModel<EstadoAppViewModel> { EstadoAppViewModel() }
}