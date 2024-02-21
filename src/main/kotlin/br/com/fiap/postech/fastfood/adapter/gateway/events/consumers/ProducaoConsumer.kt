package br.com.fiap.postech.fastfood.adapter.gateway.events.consumers


import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.PedidoEvent
import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.ProducaoPedidoEvent
import br.com.fiap.postech.fastfood.domain.usecase.pedido.MudarStatusPedidoUseCase
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.slf4j.LoggerFactory
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener
import org.springframework.stereotype.Component


@Component
class ProducaoConsumer(
    private val mudarStatusPedidoUseCase: MudarStatusPedidoUseCase,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @SqsListener(value = ["\${aws.queue.status.producao.name}"])
    fun receiveMessage(message: String) {
        logger.info("Recebendo mensagem da producao ${message}")

        val event = ObjectMapper().readValue<ProducaoPedidoEvent>(message)

        mudarStatusPedidoUseCase.executa(event.id!!, StatusPedido.valueOf(event.status!!))
    }

}