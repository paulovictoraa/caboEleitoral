package br.com.eleicao.caboeleitorais.service

import br.com.eleicao.caboeleitorais.model.Eleitor
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.model.Token
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CaboEleitoralService {

    @POST("login")
    suspend fun login(@Body login: Login): Token

    @GET("eleitores")
    suspend fun listar(): MutableList<Eleitor>

    @GET("eleitores/{id}")
    suspend fun listarPorId(@Path("id") id: String): MutableList<Eleitor>

    @POST("cadastro/{id}")
    suspend fun salvar(@Path("id") id: String, @Body eleitor: Eleitor): Eleitor

}