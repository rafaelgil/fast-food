package br.com.fiap.postech.fastfood.frameworks

import br.com.fiap.postech.fastfood.adapter.gateway.PedidoRepositoryImpl
import br.com.fiap.postech.fastfood.adapter.gateway.apis.cliente.ClienteClient
import br.com.fiap.postech.fastfood.adapter.gateway.apis.pagamento.PagamentoClient
import br.com.fiap.postech.fastfood.adapter.gateway.apis.produto.ProdutoClient
import br.com.fiap.postech.fastfood.adapter.gateway.events.producer.SQSProducer
import br.com.fiap.postech.fastfood.adapter.gateway.jpa.PedidoRepositoryJpa
import br.com.fiap.postech.fastfood.domain.repository.PedidoRepository
import br.com.fiap.postech.fastfood.domain.usecase.pedido.IniciarPedidoCheckoutUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pagamento.GerarPagamentoUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class Configuration {

    @Bean
    fun pedidoRepository(pedidoRepositoryJpa: PedidoRepositoryJpa): PedidoRepository {
        return PedidoRepositoryImpl(pedidoRepositoryJpa)
    }

    @Bean
    fun listarPedidosUseCase(pedidoRepository: PedidoRepository): ListarPedidoUseCase {
        return ListarPedidoUseCase(pedidoRepository)
    }

    @Bean
    fun cadastrarPedidoUseCase(pedidoRepository: PedidoRepository,
                               clienteClient: ClienteClient,
                               produtoClient: ProdutoClient) =
        CadastrarPedidoUseCase(pedidoRepository,
            clienteClient, produtoClient)

    @Bean
    fun gerarPagamentoQrCodeUseCase(pagamentoClient: PagamentoClient): GerarPagamentoUseCase {
        return GerarPagamentoUseCase(pagamentoClient)
    }

    @Bean
    fun mudarStatusPedidoUseCase(pedidoRepository: PedidoRepository): MudarStatusPedidoUseCase{
        return MudarStatusPedidoUseCase(pedidoRepository)
    }

    @Bean
    fun iniciarCheckoutUseCase(gerarPagamentoUseCase: GerarPagamentoUseCase,
                               cadastrarPedidoUseCase: CadastrarPedidoUseCase,
    ): IniciarPedidoCheckoutUseCase {
        return IniciarPedidoCheckoutUseCase(gerarPagamentoUseCase, cadastrarPedidoUseCase)
    }

    @Bean
    fun listarTodosPedidosUseCase(
        pedidoRepository: PedidoRepository
    ): ListarTodosPedidosUseCase {
        return ListarTodosPedidosUseCase(pedidoRepository)
    }

    @Bean
    fun enviaPedidoParaProducaoUsecase(pedidoRepository: PedidoRepository,
                                       produtoClient: ProdutoClient,
                                       sqsProducer: SQSProducer
    ): EnviaPedidoParaProducaoUsecase {
        return EnviaPedidoParaProducaoUsecase(pedidoRepository, produtoClient, sqsProducer)
    }

    @Bean
    fun cancelarPedidosClienteUseCase(pedidoRepository: PedidoRepository
    ): CancelarPedidosClienteUseCase {
        return CancelarPedidosClienteUseCase(pedidoRepository)
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}