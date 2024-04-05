package com.dicegame.service

import com.dicegame.dto.LeaderboardView
import com.dicegame.dto.PlayerWinnings
import com.dicegame.exception.NoBetsPlacedException
import com.dicegame.model.Player
import com.dicegame.model.Wallet
import com.dicegame.repository.PlayerRepository
import com.dicegame.repository.TransactionRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageRequest

internal class LeaderboardServiceTest {

    @Mock
    private lateinit var transactionRepository: TransactionRepository

    @Mock
    private lateinit var playerRepository: PlayerRepository
    @Mock
    private lateinit var leaderboardService: LeaderboardService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        leaderboardService = LeaderboardService(transactionRepository, playerRepository)
    }

    @Test
    fun `getLeaderboard should retrieve top five leaderboard entries`() {
        // Prepare mock data
        val playerWinningsList = listOf(
            PlayerWinnings(1L, 500),
            PlayerWinnings(2L, 400)
        )

        val expectedLeaderboard = listOf(
            LeaderboardView("player1", 500),
            LeaderboardView("player2", 400)
        )

        // Mock repository responses
        val topFivePageRequest = PageRequest.of(0, 5)
        whenever(transactionRepository.findPlayersTotalWinnings(topFivePageRequest))
            .thenReturn(playerWinningsList)
        whenever(playerRepository.findById(1L)).thenReturn(java.util.Optional.of(Player(1L, "player1", "Doe", "player1", Wallet(2000))))
        whenever(playerRepository.findById(2L)).thenReturn(java.util.Optional.of(Player(2L, "player2", "Smith", "player2", Wallet(3000))))

        // Execute the test
        val actualLeaderboard = leaderboardService.getLeaderboard()

        // Assertions
        assertEquals(expectedLeaderboard.size, actualLeaderboard.size, "The size of the retrieved leaderboard should match the expected")
        expectedLeaderboard.forEachIndexed { index, expectedEntry ->
            val actualEntry = actualLeaderboard[index]
            assertEquals(expectedEntry.playerName, actualEntry.playerName, "Player name should match for entry at index $index")
            assertEquals(expectedEntry.totalWinnings, actualEntry.totalWinnings, "Total winnings should match for entry at index $index")
        }
    }
    @Test
    fun `getLeaderboard throws NoBetsPlacedException when there are no winnings`() {
        // Arrange
        val emptyList = emptyList<PlayerWinnings>()
        val topFive = PageRequest.of(0, 5)
        `when`(transactionRepository.findPlayersTotalWinnings(topFive)).thenReturn(emptyList)

        // Act and Assert
        assertThrows(NoBetsPlacedException::class.java) {
            leaderboardService.getLeaderboard()
        }
    }
}