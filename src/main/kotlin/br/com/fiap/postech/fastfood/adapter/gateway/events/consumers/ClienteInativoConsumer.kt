package br.com.fiap.postech.fastfood.adapter.gateway.events.consumers


import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.ClienteInativoEvent
import br.com.fiap.postech.fastfood.domain.usecase.pedido.CancelarPedidosClienteUseCase
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component


@Component
class ClienteInativoConsumer(
    private val cancelarPedidosClienteUseCase: CancelarPedidosClienteUseCase,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @SqsListener(value = ["\${aws.queue.notificacao-cliente-inativo.name}"])
    fun receiveMessage(message: String) {
        logger.info("Cancelando todos os pedidos do cliente ${message}")

        val event = ObjectMapper().readValue<ClienteInativoEvent>(message)

        cancelarPedidosClienteUseCase.executa(event.id!!)
    }

}