package br.com.fiap.postech.fastfood.domain.usecase.pedido

import br.com.fiap.postech.fastfood.adapter.gateway.apis.cliente.ClienteClient
import br.com.fiap.postech.fastfood.adapter.gateway.apis.produto.ProdutoClient
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import br.com.fiap.postech.fastfood.domain.exception.ClienteInativoException
import br.com.fiap.postech.fastfood.domain.exception.ProdutoPrecoException
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import org.slf4j.LoggerFactory

class CadastrarPedidoUseCase(
    private val pedidoRepository: PedidoRepository,
    private val clienteClient: ClienteClient,
    private val produtoClient: ProdutoClient
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executa(pedido: Pedido): Pedido {
        validarCliente(pedido)

        validarItemProduto(pedido)

        logger.info("Cadastrando pedido ${pedido}")
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
        val cliente = clienteClient.consultarCliente(pedido.clienteId)

        cliente.status.let {
            if(it != "ATIVO") {
                throw ClienteInativoException("Cliente ${pedido.clienteId} está inativo")
            }
        }
    }

}