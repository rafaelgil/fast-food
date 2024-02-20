package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.fromPedidoEntity
import br.com.fiap.postech.fastfood.adapter.gateway.events.producer.SQSProducer
import br.com.fiap.postech.fastfood.domain.exception.NotFoundEntityException
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import org.slf4j.LoggerFactory
import java.util.*

class EnviaPedidoParaProducaoUsecase(
    private val pedidoRepository: PedidoRepository,
    private val sqsProducer: SQSProducer
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executa(idPedido: UUID) {

        val pedido = pedidoRepository.buscarPorId(idPedido)
            ?: throw NotFoundEntityException("Pedido ${idPedido} não encontrado")

        sqsProducer.sendProducaoMessage(fromPedidoEntity(pedido))
    }

}