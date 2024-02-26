package br.com.fiap.postech.fastfood.adapter.presenter

import br.com.fiap.postech.fastfood.adapter.gateway.schema.ItemPedidoSchema
import br.com.fiap.postech.fastfood.adapter.gateway.schema.PedidoSchema
import br.com.fiap.postech.fastfood.domain.entity.ItemPedido
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PedidoRequest(
    @JsonProperty("id")
    var id: UUID? = null,
    @JsonProperty("cliente_id")
    var clienteId: UUID,
    @JsonProperty("itens")
    var itens: List<ItemPedidoRequest>? = mutableListOf(),
)

data class PedidoResponse(
    @JsonProperty("id")
    var id: UUID? = null,
    var clienteId: UUID,
    var status: String,
    @JsonProperty("itens")
    var itens: List<ItemPedidoResponse>? = mutableListOf(),
    @JsonProperty("valor_total")
    var valorTotal: BigDecimal? = BigDecimal.ZERO,
    var pagamentoId: UUID? = null
)

data class StatusPedidoResponse(
    @JsonProperty("id")
    var id: UUID? = null,
    //var nomeCliente: String,
    var dataCriacao: LocalDateTime,
    var dataRecebimento: LocalDateTime?,
    var status: String,
    var itens: List<ItemPedidoSimpleResponse> = mutableListOf(),
)


data class ItemPedidoRequest(
    @JsonProperty("produto_id")
    var produtoId: UUID,
    var quantidade: Int,
    var preco: BigDecimal
)

data class ItemPedidoResponse(
    @JsonProperty("id")
    var id: UUID,
    @JsonProperty("id_produto")
    var produtoId: UUID,
    var preco: BigDecimal,
    var quantidade: Int
)

data class ItemPedidoSimpleResponse(
    var preco: BigDecimal,
    var quantidade: Int
)

fun PedidoRequest.toPedido(): Pedido {
    return Pedido(
        id = UUID.randomUUID(),
        clienteId = clienteId,
        data = LocalDateTime.now(),
        status = StatusPedido.AGUARDANDO_PAGAMENTO
    ).apply {
        itens = this@toPedido.itens!!.map { it.toItem() }
    }
}

fun ItemPedidoRequest.toItem() =
    ItemPedido(
        id = null,
        produtoId = this.produtoId,
        quantidade = this.quantidade,
        preco = this.preco
    )

fun Pedido.toResponse() =
    PedidoResponse(
        id = this.id,
        clienteId = clienteId,
        status = this.status.status,
        valorTotal = valorTotal(),
        pagamentoId = pagamentoId
    ).apply {
        itens = this@toResponse.itens.map { it.toResponse() }
    }

fun Pedido.toStatusResponse() =
    StatusPedidoResponse(
        id = this.id,
        //nomeCliente = this.cliente.nome!!.nome,
        dataCriacao = this.data,
        status = this.status.status,
        dataRecebimento = this.dataRecebimento
    ).apply {
        itens = this@toStatusResponse.itens.map { it.toSimpleResponse() }
    }

fun ItemPedido.toResponse(): ItemPedidoResponse {
    return ItemPedidoResponse(
        id = this.id!!,
        produtoId = produtoId,
        preco = this.preco,
        quantidade = this.quantidade
    )
}

fun ItemPedido.toSimpleResponse(): ItemPedidoSimpleResponse {

    return ItemPedidoSimpleResponse(
        preco = this.preco,
        quantidade = this.quantidade
    )
}

fun Pedido.toPedidoSchema() =
    PedidoSchema(
        id = this.id,
        data = this.data,
        clienteId = this.clienteId,
        status = this.status,
        dataRecebimento = this.dataRecebimento,
        pagamentoId = this.pagamentoId
    ).apply {
        itens = this@toPedidoSchema.itens.map { it.toItemPedidoSchema(this) }
    }

fun ItemPedido.toItemPedidoSchema(pedidoSchema: PedidoSchema) =
    ItemPedidoSchema(
        id = this.id,
        pedido = pedidoSchema,
        produtoId = this.produtoId,
        preco = this.preco,
        quantidade = this.quantidade
    )

fun PedidoSchema.toPedido() =
    Pedido(
        id = this.id,
        data = this.data,
        status = this.status,
        clienteId = this.clienteId,
        dataRecebimento = this.dataRecebimento,
        pagamentoId = this.pagamentoId
    ).apply {
        itens = this@toPedido.itens.map { it.toItemPedido() }
    }

fun ItemPedidoSchema.toItemPedido() =
    ItemPedido(
        id = this.id,
        produtoId = this.produtoId,
        quantidade = this.quantidade,
        preco = this.preco
    )