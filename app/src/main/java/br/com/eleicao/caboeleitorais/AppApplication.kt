package br.com.eleicao.caboeleitorais

import android.app.Application
import br.com.eleicao.caboeleitorais.di.daoModule
import br.com.eleicao.caboeleitorais.di.testeDatabaseModule
import br.com.eleicao.caboeleitorais.di.uiModule
import br.com.eleicao.caboeleitorais.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
                    testeDatabaseModule,
                    daoModule,
                    uiModule,
                    viewModelModule
                )
            )
        }
    }
}