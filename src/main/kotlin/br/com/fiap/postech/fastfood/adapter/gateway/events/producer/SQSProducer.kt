package br.com.fiap.postech.fastfood.adapter.gateway.events.producer

import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.PedidoEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SQSProducer(
        private val sqsTemplate: QueueMessagingTemplate,
        @Value("\${aws.queue.producao.pedido.name}")
        val queueProductionEvent: String,
) {

    fun sendProducaoMessage(pedidoEvent: PedidoEvent) {
        val message = MessageBuilder.withPayload(pedidoEvent).build()
        sqsTemplate.convertAndSend(queueProductionEvent, message)
    }
}