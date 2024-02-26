package br.com.fiap.postech.fastfood.adapter.presenter

import br.com.fiap.postech.fastfood.domain.entity.Pagamento
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPagamento
import java.util.*

data class PagamentoResponse(
    var id: UUID,
    var status: String
)

fun PagamentoResponse.toPagamento() =
    Pagamento(
        id = id,
        status = StatusPagamento.valueOf(status)
    )