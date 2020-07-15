package br.com.eleicao.caboeleitorais.model

import br.com.eleicao.caboeleitorais.util.JWTUtil

object UsuarioInstance {
    var usuario: Usuario? = null

    fun getToken() = usuario?.token

    fun getCodigo(): String {
        val identity = getIdentify()
        return if (identity.isEmpty()) {
            identity
        } else {
            identity.split(":")[1]
        }
    }

    fun getIdentify(): String {
        val acessToken = getToken()?.acessToken ?: ""
        val identity = JWTUtil.decodeJwt(acessToken, "identity") ?: ""
        return identity
    }

    fun isAdmin(): Boolean = getIdentify().contains("adm")

}

data class Usuario(
    val login: Login,
    val token: Token
)