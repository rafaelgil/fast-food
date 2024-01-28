package br.com.fiap.postech.fastfood.adapter.gateway.events.dtos

import br.com.fiap.postech.fastfood.domain.entity.ItemPedido
import java.time.LocalDateTime
import java.util.*
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.entity.Produto

data class PedidoEvent(
        var id: UUID?,
        var itens: List<ItemEvent>?,
        var dataRecebimento: LocalDateTime?,
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

fun fromPedidoEntity(pedido: Pedido? = null): PedidoEvent {
    if (pedido != null) {

        return PedidoEvent(
                id = pedido.id,
                itens = pedido.itens.map { fromItemPedidoEntity(it) },
                dataRecebimento = pedido.dataRecebimento,
                status = pedido.status.name,
        )
    }
    return PedidoEvent(null, null, null, null)
}

fun fromItemPedidoEntity(item: ItemPedido): ItemEvent {
    return ItemEvent(
            item.id,
            fromProdutoEntity(item.produto),
            item.quantidade
    )
}

fun fromProdutoEntity(produto: Produto): ProdutoEvent {
    return ProdutoEvent(
            produto.id,
            produto.descricao.toString(),
            produto.categoria.toString()
    )
}