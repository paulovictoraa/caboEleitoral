package br.com.eleicao.caboeleitorais.service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import br.com.eleicao.caboeleitorais.model.Token
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.ui.activity.MainActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val URL = "http://manoelmessias.pythonanywhere.com"
private const val UNAUTHORIZED = "UNAUTHORIZED"
private const val MESSAGE_UNAUTHORIZED = "Logue e tente novamente"

class AppService : KoinComponent {

    fun createInstance(): CaboEleitoralService {
        val client = createClient()
        val url = URL
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CaboEleitoralService::class.java)
    }

    private fun createClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(logging)
            .addInterceptor { chain ->
                val request = applyRequest(UsuarioInstance.getToken(), chain)
                val response = chain.proceed(request)
                response
            }
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    fun applyRequest(token: Token?, chain: Interceptor.Chain): Request =
        if (token !== null && token.acessToken.isNotEmpty()) {
            chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${token.acessToken}")
                .build()
        } else chain.request()
}
