package br.com.fiap.postech.fastfood.adapter.presenter

import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPagamento
import java.math.BigDecimal
import java.util.*

data class PedidoPagamentoRequest(
    val id: UUID,
    val clienteId: UUID,
    val valor: BigDecimal,
    val destinatarioPix: DestinatarioPixRequest
)

data class DestinatarioPixRequest(
    val nomeDestinatario: String,
    val chaveDestinatario: String,
    val descricao: String,
    val cidade: String
)

fun Pedido.toPagamentoRequest(destinatario: DestinatarioPixRequest) =
    PedidoPagamentoRequest(
        id = id!!,
        clienteId = clienteId,
        valor = valorTotal(),
        destinatarioPix = destinatario
    )