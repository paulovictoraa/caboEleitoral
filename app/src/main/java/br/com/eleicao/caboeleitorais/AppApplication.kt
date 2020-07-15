package br.com.eleicao.caboeleitorais

import android.app.Application
import br.com.eleicao.caboeleitorais.di.*
import br.com.eleicao.caboeleitorais.service.AppService
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                listOf(
//                    testeDatabaseModule,
                    databaseModule,
                    daoModule,
                    uiModule,
                    viewModelModule,
                    serviceModule
                )
            )
        }
    }
}