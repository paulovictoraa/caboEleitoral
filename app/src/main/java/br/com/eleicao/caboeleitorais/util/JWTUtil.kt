package br.com.eleicao.caboeleitorais.util

import br.com.eleicao.caboeleitorais.extension.JWTDecode
import br.com.eleicao.caboeleitorais.model.UsuarioInstance

object JWTUtil {
    @JvmStatic
    fun decodeJwt(token: String, propertie: String) = token.JWTDecode(propertie)

    fun decodeJwt(propertie: String): String {
        val token = UsuarioInstance.getToken()?.acessToken ?: ""
        return token.JWTDecode(propertie)
    }
}