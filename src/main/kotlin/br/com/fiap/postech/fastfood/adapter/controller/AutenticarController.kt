/*package br.com.fiap.postech.fastfood.adapter.controller

import br.com.fiap.postech.fastfood.adapter.presenter.*
import br.com.fiap.postech.fastfood.domain.usecase.cliente.BuscarClientePorId
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

@RestController
@RequestMapping("autenticar")
class AutenticarController (
    private val buscarClientePorId: BuscarClientePorId
) {

    @GetMapping()
    fun autenticaCliente(@RequestParam cpf: String): ClienteAutenticado {

        val base64Secret =
            "Y2hhdmUtc2VjcmV0YS1sYW1iZGEtZmFzdC1mb29kLWZpYXAtcG9zdGVjaC10dXJtYS1kZS1hcnF1aXRldHVyYS1kZS1zb2Z0d2FyZQo="
        val keyBytes: ByteArray = Decoders.BASE64.decode(base64Secret)
        val CHAVE: SecretKey = Keys.hmacShaKeyFor(keyBytes)

        val cliente = buscarClientePorId.executa(cpf).toClienteResponse()

        val jwtToken = Jwts.builder()
            .setSubject(cliente.cpf)
            .setIssuedAt(Date())
            .setExpiration(Date.from(
                LocalDateTime.now()
                    .plusMinutes(15L)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()))
            .signWith(CHAVE, SignatureAlgorithm.HS512)
            .compact()

        val response = ClienteAutenticado(
            mensagem = "Cliente autenticado",
            token = jwtToken
        )

        return response
    }
}*/