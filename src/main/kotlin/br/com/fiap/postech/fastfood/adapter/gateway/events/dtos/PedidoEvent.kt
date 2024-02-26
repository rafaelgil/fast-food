package br.com.fiap.postech.fastfood.adapter.gateway.events.dtos

import java.util.*

data class PedidoEvent(
        var id: UUID?,
        var itens: List<ItemEvent>?,
        var status: String?,
)

data class ItemEvent(
        val id: UUID?,
        val produto: ProdutoEvent,
        val quantidade: Int,
)

data class ProdutoEvent(
        val id: UUID?,
        val descricao: String?,
        val categoria: String?,
)