package br.com.eleicao.caboeleitorais.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.eleicao.caboeleitorais.database.AppDatabase
import br.com.eleicao.caboeleitorais.database.dao.CadastradosDAO
import br.com.eleicao.caboeleitorais.model.Token
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.repository.EleitorRepository
import br.com.eleicao.caboeleitorais.repository.LoginRepository
import br.com.eleicao.caboeleitorais.repository.PagamentoRepository
import br.com.eleicao.caboeleitorais.service.CaboEleitoralService
import br.com.eleicao.caboeleitorais.ui.fragment.DetalhesEleitorFragment
import br.com.eleicao.caboeleitorais.ui.fragment.ListaEleitoresFragment
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.EleitorAdapter
import br.com.eleicao.caboeleitorais.ui.recyclerview.adapter.ListaPagamentosAdapter
import br.com.eleicao.caboeleitorais.ui.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
//                        dao.salva(
//                            Eleitor(
//                                nome = "Paulo Victor Alexandre Alves",
//                                endereco = "Rua 1121, número 99",
//                                setor = "Marista",
//                                telefone = "62992694928",
//                                dataNascimento = "25/06/1996",
//                                colegioDeVotacao = "CEJAT",
//                                observacao = "Residência em frente a garagem da Nissan"
//                            ),
//                            Eleitor(
//                                nome = "Jonelito Lenda",
//                                endereco = "Lá no morro do universitário",
//                                setor = "Quebra-caixote",
//                                telefone = "62982954185",
//                                dataNascimento = "15/06/1996",
//                                colegioDeVotacao = "Playboy",
//                                observacao = "Careca de tanta habilidade"
//                            ),
//                            Eleitor(
//                                nome = "Mano Manoel",
//                                endereco = "Rua curvada do Cepal",
//                                setor = "Universitário",
//                                telefone = "62982586328",
//                                dataNascimento = "20/10/1994",
//                                colegioDeVotacao = "Pre-universitario",
//                                observacao = "Monstrao, habilidoso e patim no wow"
//                            )
//                        )
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
    single { get<AppDatabase>().produtoDao() }
    single { get<AppDatabase>().pagamentoDao() }
    single { EleitorRepository(get(), get()) }
    single { PagamentoRepository(get()) }
    single { LoginRepository(get(), get()) }
    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val uiModule = module {
    factory { DetalhesEleitorFragment() }
    factory { ListaEleitoresFragment() }
    factory { EleitorAdapter(get()) }
    factory { ListaPagamentosAdapter(get()) }
}

val viewModelModule = module {
    viewModel { EleitoresViewModel(get()) }
    viewModel { (id: Long) -> DetalhesEleitorViewModel(id, get()) }
    viewModel { PagamentoViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { EstadoAppViewModel() }
    viewModel { CadastroEleitorViewModel(get()) }
}

val serviceModule = module {
    single { createWebService<CaboEleitoralService>("http://35.224.215.210/") }
}

inline fun <reified T> createWebService(url: String): T {

    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addNetworkInterceptor(logging)
        .addInterceptor { chain ->
            val request = applyRequest(UsuarioInstance.getToken(), chain)
            chain.proceed(request)
        }
        .connectTimeout(5, TimeUnit.MINUTES)
        .readTimeout(5, TimeUnit.MINUTES)
        .writeTimeout(5, TimeUnit.MINUTES)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(T::class.java)
}

fun applyRequest(token: Token?, chain: Interceptor.Chain): Request =
    if (token !== null && token.acessToken.isNotEmpty()) {
        chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${token.acessToken}")
            .build()
    } else chain.request()