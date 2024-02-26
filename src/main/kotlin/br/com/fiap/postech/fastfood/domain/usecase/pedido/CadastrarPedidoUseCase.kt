package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.adapter.gateway.apis.cliente.ClienteClient
import br.com.fiap.postech.fastfood.adapter.gateway.apis.produto.ProdutoClient
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.exception.ProdutoPrecoException
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository

class CadastrarPedidoUseCase(
    private val pedidoRepository: PedidoRepository,
    private val clienteClient: ClienteClient,
    private val produtoClient: ProdutoClient
) {
    fun executa(pedido: Pedido): Pedido {
        validarCliente(pedido)

        validarItemProduto(pedido)

        return pedidoRepository.cadastrar(pedido)
    }

    private fun validarItemProduto(pedido: Pedido) {
        pedido.itens.apply {
            this.forEach {
                val produto = produtoClient.consultarProduto(it.produtoId)

                if(produto.preco != it.preco) {
                    throw ProdutoPrecoException("O produto ${it.produtoId} está com preço diferente do catálogo")
                }
            }
        }
    }

    private fun validarCliente(pedido: Pedido ) {
        clienteClient.consultarCliente(pedido.clienteId)
    }

}