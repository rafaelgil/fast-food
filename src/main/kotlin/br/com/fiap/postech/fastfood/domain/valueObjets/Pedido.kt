package br.com.fiap.postech.fastfood.domain.valueObjets

enum class StatusPedido(val status: String) {
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
    RECEBIDO("Pagamento Recebido"),
    EM_PREPARACAO("Em preparação"),
    PRONTO("Pronto"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado")
}