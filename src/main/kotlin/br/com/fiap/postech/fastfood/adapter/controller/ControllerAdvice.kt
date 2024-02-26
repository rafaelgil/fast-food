package br.com.fiap.postech.fastfood.adapter.controller

import br.com.fiap.postech.fastfood.domain.exception.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdvice() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(NotFoundEntityException::class)
    fun handleIllegalArgumentException(ex: NotFoundEntityException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ClienteNotFoundException::class)
    fun handleClienteNotFoundException(ex: ClienteNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ProdutoPrecoException::class)
    fun handleProdutoPrecoException(ex: ProdutoPrecoException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PedidoException::class)
    fun handlePedidoException(ex: PedidoException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(PagamentoException::class)
    fun handlePagamentoException(ex: PagamentoException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleIllegalArgumentException(ex: DataIntegrityViolationException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ViolatesUniqueConstraintException::class)
    fun handleViolatesUniqueConstraintException(ex: ViolatesUniqueConstraintException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.localizedMessage
        )

        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(ex: EmptyResultDataAccessException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Dados não encontrados"
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(AlreadyProcessedException::class)
    fun handleAlreadyProcessedException(ex: AlreadyProcessedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            "Pagamento já efetuado"
        )

        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @ExceptionHandler(HttpClientErrorException::class)
    fun handleHttpClientErrorException(ex: HttpClientErrorException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
            ex.statusCode.value(),
            ex.responseBodyAsString
        )

        return ResponseEntity(erro, ex.statusCode)
    }
}

data class ErrorResponse(
    var codigoHttp: Int,
    var mensagem: String
)