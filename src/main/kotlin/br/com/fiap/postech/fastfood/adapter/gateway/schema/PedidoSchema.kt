package br.com.fiap.postech.fastfood.adapter.gateway.schema

import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity(name="pedido")
data class PedidoSchema(
    @Id
    var id: UUID? = null,

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], fetch=FetchType.EAGER)
    var itens: List<ItemPedidoSchema> = mutableListOf(),

    @JoinColumn(name = "cliente_id")
    var clienteId: UUID,

    @Column
    var data: LocalDateTime,

    @Column(name = "data_recebimento")
    var dataRecebimento: LocalDateTime? = null,

    @Column
    @Enumerated(EnumType.STRING)
    var status: StatusPedido,

    @Column(name = "pagamento_id")
    var pagamentoId: UUID? = null,
)
