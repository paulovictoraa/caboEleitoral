package br.com.eleicao.caboeleitorais.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.model.Usuario
import br.com.eleicao.caboeleitorais.model.UsuarioInstance
import br.com.eleicao.caboeleitorais.service.CaboEleitoralService
import com.google.gson.Gson

private const val CHAVE_LOGADO = "LOGADO"
private const val CHAVE_SERVIDOR = "SERVIDOR"

class LoginRepository(
    private val preferences: SharedPreferences,
    private val service: CaboEleitoralService
) {

    suspend fun login(login: Login) {
        val token = service.login(login)
        val usuario = Usuario(login, token)
        UsuarioInstance.usuario = usuario
        salva(usuario)
    }

    fun desloga() = salva("")

    fun estaLogado(): Boolean {
        val json = preferences.getString(CHAVE_LOGADO, null)
        return if (json.isNullOrEmpty()) {
            false
        } else {
            return isUsuarioSalvoValido()
        }
    }

    private fun isUsuarioSalvoValido(): Boolean {
        if (UsuarioInstance.usuario?.token == null) {
            return try {
                val json = preferences.getString(CHAVE_LOGADO, "")
                UsuarioInstance.usuario = Gson().fromJson(json, Usuario::class.java)
                true
            } catch (e: Exception) {
                false
            }
        }

        return true
    }

    private fun salva(usuario: Usuario) {
        val json = Gson().toJson(usuario)
        salva(json)
    }

    private fun salva(login: String) {
        preferences.edit {
            putString(CHAVE_LOGADO, login)
        }
    }

    fun salvaConfiguracaoServidor(link: String) {
        preferences.edit {
            putString(CHAVE_SERVIDOR, link)
        }
    }

    fun getConfiguracaoServidor(): String {
        val defaultLink = "http://manoelmessias.pythonanywhere.com/"
        val link = preferences.getString(CHAVE_SERVIDOR, defaultLink)
        return if (link.isNullOrEmpty()) {
            defaultLink
        } else {
            link
        }
    }

}

