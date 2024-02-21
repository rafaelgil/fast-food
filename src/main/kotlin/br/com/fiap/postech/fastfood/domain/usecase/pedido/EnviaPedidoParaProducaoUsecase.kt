package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.adapter.gateway.apis.produto.ProdutoClient
import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.ItemEvent
import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.PedidoEvent
import br.com.fiap.postech.fastfood.adapter.gateway.events.dtos.ProdutoEvent
import br.com.fiap.postech.fastfood.adapter.gateway.events.producer.SQSProducer
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.exception.NotFoundEntityException
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import org.slf4j.LoggerFactory
import java.util.*

class EnviaPedidoParaProducaoUsecase(
    private val pedidoRepository: PedidoRepository,
    private val produtoClient: ProdutoClient,
    private val sqsProducer: SQSProducer
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executa(idPedido: UUID) {

        val pedido = pedidoRepository.buscarPorId(idPedido)
            ?: throw NotFoundEntityException("Pedido ${idPedido} não encontrado")

        val itensEventList = createItemEvents(pedido)

        sqsProducer.sendProducaoMessage(PedidoEvent(pedido.id, itensEventList, pedido.status.name))
    }

    private fun createItemEvents(pedido: Pedido): MutableList<ItemEvent> {
        val itensEventList = mutableListOf<ItemEvent>()

        pedido.itens.forEach {
            val produto = produtoClient.consultarProduto(it.produtoId)
            val produtoEvent = ProdutoEvent(produto.id, produto.descricao, produto.categoria)
            val item = ItemEvent(it.id, produtoEvent, it.quantidade)

            itensEventList.add(item)
        }

        return itensEventList
    }

}