package br.com.fiap.postech.fastfood.adapter.gateway.events.dtos

import java.util.*

data class ProducaoPedidoEvent(
    val id: UUID?,
    val status: String?,
){
    constructor() : this(null, null)
}