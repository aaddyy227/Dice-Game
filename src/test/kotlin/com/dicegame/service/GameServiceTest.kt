package com.dicegame.service

import com.dicegame.dto.BetRequest
import com.dicegame.model.Bet
import com.dicegame.model.Player
import com.dicegame.repository.BetRepository
import com.dicegame.repository.PlayerRepository
import com.dicegame.utils.RandomNumberGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.*
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.*
import kotlin.random.Random


@ExtendWith(MockitoExtension::class)
class GameServiceTest {

    @Mock
    private lateinit var playerRepository: PlayerRepository

    @Mock
    private lateinit var betRepository: BetRepository

    @Mock
    private lateinit var transactionService: TransactionService

    @InjectMocks
    private lateinit var gameService: GameService

    @InjectMocks
    private lateinit var playerService: PlayerService
    @Mock
    private lateinit var randomNumberGenerator: RandomNumberGenerator
    @Captor
    private lateinit var betCaptor: ArgumentCaptor<Bet>


    @Test
    fun `placing a bet with a matching number results in a big win and balance update`() {
        val playerId = 1L
        val initialBalance = 1000
        val betAmount = 100
        val chosenNumber = 7
        val generatedNumber = 7 // Winning number
        val player = Player(playerId, "testplayer", "playertest", "playerTesting")

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.of(player))
        `when`(randomNumberGenerator.nextInt(1, 11)).thenReturn(generatedNumber)

        val betResult = gameService.placeBet(BetRequest(playerId, betAmount, chosenNumber))

        assertEquals(playerId, betResult.playerId)
        assertEquals((initialBalance-betAmount)+(betAmount * 10), betResult.newBalance)
        assertEquals("WIN", betResult.betOutcome)
        verify(transactionService, times(1)).recordTransaction(eq(playerId), anyInt(), anyString())
    }

    @Test
    fun `placing a bet with a number one away results in a smaller win`() {
        // Similar setup to the above test, with the generatedNumber set to one away from the chosenNumber
        // and assertions adjusted accordingly.
    }

    @Test
    fun `placing a bet and losing`() {
        val playerId = 1L
        val initialBalance = 1000
        val betAmount = 100
        val chosenNumber = 1
        val generatedNumber = 7 // Winning number
        val player = Player(playerId, "testplayer", "playertest", "playerTesting")

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.of(player))
        `when`(randomNumberGenerator.nextInt(1, 11)).thenReturn(generatedNumber)

        val betResult = gameService.placeBet(BetRequest(playerId, betAmount, chosenNumber))

        assertEquals(playerId, betResult.playerId)
        assertEquals(initialBalance-betAmount, betResult.newBalance)
        assertEquals("LOSS", betResult.betOutcome)
        verify(transactionService, times(1)).recordTransaction(eq(playerId), anyInt(), anyString())
    }
    @Test
    fun `placing a bet and win times five`() {
        val playerId = 1L
        val initialBalance = 1000
        val betAmount = 100
        val chosenNumber = 6
        val generatedNumber = 7 // Winning number
        val player = Player(playerId, "testplayer", "playertest", "playerTesting")

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.of(player))
        `when`(randomNumberGenerator.nextInt(1, 11)).thenReturn(generatedNumber)

        val betResult = gameService.placeBet(BetRequest(playerId, betAmount, chosenNumber))

        assertEquals(playerId, betResult.playerId)
        assertEquals((initialBalance-betAmount)+betAmount*5, betResult.newBalance)
        assertEquals("WIN", betResult.betOutcome)
        verify(transactionService, times(1)).recordTransaction(eq(playerId), anyInt(), anyString())
    }

    @Test
    fun `placing a bet and win half the bet`() {
        val playerId = 1L
        val initialBalance = 1000
        val betAmount = 100
        val chosenNumber = 3
        val generatedNumber = 5 // Winning number
        val player = Player(playerId, "testplayer", "playertest", "playerTesting")

        `when`(playerRepository.findById(playerId)).thenReturn(Optional.of(player))
        `when`(randomNumberGenerator.nextInt(1, 11)).thenReturn(generatedNumber)

        val betResult = gameService.placeBet(BetRequest(playerId, betAmount, chosenNumber))

        assertEquals(playerId, betResult.playerId)
        assertEquals((initialBalance-betAmount)+betAmount/2, betResult.newBalance)
        assertEquals("WIN", betResult.betOutcome)
        verify(transactionService, times(1)).recordTransaction(eq(playerId), anyInt(), anyString())
    }
    @Test
    fun `attempting to place a bet with insufficient balance throws an exception`() {
        val playerId = 1L
        val initialBalance = 50
        val betAmount = 100
        val chosenNumber = 5
        val player = Player(playerId, "testplayer", "playertest", "playerTesting")
        player.wallet.balance = initialBalance;

        `when`(playerRepository.findById(playerId)).doReturn(Optional.of(player))

        val exception = assertThrows(IllegalArgumentException::class.java) {
            gameService.placeBet(BetRequest(playerId, betAmount, chosenNumber))
        }

        assertEquals("Insufficient balance.", exception.message)
        verify(transactionService, never()).recordTransaction(anyLong(), anyInt(), anyString())
    }
}