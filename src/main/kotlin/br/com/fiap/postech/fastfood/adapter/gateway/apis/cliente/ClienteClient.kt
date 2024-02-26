package br.com.fiap.postech.fastfood.adapter.gateway.apis.cliente

import br.com.fiap.postech.fastfood.adapter.presenter.ClienteResponse
import br.com.fiap.postech.fastfood.domain.exception.ClienteNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.util.*

@Component
class ClienteClient(
    @Autowired
    val restTemplate: RestTemplate,
    @Value("\${cliente.url}")
    val clienteUrl: String,
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun consultarCliente(id: UUID): ClienteResponse {
        logger.info("Consultando cliente por id=${id}")

        return restTemplate.getForObject("${clienteUrl}/cliente/${id}", ClienteResponse::class.java)
            ?: throw ClienteNotFoundException("Cliente ${id} não encontrado")

    }

}