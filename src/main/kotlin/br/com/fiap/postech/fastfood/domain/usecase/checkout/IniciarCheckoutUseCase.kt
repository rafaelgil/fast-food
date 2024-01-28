package br.com.fiap.postech.fastfood.domain.usecase.checkout

import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.fromPedidoEntity
import br.com.fiap.postech.fastfood.adapter.gateway.events.producer.SQSProducer
import br.com.fiap.postech.fastfood.domain.entity.Checkout
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.repository.CheckoutRepository
import br.com.fiap.postech.fastfood.domain.usecase.pagamento.GerarPagamentoUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.CadastrarPedidoUseCase
import java.time.LocalDateTime

class IniciarCheckoutUseCase(
    private val gerarPagamentoUseCase: GerarPagamentoUseCase,
    private val checkoutRepository: CheckoutRepository,
    private val cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    private val sqsProducer: SQSProducer
) {

    fun executa(pedido: Pedido): Checkout {
        val pagamento = gerarPagamentoUseCase.executa(pedido)

        pedido.atualizaPagamento(pagamento)

        val pedidoCadastrado = cadastrarPedidoUseCase.executa(pedido)

        return criarCheckout(pedidoCadastrado)
    }

    private fun criarCheckout(pedido: Pedido): Checkout {

        val pedidoEvent = fromPedidoEntity(pedido)
        sqsProducer.sendProducaoMessage(pedidoEvent)

        return Checkout(
            pedido = pedido,
            data = LocalDateTime.now()
        )
    }
}