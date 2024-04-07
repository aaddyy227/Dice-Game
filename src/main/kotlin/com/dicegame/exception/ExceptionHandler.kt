package com.dicegame.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {


    @ExceptionHandler(NoBetsPlacedException::class)
    fun handleNoBetsPlacedException(ex: NoBetsPlacedException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NoTransactionsFoundException::class)
    fun handleNoTransactionsFoundException(ex: NoTransactionsFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExistsException(ex: UsernameAlreadyExistsException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserIdNotFoundException::class)
    fun handleUserIdNotFoundException(ex: UserIdNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotEnoughBalanceException::class)
    fun handleUserIdNotFoundException(ex: NotEnoughBalanceException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }
}

class NoBetsPlacedException(message: String) : RuntimeException(message)

class NoTransactionsFoundException(message: String) : RuntimeException(message)
class UserIdNotFoundException(message: String) : RuntimeException(message)
class NotEnoughBalanceException(message: String) : RuntimeException(message)
class UsernameAlreadyExistsException(message: String) : RuntimeException(message)