package br.com.fiap.postech.fastfood.adapter.gateway.apis.produto

import br.com.fiap.postech.fastfood.adapter.presenter.ClienteResponse
import br.com.fiap.postech.fastfood.adapter.presenter.ProdutoResponse
import br.com.fiap.postech.fastfood.domain.exception.ClienteNotFoundException
import br.com.fiap.postech.fastfood.domain.exception.NotFoundEntityException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class ProdutoClient(
    @Autowired
    val restTemplate: RestTemplate,
    @Value("\${produto.url}")
    val produtoUrl: String,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun consultarProduto(id: UUID): ProdutoResponse {
        logger.info("Consultando cliente por id=${id}")

        return restTemplate.getForObject("${produtoUrl}/produto/${id}", ProdutoResponse::class.java)
            ?: throw NotFoundEntityException("Produto ${id} não encontrado")
    }

}