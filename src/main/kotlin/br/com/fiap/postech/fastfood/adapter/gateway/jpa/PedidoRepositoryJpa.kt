package br.com.fiap.postech.fastfood.adapter.gateway.jpa

import br.com.fiap.postech.fastfood.adapter.gateway.schema.PedidoSchema
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PedidoRepositoryJpa : JpaRepository<PedidoSchema, UUID> {

    @Query("select p from pedido p where p.dataRecebimento is not null and p.status in (:status) order by p.dataRecebimento")
    fun findByPedidosRecebido(status: List<StatusPedido> = listOf(StatusPedido.PRONTO, StatusPedido.EM_PREPARACAO, StatusPedido.RECEBIDO))

    fun findByClienteIdAndStatus(clienteId: UUID, statusPedido: StatusPedido = StatusPedido.AGUARDANDO_PAGAMENTO): List<PedidoSchema>

}