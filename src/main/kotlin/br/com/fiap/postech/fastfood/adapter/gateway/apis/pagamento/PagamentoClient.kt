package br.com.fiap.postech.fastfood.adapter.gateway.apis.pagamento

import br.com.fiap.postech.fastfood.adapter.presenter.DestinatarioPixRequest
import br.com.fiap.postech.fastfood.adapter.presenter.PagamentoResponse
import br.com.fiap.postech.fastfood.adapter.presenter.PedidoPagamentoRequest
import br.com.fiap.postech.fastfood.adapter.presenter.toPagamentoRequest
import br.com.fiap.postech.fastfood.domain.entity.Pedido
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class PagamentoClient(
    @Autowired
    val restTemplate: RestTemplate,
    @Value("\${dados.pagamento.nomeDestinatario}")
    val nomeDestinatario: String,
    @Value("\${dados.pagamento.chaveDestinatario}")
    val chaveDestinatario: String,
    @Value("\${dados.pagamento.cidade}")
    val cidade: String,
    @Value("\${pagamento.url}")
    val pagamentoUrl: String,

) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun gerarPagamento(pedido: Pedido): PagamentoResponse {
        logger.info("Gerando pagamento para o pedido=${pedido.id}")

        val destinatario = DestinatarioPixRequest(
            nomeDestinatario = nomeDestinatario,
            chaveDestinatario = chaveDestinatario,
            descricao = "PEDIDO ${pedido.id}",
            cidade = cidade
        )

        logger.info("Url do pagamento: ${pagamentoUrl}")

        val request: HttpEntity<PedidoPagamentoRequest> = HttpEntity<PedidoPagamentoRequest>(pedido.toPagamentoRequest(destinatario))
        val pagamento = restTemplate.postForObject("${pagamentoUrl}/v1/pagamentos", request, PagamentoResponse::class.java)

        return pagamento!!;
    }


}