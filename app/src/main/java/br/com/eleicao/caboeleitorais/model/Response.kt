package br.com.eleicao.caboeleitorais.model

data class Response<T>(
    val response: T?,
    val message: String
)