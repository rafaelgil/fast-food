package br.com.fiap.postech.fastfood.adapter.gateway.events.dtos

import java.util.*

data class PagamentoEvent(
    val id: UUID?,
    val status: String?,
    val pedidoId: UUID?
){
    constructor() : this(null, null, null)
}

data class PagamentoErrorEvent(
    val id: UUID?
){
    constructor() : this(null)
}