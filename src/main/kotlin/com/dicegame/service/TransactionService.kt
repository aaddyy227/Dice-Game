package com.dicegame.service

import com.dicegame.model.Transaction
import com.dicegame.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    fun recordTransaction(playerId: Long, amount: Int, type: String) {
        val transaction = Transaction(
            playerId = playerId,
            amount = amount,
            transactionType = type,
            transactionTime = LocalDateTime.now()
        )
        transactionRepository.save(transaction)
    }
}