package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.domain.exception.NotFoundEntityException
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import org.slf4j.LoggerFactory
import java.util.*

class MudarStatusPedidoUseCase(
    private val pedidoRepository: PedidoRepository,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executa(id: UUID, statusPedido: StatusPedido ): Pedido {

        val pedido = pedidoRepository.buscarPorId(id)
            ?: throw NotFoundEntityException("Pedido ${id} não encontrado")

        pedido.mudarStatus(statusPedido)

        logger.info("Pedido ${pedido.id} atualizado com status ${statusPedido.name}")

        return pedidoRepository.atualizar(pedido)
    }
}