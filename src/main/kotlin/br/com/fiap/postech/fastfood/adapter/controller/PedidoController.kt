package br.com.fiap.postech.fastfood.adapter.controller


import br.com.fiap.postech.fastfood.adapter.presenter.*
import br.com.fiap.postech.fastfood.domain.usecase.pedido.IniciarPedidoCheckoutUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.ListarPedidoUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.ListarTodosPedidosUseCase
import br.com.fiap.postech.fastfood.domain.usecase.pedido.MudarStatusPedidoUseCase
import br.com.fiap.postech.fastfood.domain.valueObjets.StatusPedido
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("pedidos")
class PedidoController (
    private val listarPedidoUseCase: ListarPedidoUseCase,
    private val iniciarPedidoCheckoutUseCase: IniciarPedidoCheckoutUseCase,
    private val mudarStatusPedidoUseCase: MudarStatusPedidoUseCase,
    private val listarTodosPedidosUseCase: ListarTodosPedidosUseCase
) {

    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    fun cadastrar(@RequestBody pedidoRequest: PedidoRequest) : PedidoResponse {

       return iniciarPedidoCheckoutUseCase.executa(pedidoRequest.toPedido()).toResponse()
    }


    @GetMapping("/{id}")
    fun listar(@PathVariable id: UUID): PedidoResponse {
        return listarPedidoUseCase.execute(id).toResponse()
    }

    @GetMapping()
    fun listarTodosPedidos(): List<StatusPedidoResponse> {
        return listarTodosPedidosUseCase.execute()
    }

    @GetMapping("/status/{id}")
    fun consultarStatus(@PathVariable id: UUID): StatusPedidoResponse {
        return listarPedidoUseCase.execute(id).toStatusResponse()
    }

    @PutMapping("/mudar-status/confirmar-entrega/{id}")
    fun confirmarEntrega(@PathVariable id: UUID) =
        mudarStatusPedidoUseCase.executa(id, StatusPedido.FINALIZADO).toResponse()
}