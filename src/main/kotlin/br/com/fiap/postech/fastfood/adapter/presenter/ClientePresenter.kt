package br.com.fiap.postech.fastfood.adapter.presenter

import java.util.*

data class ClienteResponse (
    val id: UUID,
    val cpf: String,
    val nome: String,
    val email: String
)