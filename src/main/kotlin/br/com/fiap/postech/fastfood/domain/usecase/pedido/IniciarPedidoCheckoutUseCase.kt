package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.usecase.pagamento.GerarPagamentoUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.CadastrarPedidoUseCase

class IniciarPedidoCheckoutUseCase(
    private val gerarPagamentoUseCase: GerarPagamentoUseCase,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
) {

    fun executa(pedido: Pedido): Pedido {
        val pagamento = gerarPagamentoUseCase.executa(pedido)

        pedido.atualizaPagamento(pagamento)

        return cadastrarPedidoUseCase.executa(pedido)
    }
}