package com.dicegame.service

import com.dicegame.repository.TransactionRepository
import com.dicegame.model.Transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import java.time.LocalDateTime
@ExtendWith(MockitoExtension::class)
class TransactionServiceTest {

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @InjectMocks
    private lateinit var transactionService: TransactionService

    @Test
    fun `recordTransaction saves a transaction`() {
        val playerId: Long = 1L
        val amount: Int = 100
        val type: String = "BET_PLACED"
        val transactionCaptor = ArgumentCaptor.forClass(Transaction::class.java)

        transactionService.recordTransaction(playerId, amount, type)

        verify(transactionRepository).save(transactionCaptor.capture())
        val capturedTransaction = transactionCaptor.value

        Assertions.assertEquals(playerId, capturedTransaction.playerId)
        Assertions.assertEquals(amount, capturedTransaction.amount)
        Assertions.assertEquals(type, capturedTransaction.transactionType)
        Assertions.assertNotNull(capturedTransaction.transactionTime)
        Assertions.assertTrue(capturedTransaction.transactionTime.isBefore(LocalDateTime.now().plusMinutes(1)) && capturedTransaction.transactionTime.isAfter(LocalDateTime.now().minusMinutes(1)))
    }
}