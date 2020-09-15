package br.com.eleicao.caboeleitorais.repository

import br.com.eleicao.caboeleitorais.model.Login
import br.com.eleicao.caboeleitorais.service.CaboEleitoralService

class UsuarioRepository(private val service: CaboEleitoralService) {

    suspend fun salvarUsuario(login: Login) = service.salvarUsuario(login)

}