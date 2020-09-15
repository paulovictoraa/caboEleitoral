package br.com.eleicao.caboeleitorais.service

import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.model.Token
import br.com.eleicao.caboeleitorais.model.UsuarioResponse
import br.com.eleicao.caboeleitorais.model.eleitor.Eleitor
import retrofit2.http.*

interface CaboEleitoralService {

    @POST("login")
    suspend fun login(@Body login: Login): Token

    @GET("eleitores")
    suspend fun listar(@Query("offset") offset: Int = 0): MutableList<Eleitor>

    @GET("eleitores/{id}")
    suspend fun listarPorId(@Path("id") id: String): MutableList<Eleitor>

    @POST("cadastro/{id}")
    suspend fun salvar(@Path("id") id: String, @Body eleitor: Eleitor): Eleitor

    @POST("cadastro_usuario")
    suspend fun salvarUsuario(@Body login: Login): UsuarioResponse

}