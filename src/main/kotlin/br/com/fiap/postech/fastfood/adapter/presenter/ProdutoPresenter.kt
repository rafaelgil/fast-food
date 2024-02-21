package br.com.fiap.postech.fastfood.adapter.presenter

import java.math.BigDecimal
import java.util.*

data class ProdutoResponse(var id: UUID,
                          var descricao: String,
                          var categoria: String,
                          var preco: BigDecimal
)