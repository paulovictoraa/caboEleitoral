package br.com.eleicao.caboeleitorais.model

import br.com.eleicao.caboeleitorais.util.JWTUtil

object UsuarioInstance {
    var usuario: Usuario? = null

    fun getToken() = usuario?.token

    fun getCodigo(): String {
        val acessToken = getToken()?.acessToken ?: ""
        val identity = JWTUtil.decodeJwt(acessToken, "identity") ?: ""
        return if (identity.isEmpty()) {
            identity
        } else {
            identity.split(":")[1]
        }
    }
}

data class Usuario(
    val login: Login,
    val token: Token
)