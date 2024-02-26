package br.com.fiap.postech.fastfood.adapter.gateway

import br.com.fiap.postech.fastfood.adapter.gateway.jpa.PedidoRepositoryJpa
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.adapter.presenter.toPedido
import br.com.fiap.postech.fastfood.adapter.presenter.toPedidoSchema
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import java.util.*

class PedidoRepositoryImpl(
    private val pedidoRepositoryJpa: PedidoRepositoryJpa
): PedidoRepository {
    override fun cadastrar(pedido: Pedido) =
        pedidoRepositoryJpa.save(pedido.toPedidoSchema()).toPedido()
    override fun atualizar(pedido: Pedido) =
        this.cadastrar(pedido)

    override fun listarPedidosRecebidos(): List<Pedido> {
        return pedidoRepositoryJpa.findAll().map{ it.toPedido() }
    }

    override fun buscarPorId(id: UUID): Pedido? {
        val opPedido = pedidoRepositoryJpa.findById(id)

       if (opPedido.isPresent) {
           return opPedido.get().toPedido()
       }

       return null
    }

}