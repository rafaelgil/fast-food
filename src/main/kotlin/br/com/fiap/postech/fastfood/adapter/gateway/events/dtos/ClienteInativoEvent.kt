package br.com.fiap.postech.fastfood.adapter.gateway.events.dtos

import java.util.*

data class ClienteInativoEvent(
    val id: UUID?
){
    constructor() : this(null)
}