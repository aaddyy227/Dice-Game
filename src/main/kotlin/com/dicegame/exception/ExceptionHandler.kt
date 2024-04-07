package com.dicegame.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoBetsPlacedException::class)
    fun handleNoBetsPlacedException(ex: NoBetsPlacedException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(NoTransactionsFoundException::class)
    fun handleNoTransactionsFoundException(ex: NoTransactionsFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}

class NoBetsPlacedException(message: String) : IllegalArgumentException(message)

class NoTransactionsFoundException(message: String): RuntimeException(message)