package br.com.eleicao.caboeleitorais.exception

data class BusinessException(override val message: String) : Exception()