package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import org.slf4j.LoggerFactory
import java.util.*

class CancelarPedidosClienteUseCase(
    private val pedidoRepository: PedidoRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executa(clienteId: UUID) {

        pedidoRepository.buscarPorClienteIdEAguardandoPagamento(clienteId)
        .forEach {
            it.mudarStatus(StatusPedido.CANCELADO)

            logger.info("Pedido ${it.id} atualizado com status ${StatusPedido.CANCELADO}")

            pedidoRepository.atualizar(it)
        }

        logger.info("Pedidos do cliente $clienteId que estavam aguardando pagamento cancelados")
    }
}