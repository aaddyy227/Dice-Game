package com.dicegame.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoBetsPlacedException::class)
    fun handleNoBetsPlacedException(ex: NoBetsPlacedException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
}


class NoBetsPlacedException(message: String) : IllegalArgumentException(message)
