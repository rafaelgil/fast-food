package br.com.fiap.postech.fastfood.domain.repository

import br.com.fiap.postech.fastfood.domain.entity.Pedido
import java.util.*

interface PedidoRepository {

    fun cadastrar(pedido: Pedido): Pedido
    fun atualizar(pedido: Pedido): Pedido
    fun listarPedidosRecebidos(): List<Pedido>
    fun buscarPorId(id: UUID): Pedido?
}